package com.game.mancala.models;

import lombok.*;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
/**
 * A class to represent the state of the game on the board of a mancala game.
 * This class has been designed to make it as close to the game's real world
 * existence as possible. This makes it easier to implement other service methods
 * going forward.
 */
public class Game {
    private Player playerOne;
    private Player playerTwo;
    private List<Pit> playerOneSmallPits;
    private List<Pit> playerTwoSmallPits;
    private Pit playerOneBigPit;
    private Pit playerTwoBigPit;
    private int nextTurn;
    private boolean isGameEnd;
    private int winner;
    private boolean isTieGame;
}
