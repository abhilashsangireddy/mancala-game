package com.game.mancala.models.dto;

import com.game.mancala.utils.ValidationUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
 * A class to provide necessary inputs for the exposed public API
 * endpoint {@link com.game.mancala.controllers.MancalaController}
 * to understand the state of the game sent by the API.
 * Its object instantiations is validated by {@link ValidationUtils}
 */
@ApiModel(description = "Describes the state of the game")
public class GameDTO {
    @ApiModelProperty(notes = "playerOne", example = "{\"name\":\"playerOne\",\"playerNumber\":0}", required = true)
    private PlayerDTO playerOne;
    @ApiModelProperty(notes = "name", example = "{\"name\":\"playerTwo\",\"playerNumber\":1}", required = true)
    private PlayerDTO playerTwo;
    @ApiModelProperty(notes = "Array containing No. of stones in each of player one small pits", example = "[1,9,2,0,1,8]", required = true)
    private List<Integer> playerOneSmallPits;
    @ApiModelProperty(notes = "Array containing No. of stones in each of player two small pits", example = "[9,9,2,0,1,8]", required = true)
    private List<Integer> playerTwoSmallPits;
    @ApiModelProperty(notes = "No. of stones in playerOne BigPit", example = "22", required = true)
    private int playerOneBigPit;
    @ApiModelProperty(notes = "No. of stones in playerTwo BigPit", example = "22", required = true)
    private int playerTwoBigPit;
    @ApiModelProperty(notes = "playerNumber corresponding to the next turn", example = "0", required = false)
    private int nextTurn;
    @ApiModelProperty(notes = "if the game has ended ", example = "false", required = false)
    private boolean isGameEnd;
    @ApiModelProperty(notes = "playerNumber corresponding to winner of the game", example = "1", required = false)
    private int winner;
    @ApiModelProperty(notes = "if it is a tie game", example = "false", required = false)
    private boolean isTie;
}
