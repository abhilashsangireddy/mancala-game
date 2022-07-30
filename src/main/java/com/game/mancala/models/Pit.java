package com.game.mancala.models;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
/**
 * A class to represent the pits on the board of a mancala game.
 * Is always associated with a player and pitType.
 * Found as a part of {@link Game}
 */
public class Pit {
    private int count;
    private PitType pitType;
    private PlayerType playerType;

    public void incrementByOne() {
        this.count = this.count + 1;
    }
}
