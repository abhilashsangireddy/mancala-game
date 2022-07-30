package com.game.mancala.utils;

import com.game.mancala.models.Game;
import com.game.mancala.models.Pit;
import com.game.mancala.models.PlayerType;

import java.util.List;
import static com.game.mancala.models.PlayerType.PLAYER_ONE;
import static com.game.mancala.models.PlayerType.PLAYER_TWO;

/**
 * A utils class to provide procedure calls to methods that can be frequently
 * performed on a game. The methods described in this class are not tied up to the
 * state of the current class. Hence, these methods are all made static.
 */
public class GameUtils {

    public static Integer getPlayerSmallPitCountByIndex(Integer pitIndex, Integer playerNumber, Game game) {
        List<Integer> playerSmallPits = getPlayerSmallPitCounts(playerNumber, game);
        return playerSmallPits.get(pitIndex);
    }

    private static List<Integer> getPlayerSmallPitCounts(Integer playerNumber, Game game) {
        List<Pit> playerSmallPits = (PLAYER_ONE.getPlayerNumber() == playerNumber) ? game.getPlayerOneSmallPits() : game.getPlayerTwoSmallPits();
        return playerSmallPits.stream().map(Pit::getCount).toList();
    }

    public static List<Pit> getCurrentPlayerSmallPits(Integer playerNumber, Game game) {
        return (PLAYER_ONE.getPlayerNumber() == playerNumber) ? game.getPlayerOneSmallPits() : game.getPlayerTwoSmallPits();
    }

    public static List<Pit> getOpponentPlayerSmallPits(Integer playerNumber, Game game) {
        return (PLAYER_ONE.getPlayerNumber() == playerNumber) ? game.getPlayerTwoSmallPits() : game.getPlayerOneSmallPits();
    }

    public static Pit getCurrentPlayerBigPit(Integer playerNumber, Game game) {
        return (PLAYER_ONE.getPlayerNumber() == playerNumber) ? game.getPlayerOneBigPit() : game.getPlayerTwoBigPit();
    }

    public static PlayerType getPlayerTypeFromNumber(Integer playerNumber) {
        return (PLAYER_ONE.getPlayerNumber() == playerNumber) ? PLAYER_ONE : PLAYER_TWO;
    }
}
