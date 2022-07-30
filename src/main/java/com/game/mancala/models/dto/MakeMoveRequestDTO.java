package com.game.mancala.models.dto;

import com.game.mancala.utils.ValidationUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
/**
 * A class to provide necessary inputs for the exposed public API
 * endpoint {@link com.game.mancala.controllers.MancalaController}
 * to make a move in the game.
 * Its object instantiations is validated by {@link ValidationUtils}
 */
@ApiModel(description = "Describes the request to make a move in the game")
public class MakeMoveRequestDTO {
    @ApiModelProperty(notes = "player number corresponding to the player making the move", example = "0",required = true)
    private Integer playerNumber;
    @ApiModelProperty(notes = "index of the pit on which the player has selected to make the move", example = "2", required = true)
    private Integer pitNumber;
    @ApiModelProperty(notes = "state of the game on which the move is to be performed", required = true)
    private GameDTO gameDTO;
}
