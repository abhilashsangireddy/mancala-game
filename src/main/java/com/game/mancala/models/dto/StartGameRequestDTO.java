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
/**
 * A class to provide necessary inputs for the exposed public API
 * endpoint {@link com.game.mancala.controllers.MancalaController}
 * to start the game.
 * Its object instantiations is validated by {@link ValidationUtils}
 */
@ApiModel
public class StartGameRequestDTO {
    @ApiModelProperty(notes = "details corresponding to player one", required = true)
    private PlayerDTO playerOne;
    @ApiModelProperty(notes = "details corresponding to player two", required = true)
    private PlayerDTO playerTwo;
}