import React, { useState } from "react";
import Board from "./components/Board";
import LandingPage from "./components/LandingPage";
import Scores from "./components/Scores";
import 'bootstrap/dist/css/bootstrap.min.css';

const PLAYERS_INITIAL_STATE = {
  playerOne: "",
  playerTwo: "",
};

const GAME_INITIAL_STATE = {
  players: [],
  gameEnded: true,
};


export default function App() {
  const [gamePlayers, setGamePlayers] = useState(PLAYERS_INITIAL_STATE);
  const [gameState, setGameState] = useState(GAME_INITIAL_STATE);
  const [scoreState, setScoreState] = useState(null);

const { playerOne, playerTwo, playerOneSmallPits, playerTwoSmallPits,  gameEnd , playerOneBigPit, playerTwoBigPit, nextTurn, winner, tie} = gameState;


  const gameExists = (playerOne && playerTwo);

  const onReset = () => {
    setGamePlayers(PLAYERS_INITIAL_STATE);
    setGameState(GAME_INITIAL_STATE);
    setScoreState(null);
  };

  return (
    <div style={{ textAlign: "center" }}>
      <h1 style={{ marginTop: "40px"}}>Mancala</h1>
      {!gameExists && (
        <LandingPage
          gamePlayers={gamePlayers}
          setGamePlayers={setGamePlayers}
          setGameState={setGameState}
        />
      )}
      {gameExists && !gameEnd && (
        <Board
          gameState={gameState}
          setGameState={setGameState}
          setScoreState={setScoreState}
          onReset={onReset}
        />
      )}
      {scoreState && <Scores scoreState={scoreState} onReset={onReset} />}
    </div>
  );
}
