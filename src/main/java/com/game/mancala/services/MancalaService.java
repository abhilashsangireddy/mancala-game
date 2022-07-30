package com.game.mancala.services;

import com.game.mancala.GameConstants;
import com.game.mancala.models.*;
import com.game.mancala.utils.GameUtils;
import com.game.mancala.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * A service class to provide various functional services to play the Mancala Game.
 * This class provides three methods to support starting a game, making a move and to end a game.
 * These methods expect valid game inputs and the necessary validation is to be provided by
 * {@link ValidationUtils} at appropriate places before calling these methods. This code is
 * designed to function independent of playerType as the game of Mancala is a Symmetric game.
 * @see <a href="https://en.wikipedia.org/wiki/Symmetric_game"> Symmetric Game</a>
 */
@Service
public class MancalaService {

    @Autowired
    private final GameConstantProvider gameConstantProvider;


    public MancalaService(GameConstantProvider gameConstantProvider){
        this.gameConstantProvider = gameConstantProvider;
    }

    /**
     * This method takes two players of type {@link Player} and initializes a game
     * of Mancala as per the constants provided in the {@link GameConstants}. The Big Pits counts
     * of the game are initialized to zero and the small pits count to the count specified in
     * {@link GameConstants}. The first turn of the player is randomly assigned.
     *
     * @param playerOne PlayerOne
     * @param playerTwo playerTwo
     * @return {@link Game}
     */
    public Game startGame(Player playerOne, Player playerTwo) {
        Game game = new Game();
        game.setPlayerOne(playerOne);
        game.setPlayerTwo(playerTwo);
        game.setNextTurn((int) Math.round(Math.random()));
        game.setGameEnd(false);
        initializePitsForMancalaGame(game);
        return game;
    }

    /**
     * This method alters the state of a 'game' object when a certain player decides to make a
     * move on a certain pit. The state of the game is altered based on the rules of the game
     * Mancala. It also checks for bonus conditions when the last stone placed by the user ends up
     * in one of his own empty small pits. It assigns the next turn based on whether the last stone is
     * ending up in the player's big pit or any of the small pits. Also checks for end game condition
     * where all the small pits of one of the players are empty.
     *
     * @param pitIndex     index of the pit on which the move is made.
     * @param playerNumber playerNumber that is making the move. Accepts values 0 or 1.
     * @param game         initial state of the game on which the move is to be made.
     * @return {@link Game} updated game object based on provided inputs.
     */
    public Game makeMove(Integer pitIndex, Integer playerNumber, Game game) {
        checkIfValidMove(pitIndex, playerNumber, game);
        int nextTurn;
        Pit lastUpdatedPit;
        List<Pit> currentPlayerSmallPits = GameUtils.getCurrentPlayerSmallPits(playerNumber, game);
        List<Pit> opponentPlayerSmallPits = GameUtils.getOpponentPlayerSmallPits(playerNumber, game);
        Pit currentPlayerBigPit = GameUtils.getCurrentPlayerBigPit(playerNumber, game);

        Integer stonesToDistribute = GameUtils.getPlayerSmallPitCountByIndex(pitIndex, playerNumber, game);
        currentPlayerSmallPits.get(pitIndex).setCount(0);
        lastUpdatedPit = currentPlayerSmallPits.get(pitIndex);
        int lastUpdatedPitIndex = 0;
        int currentPitIndex;
        boolean startAgain = false;

        while (stonesToDistribute > 0) {
            currentPitIndex = startAgain ? 0 : pitIndex + 1;
            while (currentPitIndex < gameConstantProvider.getNumberOfSmallPitsPerPlayer() && stonesToDistribute > 0) {
                currentPlayerSmallPits.get(currentPitIndex).incrementByOne();
                lastUpdatedPitIndex = currentPitIndex;
                lastUpdatedPit = currentPlayerSmallPits.get(currentPitIndex);
                currentPitIndex++;
                stonesToDistribute--;
            }
            if (stonesToDistribute > 0) {
                currentPlayerBigPit.incrementByOne();
                stonesToDistribute--;
                lastUpdatedPit = currentPlayerBigPit;
            }
            currentPitIndex = 0;
            while (currentPitIndex < gameConstantProvider.getNumberOfSmallPitsPerPlayer() && stonesToDistribute > 0) {
                opponentPlayerSmallPits.get(currentPitIndex).incrementByOne();
                lastUpdatedPitIndex = currentPitIndex;
                lastUpdatedPit = opponentPlayerSmallPits.get(currentPitIndex);

                currentPitIndex++;
                stonesToDistribute--;
            }
            startAgain = true;
        }
        checkForBonusAndUpdate(lastUpdatedPit, playerNumber, game, lastUpdatedPitIndex);

        nextTurn = valuateNextTurn(lastUpdatedPit, playerNumber);
        game.setNextTurn(nextTurn);
        game.setTieGame(false);
        game.setGameEnd(false);
        if (isGameEnd(game)) {
            addWinnerDetailsToGame(game);
        }
        return game;
    }

    /**
     * This method takes a game state and computes the winner of the game without making
     * any more moves. The criteria for winning a game is to have the most number of total
     * number of stones (number of stones in small pits and big pit combined).
     *
     * @param game initial state of the game on which the winner is to be decided.
     * @return {@link Game} updated game object with winner details.
     */
    public Game endGame(Game game) {
        addWinnerDetailsToGame(game);
        return game;
    }

    private void checkForBonusAndUpdate(Pit lastUpdatedPit, Integer playerNumber, Game game, Integer pitIndex) {
        if (PitType.SMALL_PIT.equals(lastUpdatedPit.getPitType()) && GameUtils.getPlayerTypeFromNumber(playerNumber).equals(lastUpdatedPit.getPlayerType())) {
            if (lastUpdatedPit.getCount() == 1) {
                List<Pit> opponentSmallPits = GameUtils.getOpponentPlayerSmallPits(playerNumber, game);
                Pit oppositePit = opponentSmallPits.get(gameConstantProvider.getNumberOfSmallPitsPerPlayer() - pitIndex-1);
                int bigPitIncrement = lastUpdatedPit.getCount() + oppositePit.getCount();
                Pit currentPlayerBigPit = GameUtils.getCurrentPlayerBigPit(playerNumber, game);
                currentPlayerBigPit.setCount(currentPlayerBigPit.getCount() + bigPitIncrement);
                lastUpdatedPit.setCount(0);
                oppositePit.setCount(0);
            }
        }
    }

    private int valuateNextTurn(Pit lastUpdatedPit, Integer playerNumber) {
        return PitType.BIG_PIT.equals(lastUpdatedPit.getPitType()) ? playerNumber : playerNumber ^ 1;
    }

    private void addWinnerDetailsToGame(Game game) {
        game.setGameEnd(true);
        int playerOneCount = game.getPlayerOneBigPit().getCount() + (int) game.getPlayerOneSmallPits().stream().map(Pit::getCount).count();
        int playerTwoCount = game.getPlayerTwoBigPit().getCount() + (int) game.getPlayerTwoSmallPits().stream().map(Pit::getCount).count();
        if (playerOneCount > playerTwoCount) {
            game.setWinner(0);
            game.setTieGame(false);
        } else if (playerOneCount < playerTwoCount) {
            game.setWinner(1);
            game.setTieGame(false);
        } else {
            game.setTieGame(true);
        }
    }

    private void checkIfValidMove(Integer pitNumber, Integer playerNumber, Game game) {
        if (pitNumber >= gameConstantProvider.getNumberOfSmallPitsPerPlayer()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such pit exists");
        }
        if (GameUtils.getCurrentPlayerSmallPits(playerNumber, game).get(pitNumber).getCount() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No stones present in the pit to make this move");
        }
    }

    private void initializePitsForMancalaGame(Game game) {
        game.setPlayerOneBigPit(Pit.builder().pitType(PitType.BIG_PIT).playerType(PlayerType.PLAYER_ONE).build());
        game.setPlayerTwoBigPit(Pit.builder().pitType(PitType.BIG_PIT).playerType(PlayerType.PLAYER_TWO).build());
        game.setPlayerOneSmallPits(new ArrayList<>(gameConstantProvider.getNumberOfSmallPitsPerPlayer()));
        game.setPlayerTwoSmallPits(new ArrayList<>(gameConstantProvider.getNumberOfSmallPitsPerPlayer()));
        for (int i = 0; i < gameConstantProvider.getNumberOfSmallPitsPerPlayer(); i++) {
            game.getPlayerOneSmallPits().add(new Pit( gameConstantProvider.getNumberOfStonesPerPit(), PitType.SMALL_PIT, PlayerType.PLAYER_ONE));
            game.getPlayerTwoSmallPits().add(new Pit( gameConstantProvider.getNumberOfStonesPerPit(), PitType.SMALL_PIT, PlayerType.PLAYER_TWO));
        }
    }

    private boolean isGameEnd(Game game) {
        List<Integer> playerOnePitsCounts = game.getPlayerOneSmallPits().stream().map(Pit::getCount).toList();
        List<Integer> playerTwoPitsCounts = game.getPlayerTwoSmallPits().stream().map(Pit::getCount).toList();
        Predicate<Integer> predicate = obj -> Objects.equals(0, obj);
        return playerOnePitsCounts.stream().allMatch(predicate) || playerTwoPitsCounts.stream().allMatch(predicate);
    }
}
