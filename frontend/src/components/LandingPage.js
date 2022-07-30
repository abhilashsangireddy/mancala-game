import React from "react";
import axios from "axios";
import Form from 'react-bootstrap/Form';
import InputGroup from 'react-bootstrap/InputGroup';
import Button from 'react-bootstrap/Button';

const BASE_URL = "http://localhost:8080";

export default function LandingPage({ gamePlayers, setGamePlayers, setGameState }) {
  const handleGameStart = async () => {
    const payload = {
    playerOne: 
        {
          name: gamePlayers.playerOne || "playerOne",
          playerNumber: 0
        },
        playerTwo: {
          name: gamePlayers.playerTwo || "playerTwo",
          playerNumber: 1
        },

    };
    const response = await axios.post(`${BASE_URL}/mancala/start-game`, payload);
    setGameState(response.data);
  };

  const handleOnChange = (evt) => {
    setGamePlayers((currentState) => ({
      ...currentState,
      [evt.target.name]: evt.target.value,
    }));
  };

  return (
    <div style={{ paddingLeft: "300px", paddingRight:"300px" , paddingTop: "50px"}}>
    <InputGroup className="mb-3">
        <InputGroup.Text id="basic-addon1">⛹️</InputGroup.Text>
        <Form.Control
          placeholder="Player 1"
          aria-label="Player 1"
          aria-describedby="basic-addon1"
          onChange={(evt) => handleOnChange(evt)}
          />
           
      </InputGroup>

      <InputGroup className="mb-3">
        <InputGroup.Text id="basic-addon1">⛹️</InputGroup.Text>
        <Form.Control
          placeholder="Player 2"
          aria-label="Player 2"
          aria-describedby="basic-addon1"
          onChange={(evt) => handleOnChange(evt)}
          />
           
      </InputGroup>
      <Button variant="outline-secondary" type="submit" onClick={handleGameStart}>
        Start
      </Button>
    </div>
  );
}
