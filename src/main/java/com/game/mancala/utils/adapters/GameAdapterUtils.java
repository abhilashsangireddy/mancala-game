package com.game.mancala.utils.adapters;

import com.game.mancala.models.*;
import com.game.mancala.models.dto.GameDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * A utils class to provide support to adapt the {@link Game} objects to {@link GameDTO}
 * and back. This utils class necessity comes with the need to adapt a same object between the
 * Data Transfer object and the Actual Entity that is used in the code. This helps in exposing
 * only the useful information about the Entity state to the end user.
 */
public class GameAdapterUtils {

    public static GameDTO adapt(Game game) {
        GameDTO gameDTO = new GameDTO();

        gameDTO.setGameEnd(game.isGameEnd());
        gameDTO.setNextTurn(game.getNextTurn());
        gameDTO.setWinner(game.getWinner());
        gameDTO.setTie(game.isTieGame());

        gameDTO.setPlayerOne(PlayerAdapterUtils.adapt(game.getPlayerOne()));
        gameDTO.setPlayerTwo(PlayerAdapterUtils.adapt(game.getPlayerTwo()));

        gameDTO.setPlayerOneBigPit(game.getPlayerOneBigPit().getCount());
        gameDTO.setPlayerTwoBigPit(game.getPlayerTwoBigPit().getCount());
        gameDTO.setPlayerOneSmallPits(game.getPlayerOneSmallPits().stream().map(Pit::getCount).collect(Collectors.toList()));
        gameDTO.setPlayerTwoSmallPits(game.getPlayerTwoSmallPits().stream().map(Pit::getCount).collect(Collectors.toList()));
        return gameDTO;
    }


    public static Game reverseAdapt(GameDTO gameDTO) {
        Game game = new Game();
        game.setNextTurn(gameDTO.getNextTurn());
        game.setWinner(gameDTO.getWinner());
        game.setGameEnd(gameDTO.isGameEnd());
        game.setTieGame(gameDTO.isTie());

        game.setPlayerOne(Player.builder().playerType(PlayerType.PLAYER_ONE).name(gameDTO.getPlayerOne().getName()).build());
        game.setPlayerTwo(Player.builder().playerType(PlayerType.PLAYER_TWO).name(gameDTO.getPlayerTwo().getName()).build());

        game.setPlayerOneBigPit(Pit.builder().pitType(PitType.BIG_PIT).count(gameDTO.getPlayerOneBigPit()).playerType(PlayerType.PLAYER_ONE).build());
        game.setPlayerTwoBigPit(Pit.builder().pitType(PitType.BIG_PIT).count(gameDTO.getPlayerTwoBigPit()).playerType(PlayerType.PLAYER_TWO).build());

        List<Pit> playerOneSmallPits = new ArrayList<>();
        gameDTO.getPlayerOneSmallPits().forEach(pitCount -> {
            playerOneSmallPits.add(Pit.builder().pitType(PitType.SMALL_PIT).count(pitCount).playerType(PlayerType.PLAYER_ONE).build());
        });
        game.setPlayerOneSmallPits(playerOneSmallPits);

        List<Pit> playerTwoSmallPits = new ArrayList<>();
        gameDTO.getPlayerTwoSmallPits().forEach(pitCount -> {
            playerTwoSmallPits.add(Pit.builder().pitType(PitType.SMALL_PIT).count(pitCount).playerType(PlayerType.PLAYER_TWO).build());
        });
        game.setPlayerTwoSmallPits(playerTwoSmallPits);

        return game;
    }

}
