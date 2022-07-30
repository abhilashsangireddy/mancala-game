import React, { useState } from "react";
import axios from "axios";
import Scores from './Scores'
import Button from 'react-bootstrap/Button';

const BASE_URL = "http://localhost:8080";

const getCurrentTurn = (gameState) => {
    const player = gameState.nextTurn === 0 ? gameState.playerOne : gameState.playerTwo;
    return { name: player.name, type: player.playerNumber };
};

export default function Board({ gameState, setGameState, setScoreState, onReset }) {

    const [turn, setTurn] = useState(getCurrentTurn(gameState));
    const playerOne = gameState.playerOne;
    const playerTwo = gameState.playerTwo;
    const playerOneSmallPits = gameState.playerOneSmallPits;
    const playerTwoSmallPits = gameState.playerTwoSmallPits;
    const [data, setNewData] = useState({})
    const [score, setScore] = useState(null);

    const handleBoardCellSelected = async (idx, playerType) => {
        gameState.playerTwoSmallPits = gameState.playerTwoSmallPits.reverse()
        if (playerType === 1) {
            idx = playerTwoSmallPits.length - idx - 1;
        }

        const payload = {
            playerNumber: playerType,
            pitNumber: idx,
            gameDTO: gameState
        };

        try {
            const newGameState = await axios.post(
                `${BASE_URL}/mancala/make-move`,
                payload
            );
            const { data } = newGameState;
            data.playerTwoSmallPits = data.playerTwoSmallPits.reverse()
            setGameState(data);

            if (!data.gameEnd) {
                setTurn(getCurrentTurn(data));
            } else {
                const endPayload = data;
                const scoreState = await axios.post(
                    `${BASE_URL}/mancala/end-game`,
                    endPayload
                );
                setScoreState(scoreState.data);
            }
        } catch (error) {
            const err = error?.response?.data;
            alert(`${err?.error}\n\n${err?.status}\n${err?.timestamp}`);
        }
    };

    async function fetchEndGameData() {
        return await axios.post(
            `${BASE_URL}/mancala/end-game`,
            gameState
        );
    }

    const buildBoardForPlayer = (player, pits) => {
        console.log('turn', turn)
        return (
            <div>
                {/*pits*/}
                {pits.map((pit, idx) => (
                    <button
                        style={{
                            margin: "2px",
                            width: "80px",
                            height: "80px",
                            backgroundColor: "#FAF0E6",
                            color: player.playerNumber === 0 ? "#A52A2a" : "#2f4f4f",
                            fontSize: "1rem",
                            borderRadius: "20px",
                            cursor: "pointer",
                            border: "2px solid black"
                        }}
                        onClick={() => (turn.type == player.playerNumber && pit > 0) ? handleBoardCellSelected(idx, player.playerNumber) : null}
                    >
                        {pit}
                    </button>
                ))}
            </div>
        );
    };

    return score != null ? <Scores scoreState={score} onReset={onReset} /> : (
        <div>
            <br />
            <br />
            <div></div>
            <div style={{ color: "#2f4f4f", fontSize: "2rem", marginBottom: "20px" }}> {turn.type == 1 ? "⛹️" : null} {playerTwo.name}</div>
            <div
                style={{
                    display: "flex",
                    flexDirection: "row",
                    justifyContent: "center",
                    alignItems: "center",
                    borderRadius: "10px",
                }}
            >
                {/* bigPit P2*/}
                <div
                    style={{
                        border: "2px solid black",
                        width: "5rem",
                        height: "10rem",
                        display: "flex",
                        justifyContent: "center",
                        alignItems: "center",
                        backgroundColor: "#FFFACD",
                        borderRadius: "20px",
                        fontSize: "2rem",
                        color: "#2f4f4f",
                    }}
                >
                    {gameState.playerTwoBigPit}
                </div>
                <div
                    style={{
                        margin: "2px",
                        border: "0px solid black",
                        height: "10rem",
                        display: "flex",
                        flexDirection: "column",
                        justifyContent: "center",
                        alignItems: "center",
                        backgroundColor: "white",
                        borderRadius: "20px",
                    }}
                >
                    {buildBoardForPlayer(playerTwo, playerTwoSmallPits)}
                    {buildBoardForPlayer(playerOne, playerOneSmallPits)}
                </div>
                {/* bigPit P1*/}
                <div
                    style={{
                        margin: "2px",
                        border: "2px solid black",
                        width: "5rem",
                        height: "10rem",
                        display: "flex",
                        justifyContent: "center",
                        alignItems: "center",
                        backgroundColor: "#FFFACD",
                        borderRadius: "20px",
                        fontSize: "2rem",
                        color: "#A52A2a",
                    }}
                >
                    {gameState.playerOneBigPit}
                </div>
            </div>
            <div>
                <div />
                <div style={{ color: "#A52A2a", fontSize: "2rem", marginTop: '20px' }}>{turn.type == 0 ? "⛹️" : null} {playerOne.name}</div>
            </div>
            <div>
                <Button variant="outline-dark" type="submit" style={{ marginTop: "20px" }} onClick={() => {
                    fetchEndGameData().then(({ data: score }) => { console.log('gotsocore', score); setScore(score) });
                }}>
                    End Game
                </Button>

            </div>
        </div>
    );
}