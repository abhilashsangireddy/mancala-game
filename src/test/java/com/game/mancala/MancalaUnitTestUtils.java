package com.game.mancala;

import com.game.mancala.models.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MancalaUnitTestUtils {

    public static final String PLAYER_ONE_NAME = "playerOne";
    public static final String PLAYER_TWO_NAME = "playerTwo";

    public static final Player playerOne = Player.builder().playerType(PlayerType.PLAYER_ONE).name(PLAYER_ONE_NAME).build();
    public static final Player playerTwo = Player.builder().playerType(PlayerType.PLAYER_TWO).name(PLAYER_TWO_NAME).build();

    public static final List<Integer> POSSIBLE_PLAYER_NUMBERS = new ArrayList<>(Arrays.asList(0, 1));

    public static Pit adaptToPlayer(PitType pitType, Integer count, PlayerType playerType) {
        return Pit.builder().pitType(pitType).count(count).playerType(playerType).build();
    }

    public static Game getGameFromValues(List<Integer> playerOneSmallPitsCounts, List<Integer> playerTwoSmallPitsCount,
                                         Integer playerOneBigPitCount, Integer playerTwoBigPitCount) {
        List<Pit> playerOneSmallPits = playerOneSmallPitsCounts.stream().map(count -> adaptToPlayer(PitType.SMALL_PIT, count, PlayerType.PLAYER_ONE)).toList();
        List<Pit> playerTwoSmallPits = playerTwoSmallPitsCount.stream().map(count -> adaptToPlayer(PitType.SMALL_PIT, count, PlayerType.PLAYER_TWO)).toList();
        return Game.builder().playerOne(playerOne).playerTwo(playerTwo).playerOneSmallPits(playerOneSmallPits).playerTwoSmallPits(playerTwoSmallPits)
                .playerOneBigPit(Pit.builder().pitType(PitType.BIG_PIT).playerType(PlayerType.PLAYER_ONE).count(playerOneBigPitCount).build())
                .playerTwoBigPit(Pit.builder().pitType(PitType.BIG_PIT).playerType(PlayerType.PLAYER_TWO).count(playerTwoBigPitCount).build())
                .isTieGame(false).isGameEnd(false).build();
    }

}
