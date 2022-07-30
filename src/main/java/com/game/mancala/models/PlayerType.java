package com.game.mancala.models;

import lombok.Getter;
@Getter
/**
 * An enum class to quantify the type of the player that is associated with various
 * classes of the code like {@link Pit} and {@link Player}.
 */
public enum PlayerType {
    PLAYER_ONE(0),
    PLAYER_TWO(1);
    public final int playerNumber;
    PlayerType(int playerNumber) {
        this.playerNumber = playerNumber;
    }
}
