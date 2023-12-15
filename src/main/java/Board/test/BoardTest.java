package main.java.Board.test;

import main.java.Board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;

    @BeforeEach
    public void initBoard() {
        board = new Board();
    }

    @Test
    void count() {
        // Given
        char cellValueToTest = 'X';
        // When
        board.setCell(1, 1, cellValueToTest);
        board.setCell(2, 2, cellValueToTest);
        int result = board.count(cellValueToTest);
        int expectedResult = 2;

        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    void isEmpty() {
        // Given
        board.setCell(2, 1, 'X');

        // When
        boolean result = board.isEmpty(2, 1);
        boolean expectedResult = false;

        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    void setCell() {
        // Given
        board.setCell(2, 2, 'O');

        // When
        char result = board.getCell(2, 2);
        char expectedResult = 'O';

        // Then
        assertEquals(expectedResult, result);
    }

}