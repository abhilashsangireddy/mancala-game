package com.game.mancala.utils.adapters;

import com.game.mancala.models.Player;
import com.game.mancala.models.PlayerType;
import com.game.mancala.models.dto.PlayerDTO;

/**
 * A utils class to provide support to adapt the {@link Player} objects to {@link PlayerDTO}
 * and back. This utils class necessity comes with the need to adapt a same object between the
 * Data Transfer object and the Actual Entity that is used in the code. This helps in exposing
 * only the useful information about the Entity state to the end user.
 */
public class PlayerAdapterUtils {

    public static PlayerDTO adapt(Player player) {
        return new PlayerDTO(player.getName(), player.getPlayerType().getPlayerNumber());
    }

    public static Player reverseAdapt(PlayerDTO playerDTO) {
        Player player = new Player();
        player.setName(playerDTO.getName());
        if(playerDTO.getPlayerNumber() == 0) {
            player.setPlayerType(PlayerType.PLAYER_ONE);
        } else if (playerDTO.getPlayerNumber() == 1) {
            player.setPlayerType(PlayerType.PLAYER_TWO);
        }
        return player;
    }
}
