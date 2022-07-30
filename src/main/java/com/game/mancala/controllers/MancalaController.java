package com.game.mancala.controllers;

import com.game.mancala.models.Game;
import com.game.mancala.models.Player;
import com.game.mancala.utils.adapters.GameAdapterUtils;
import com.game.mancala.utils.adapters.PlayerAdapterUtils;
import com.game.mancala.models.dto.GameDTO;
import com.game.mancala.models.dto.MakeMoveRequestDTO;
import com.game.mancala.models.dto.StartGameRequestDTO;
import com.game.mancala.services.MancalaService;
import com.game.mancala.utils.ValidationUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/mancala")
@ApiOperation("Products API")
public class MancalaController {

    private final MancalaService mancalaService;

    @Autowired
    public MancalaController(MancalaService mancalaService) {
        this.mancalaService = mancalaService;
    }

    @PostMapping("/start-game")
    @ApiOperation(value = "Start a game with two players", response = GameDTO.class,
            notes = "returns a new game based on the GameConstants set in the backend")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "startGameRequestDTO", value = "request DTO consisting of information for the two players",
//                    required = true, paramType = "body")
//    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    public ResponseEntity<GameDTO> startGame(@RequestBody StartGameRequestDTO startGameRequestDTO) {
        log.info("Starting new Mancala game");
        ValidationUtils.validateStartGameRequestDTO(startGameRequestDTO);
        Player playerOne = PlayerAdapterUtils.reverseAdapt(startGameRequestDTO.getPlayerOne());
        Player playerTwo = PlayerAdapterUtils.reverseAdapt(startGameRequestDTO.getPlayerTwo());
        Game game = mancalaService.startGame(playerOne, playerTwo);
        return ResponseEntity.ok(GameAdapterUtils.adapt(game));
    }

    @PostMapping("/make-move")
    @ApiOperation(value = "Make a move in game", response = GameDTO.class,
            notes = "returns a new game based move made by the players")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "moveRequestDTO", value = "request DTO consisting of information for player making the move, " +
//                    "index on which move is being made and the initial state of the game",
//                    required = true, paramType = "body", type = "MakeMoveRequestDTO")
//    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    public ResponseEntity<GameDTO> makeMove(@RequestBody MakeMoveRequestDTO moveRequestDTO) {
        log.info("Move request received from Player #" + moveRequestDTO.getPlayerNumber() + " on Pit #" + moveRequestDTO.getPitNumber());
        ValidationUtils.validateMakeMoveRequestDTO(moveRequestDTO);
        Game game = mancalaService.makeMove(moveRequestDTO.getPitNumber(), moveRequestDTO.getPlayerNumber(), GameAdapterUtils.reverseAdapt(moveRequestDTO.getGameDTO()));
        return ResponseEntity.ok(GameAdapterUtils.adapt(game));
    }

    @PostMapping("/end-game")
    @ApiOperation(value = "End the game", response = GameDTO.class,
            notes = "returns a game with winner details computed")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "GameDTO", value = "request DTO consisting of information for the game to be ended",
//                    required = true, paramType = "body")
//    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    public ResponseEntity<GameDTO> endGame(@RequestBody GameDTO gameDTO) {
        log.info("Request received to end game");
        ValidationUtils.validateGameRequestDTO(gameDTO);
        Game game = mancalaService.endGame(GameAdapterUtils.reverseAdapt(gameDTO));
        return ResponseEntity.ok(GameAdapterUtils.adapt(game));
    }

}
