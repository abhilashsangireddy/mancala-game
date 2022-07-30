package com.game.mancala.unit;

import com.game.mancala.models.Game;
import com.game.mancala.models.Pit;
import com.game.mancala.models.PitType;
import com.game.mancala.models.PlayerType;
import com.game.mancala.services.GameConstantProvider;
import com.game.mancala.services.MancalaService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.game.mancala.MancalaUnitTestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MancalaServiceUnitTest {

    private MancalaService mancalaService;
    private GameConstantProvider gameConstantProvider;

    @BeforeAll
    public void setUp() {
        gameConstantProvider = Mockito.mock(GameConstantProvider.class);
        mancalaService = new MancalaService(gameConstantProvider);
        Mockito.when(gameConstantProvider.getNumberOfSmallPitsPerPlayer()).thenReturn(6);
        Mockito.when(gameConstantProvider.getNumberOfStonesPerPit()).thenReturn(6);
    }

    @Test
    public void MancalaService_StartGame() {
        Game game = mancalaService.startGame(playerOne, playerTwo);
        assertEquals(playerOne, game.getPlayerOne(), "playerOne is not set properly");
        assertEquals(playerTwo, game.getPlayerTwo(), "playerTwo is not set properly");
        assertEquals(gameConstantProvider.getNumberOfSmallPitsPerPlayer(), game.getPlayerOneSmallPits().size(),
                "number of initialized small pits for playerOne is wrong");
        assertEquals(gameConstantProvider.getNumberOfSmallPitsPerPlayer(), game.getPlayerTwoSmallPits().size(),
                "number of initialized small pits for playerTwo is wrong");

        for (int i = 0; i < gameConstantProvider.getNumberOfSmallPitsPerPlayer(); i++) {
            assertEquals(gameConstantProvider.getNumberOfStonesPerPit(), game.getPlayerOneSmallPits().get(i).getCount(),
                    "number of initialized stones in pits for playerOne is wrong");
            assertEquals(gameConstantProvider.getNumberOfStonesPerPit(), game.getPlayerTwoSmallPits().get(i).getCount(),
                    "number of initialized stones in pits for playerTwo is wrong");
            assertEquals(PlayerType.PLAYER_ONE, game.getPlayerOneSmallPits().get(i).getPlayerType(),
                    "PlayerType is wrongly assigned for playerOne small pits");
            assertEquals(PlayerType.PLAYER_TWO, game.getPlayerTwoSmallPits().get(i).getPlayerType(),
                    "PlayerType is wrongly assigned for playerTwo small pits");
            assertEquals(PitType.SMALL_PIT, game.getPlayerOneSmallPits().get(i).getPitType(),
                    "PitType is wrongly assigned for playerOne small pits");
            assertEquals(PitType.SMALL_PIT, game.getPlayerTwoSmallPits().get(i).getPitType(),
                    "PitType is wrongly assigned for playerTwo small pits");
        }
        assertEquals(0, game.getPlayerOneBigPit().getCount(),
                "Number of stones in big pits must be zero  for playerOne during initialization");
        assertEquals(0, game.getPlayerTwoBigPit().getCount(),
                "Number of stones in big pits must be zero  for playerTwo during initialization");
        assertEquals(PitType.BIG_PIT, game.getPlayerOneBigPit().getPitType(), "BigPit type doesn't match for playerOne during initialization");
        assertEquals(PitType.BIG_PIT, game.getPlayerTwoBigPit().getPitType(), "BigPit type doesn't match  for playerTwo during initialization");
        assertTrue(POSSIBLE_PLAYER_NUMBERS.contains(game.getNextTurn()), "nextTurn initialized to invalid number");
        assertFalse(game.isGameEnd(), "game is initialized to end game case");
    }

    @Test
    public void MancalaService_MakeMove_RandomMoveOne() {
        Game gameBeforeMove = getGameFromValues(Arrays.asList(6, 6, 6, 6, 6, 6), Arrays.asList(6, 6, 6, 6, 6, 6), 0, 0);
        Game gameAfterMove = mancalaService.makeMove(0, 1, gameBeforeMove);
        verifyPits(Arrays.asList(6, 6, 6, 6, 6, 6), Arrays.asList(0, 7, 7, 7, 7, 7), 0, 1, gameAfterMove);
        assertFalse(gameAfterMove.isTieGame(), "isTieGame wrongly set");
        assertFalse(gameAfterMove.isGameEnd(), "Game End wrongly set");
        assertEquals(1, gameAfterMove.getNextTurn(), "nextTurn wrongly computed");
        assertEquals(0, gameAfterMove.getWinner(), "winner wrongly computed");
    }

    @Test
    public void MancalaService_MakeMove_RandomMoveTwo() {
        Game gameBeforeMove = getGameFromValues(Arrays.asList(6, 6, 6, 6, 6, 6), Arrays.asList(0, 7, 7, 7, 7, 7), 0, 1);
        Game gameAfterMove = mancalaService.makeMove(1, 1, gameBeforeMove);
        verifyPits(Arrays.asList(7, 7, 6, 6, 6, 6), Arrays.asList(0, 0, 8, 8, 8, 8), 0, 2, gameAfterMove);
        assertFalse(gameAfterMove.isTieGame(), "isTieGame wrongly set");
        assertFalse(gameAfterMove.isGameEnd(), "Game End wrongly set");
        assertEquals(0, gameAfterMove.getNextTurn(), "nextTurn wrongly computed");
        assertEquals(0, gameAfterMove.getWinner(), "winner wrongly computed");
    }

    @Test
    public void MancalaService_MakeMove_RandomMoveThree() {
        Game gameBeforeMove = getGameFromValues(Arrays.asList(7, 7, 0, 7, 7, 7), Arrays.asList(1, 1, 8, 8, 8, 8), 1, 2);
        Game gameAfterMove = mancalaService.makeMove(4, 1, gameBeforeMove);
        verifyPits(Arrays.asList(8, 8, 1, 8, 8, 8), Arrays.asList(1, 1, 8, 8, 0, 9), 1, 3, gameAfterMove);
        assertFalse(gameAfterMove.isTieGame(), "isTieGame wrongly set");
        assertFalse(gameAfterMove.isGameEnd(), "Game End wrongly set");
        assertEquals(0, gameAfterMove.getNextTurn(), "nextTurn wrongly computed");
        assertEquals(0, gameAfterMove.getWinner(), "winner wrongly computed");
    }

    @Test
    public void MancalaService_MakeMove_RandomMoveFour() {
        Game gameBeforeMove = getGameFromValues(Arrays.asList(15, 1, 8, 5, 2, 4), Arrays.asList(7, 7, 3, 1, 0, 0), 12, 7);
        Game gameAfterMove = mancalaService.makeMove(0, 0, gameBeforeMove);
        verifyPits(Arrays.asList(1, 3, 10, 6, 3, 5), Arrays.asList(8, 8, 4, 2, 1, 1), 13, 7, gameAfterMove);
        assertFalse(gameAfterMove.isTieGame(), "isTieGame wrongly set");
        assertFalse(gameAfterMove.isGameEnd(), "Game End wrongly set");
        assertEquals(1, gameAfterMove.getNextTurn(), "nextTurn wrongly computed");
        assertEquals(0, gameAfterMove.getWinner(), "winner wrongly computed");
    }

    @Test
    public void MancalaService_MakeMove_BonusConditionMove() {
        Game gameBeforeMove = getGameFromValues(Arrays.asList(4, 0, 0, 0, 0, 2), Arrays.asList(12, 0, 4, 10, 1, 8), 22, 9);
        Game gameAfterMove = mancalaService.makeMove(0, 0, gameBeforeMove);
        verifyPits(Arrays.asList(0, 1, 1, 1, 0, 2), Arrays.asList(12, 0, 4, 10, 1, 8), 23, 9, gameAfterMove);
        assertFalse(gameAfterMove.isTieGame(), "isTieGame wrongly set");
        assertFalse(gameAfterMove.isGameEnd(), "Game End wrongly set");
        assertEquals(1, gameAfterMove.getNextTurn(), "nextTurn wrongly computed");
        assertEquals(0, gameAfterMove.getWinner(), "winner wrongly computed");
    }

    @Test
    public void MancalaService_MakeMove_GameEndConditionMove() {
        Game gameBeforeMove = getGameFromValues(Arrays.asList(0, 3, 2, 9, 5, 0), Arrays.asList(0, 0, 0, 1, 0, 0), 31, 21);
        Game gameAfterMove = mancalaService.makeMove(3, 1, gameBeforeMove);
        verifyPits(Arrays.asList(0, 0, 2, 9, 5, 0), Arrays.asList(0, 0, 0, 0, 0, 0), 31, 25, gameAfterMove);
        assertFalse(gameAfterMove.isTieGame(), "isTieGame wrongly set");
        assertTrue(gameAfterMove.isGameEnd(), "Game End wrongly set");
        assertEquals(0, gameAfterMove.getNextTurn(), "nextTurn wrongly computed");
        assertEquals(0, gameAfterMove.getWinner(), "winner wrongly computed");
    }

    @Test
    public void MancalaService_MakeMove_ExceptionConditionOne() {
        Game gameBeforeMove = getGameFromValues(Arrays.asList(0, 3, 2, 9, 5, 0), Arrays.asList(0, 0, 0, 1, 0, 0), 31, 21);
        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () ->  mancalaService.makeMove(10, 1, gameBeforeMove),
                "Expected to throw a ResponseStatusException, but it didn't"
        );
        assertTrue(Objects.requireNonNull(thrown.getMessage()).contains("No such pit exists"));
    }

    @Test
    public void MancalaService_MakeMove_ExceptionConditionTwo() {
        Game gameBeforeMove = getGameFromValues(Arrays.asList(0, 3, 2, 9, 5, 0), Arrays.asList(0, 0, 0, 1, 0, 0), 31, 21);
        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () ->  mancalaService.makeMove(0, 1, gameBeforeMove),
                "Expected to throw a ResponseStatusException, but it didn't"
        );
        assertTrue(Objects.requireNonNull(thrown.getMessage()).contains("No stones present in the pit to make this move"));
    }

    @Test
    public void MancalaService_EndGame_TieCondition() {
        Game gameBeforeMove = getGameFromValues(Arrays.asList(6, 6, 6, 6, 6, 6), Arrays.asList(6, 6, 6, 6, 6, 6), 0, 0);
        Game gameAfterMove = mancalaService.endGame(gameBeforeMove);
        verifyPits(Arrays.asList(6, 6, 6, 6, 6, 6), Arrays.asList(6, 6, 6, 6, 6, 6), 0, 0, gameAfterMove);
        assertTrue(gameAfterMove.isTieGame(), "isTieGame wrongly set");
        assertTrue(gameAfterMove.isGameEnd(), "Game End wrongly set");
        assertEquals(0, gameAfterMove.getNextTurn(), "nextTurn wrongly computed");
        assertEquals(0, gameAfterMove.getWinner(), "winner wrongly computed");
    }

    @Test
    public void MancalaService_EndGame_RandomCondition() {
        Game gameBeforeMove = getGameFromValues(Arrays.asList(9, 9, 1, 2, 9, 8), Arrays.asList(9, 9, 2, 0, 1, 8), 2, 3);
        Game gameAfterMove = mancalaService.endGame(gameBeforeMove);
        verifyPits(Arrays.asList(9, 9, 1, 2, 9, 8), Arrays.asList(9, 9, 2, 0, 1, 8), 2, 3, gameAfterMove);
        assertFalse(gameAfterMove.isTieGame(), "isTieGame wrongly set");
        assertTrue(gameAfterMove.isGameEnd(), "Game End wrongly set");
        assertEquals(0, gameAfterMove.getNextTurn(), "nextTurn wrongly computed");
        assertEquals(1, gameAfterMove.getWinner(), "winner wrongly computed");
    }

    private void verifyPits(List<Integer> playerOneSmallPitsCounts, List<Integer> playerTwoSmallPitsCount,
                            Integer playerOneBigPitCount, Integer playerTwoBigPitCount, Game game) {
        List<Pit> playerOneSmallPits = playerOneSmallPitsCounts.stream().map(count -> adaptToPlayer(PitType.SMALL_PIT, count, PlayerType.PLAYER_ONE)).toList();
        List<Pit> playerTwoSmallPits = playerTwoSmallPitsCount.stream().map(count -> adaptToPlayer(PitType.SMALL_PIT, count, PlayerType.PLAYER_TWO)).toList();
        assertEquals(playerOneSmallPits, game.getPlayerOneSmallPits(), "PlayerOne small-pits are not computed properly");
        assertEquals(playerTwoSmallPits, game.getPlayerTwoSmallPits(), "PlayerTwo small-pits are not computed properly");
        assertEquals(Pit.builder().pitType(PitType.BIG_PIT).playerType(PlayerType.PLAYER_ONE).count(playerOneBigPitCount).build(), game.getPlayerOneBigPit(), "playerOne bigPit not computed properly");
        assertEquals(Pit.builder().pitType(PitType.BIG_PIT).playerType(PlayerType.PLAYER_TWO).count(playerTwoBigPitCount).build(), game.getPlayerTwoBigPit(), "playerTwo bigPit not computed properly");
    }
}
