package com.game.mancala.models.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
/**
 * A class to capture necessary inputs for the exposed public API
 * endpoint {@link com.game.mancala.controllers.MancalaController}
 * while representing a player.
 * This class object becomes a part {@link GameDTO}
 */
@ApiModel(description = "Describes the player participating in a game")
public class PlayerDTO {
    @ApiModelProperty(notes = "Name of the player", example = "Abhilash", required = true)
    private String name;
    @ApiModelProperty(notes = "number corresponding to player. 0 if playerOne, 1 if playertwo", example = "0", required = true)
    private Integer playerNumber;
}
