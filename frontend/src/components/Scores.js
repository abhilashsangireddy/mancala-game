import React from "react";
import "../App.css";
import Button from 'react-bootstrap/Button';

export default function Scores({ scoreState, onReset }) {
  console.log('--score state', scoreState)
  const { tie, winner , playerOne, playerTwo} = scoreState;
  var winnerName = (winner === 0)?playerOne.name: playerTwo.name;
  return (
    <div style={{ paddingTop: "50px"}}>
      <h3>Results:</h3>
      <div
        style={{
          border: "0px solid",
          width: "30rem",
          height: "18rem",
          flexDirection: "column",
          display: "flex",
          alignItems: "center",
          padding: "0px",
          margin: "auto",
        }}
      >
        <h3>{tie ? "It is a tie game" : `Winner of the game is: `}</h3>
        <h3>{!tie ? ` ${winnerName} ` : ``}</h3>
        <div
          style={{
            fontWeight: "bold",
            paddingBottom: "15px",
            display: "flex",
            alignSelf: "stretch",
          }}
        >
        </div>
        <Button variant="outline-success" style={{ padding: "10px" }} onClick={onReset}>
        Play again
        </Button>
      </div>
    </div>
  );
}
