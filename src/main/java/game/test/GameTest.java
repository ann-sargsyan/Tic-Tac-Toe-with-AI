package main.java.game.test;

import main.java.Board.Board;
import main.java.game.Game;
import main.java.game.state.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {

    Board board;

    Game game;

    @BeforeEach
    public void startGame() {
        game = new Game();
        board = new Board();
        game.newGame();
    }

    @Test
    void getCurrentPlayer() {
        // Given
        board.setCell(1, 1, 'X');
        board.setCell(1, 2, 'O');

        //When
        char result = Game.getCurrentPlayer();
        char expectedResult = 'X';

        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    void currentState() {
        // Given
        board.setCell(2, 1, 'X');
        board.setCell(2, 2, 'O');

        // When
        GameState result = game.currentState();
        GameState expectedResult = GameState.GAME_NOT_FINISHED;

        // Then
        assertEquals(expectedResult, result);

    }
}