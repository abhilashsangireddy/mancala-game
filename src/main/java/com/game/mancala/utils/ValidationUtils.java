package com.game.mancala.utils;

import com.game.mancala.GameConstants;
import com.game.mancala.models.Game;
import com.game.mancala.models.dto.GameDTO;
import com.game.mancala.models.dto.MakeMoveRequestDTO;
import com.game.mancala.models.dto.PlayerDTO;
import com.game.mancala.models.dto.StartGameRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
/**
 * A utils class to provide validations for the API exposed on {@link com.game.mancala.controllers.MancalaController}.
 * This class supports three methods to start the game, make a move and to end the game.
 * These methods expect valid game inputs and the necessary validation is to be provided by classes like
 * {@link ValidationUtils} at appropriate places before calling these methods.
 */
public class ValidationUtils {

    public static void validateStartGameRequestDTO(StartGameRequestDTO startGameRequestDTO) {
        List<String> errors = new ArrayList<>();
        validatePlayerDTOs(startGameRequestDTO.getPlayerOne(), startGameRequestDTO.getPlayerTwo(), errors);
        log.info(errors.toString());
        System.out.println(errors);
        if (!errors.isEmpty()) {
            ResponseStatusException ex = new ResponseStatusException(HttpStatus.BAD_REQUEST);
            for (String error : errors) {
                ex.addSuppressed(new Exception(error));
            }
            throw ex;
        }
    }

    public static  void validateGameRequestDTO(GameDTO gameDTO) {
        List<String> errors = new ArrayList<>();
        validateGameDTO(gameDTO, errors);
        if (!errors.isEmpty()) {
            ResponseStatusException ex = new ResponseStatusException(HttpStatus.BAD_REQUEST);
            for (String error : errors) {
                ex.addSuppressed(new Exception(error));
            }
            throw ex;
        }
    }

    public static void validateMakeMoveRequestDTO(MakeMoveRequestDTO moveRequestDTO) {
        List<String> errors = new ArrayList<>();
        if (!isValidPlayerNumber(moveRequestDTO.getPlayerNumber())) {
            errors.add("Invalid player numbers");
        }
        if (moveRequestDTO.getPitNumber() > GameConstants.NUMBER_OF_SMALL_PITS_PER_PLAYER) {
            errors.add("Invalid pit number");
        }
        validateGameDTO(moveRequestDTO.getGameDTO(), errors);
        if (!errors.isEmpty()) {
            ResponseStatusException ex = new ResponseStatusException(HttpStatus.BAD_REQUEST);
            for (String error : errors) {
                ex.addSuppressed(new Exception(error));
            }
            throw ex;
        }
    }

    private static void validateGameDTO(GameDTO gameDTO, List<String> errors) {
        validatePlayerDTOs(gameDTO.getPlayerOne(), gameDTO.getPlayerTwo(), errors);
        if (gameDTO.getPlayerOneSmallPits().size() > GameConstants.NUMBER_OF_SMALL_PITS_PER_PLAYER ||
                gameDTO.getPlayerTwoSmallPits().size() > GameConstants.NUMBER_OF_SMALL_PITS_PER_PLAYER) {
            errors.add("Players can't have more than " + GameConstants.NUMBER_OF_SMALL_PITS_PER_PLAYER + " small pits each");
        }
        if (!isValidPlayerNumber(gameDTO.getNextTurn())) {
            errors.add("Invalid next turn number");
        }
    }


    private static void validatePlayerDTOs(PlayerDTO playerOneDTO, PlayerDTO playerTwoDTO, List<String> errors) {
        ensureNotNull(playerOneDTO, "playerOne is null", errors);
        ensureNotNull(playerTwoDTO, "playerTwo is null", errors);
        if (!hasContent(playerOneDTO.getName()) || !hasContent(playerTwoDTO.getName())) {
            errors.add("Player name has no content.");
        }
        if (!isValidPlayerNumber(playerOneDTO.getPlayerNumber()) || !isValidPlayerNumber(playerTwoDTO.getPlayerNumber())
                || !isValidPlayerNumberPair(playerOneDTO.getPlayerNumber(), playerTwoDTO.getPlayerNumber())) {
            errors.add("Invalid player numbers");
        }
    }

    private static void ensureNotNull(Object field, String errorMsg, List<String> errors) {
        if (field == null) {
            errors.add(errorMsg);
        }
    }

    private static boolean hasContent(String string) {
        return (string != null && string.trim().length() > 0);
    }

    private static boolean isValidPlayerNumber(int playerNumber) {
        return playerNumber == 0 || playerNumber == 1;
    }

    private static boolean isValidPlayerNumberPair(int playerOneNumber, int playerTwoNumber) {
        return (playerOneNumber == 0 && playerTwoNumber == 1) || (playerOneNumber == 1 && playerTwoNumber == 0);
    }
}
