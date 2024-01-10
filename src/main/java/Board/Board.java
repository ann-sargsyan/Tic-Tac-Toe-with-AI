package main.java.Board;

import main.java.Board.value.BoardValue;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {
    private static final String HORIZONTAL_LINE = "-";
    private static final String VERTICAL_LINE = "|";
    private static final int MAX_COUNT_OF_ELEMENTS = 9;
    public static int SIZE = 3;
    private final char[][] board;

    public Board() {
        board = new char[SIZE][SIZE];
        init();
    }

    public void init() {
        IntStream.range(0, SIZE).forEach(row -> IntStream.range(0, SIZE).
                forEach(col -> board[row][col] = BoardValue.EMPTY.symbol));
    }

    public int count(char cellValue) {
        return (int) IntStream.range(0, SIZE)
                .flatMap(row -> IntStream.range(0, SIZE)
                        .filter(col -> isCellEqual(row, col, cellValue))).count();
    }

    public boolean isEmpty(int x, int y) {
        return board[x][y] == BoardValue.EMPTY.symbol;
    }

    private boolean isCellEqual(int row, int col, char cellValue) {
        return board[row][col] == cellValue;
    }

    /**
     * The lineSeparator() is a built-in method in Java which returns the system-dependent line separator string.
     * It always returns the same value – the initial value of the system property line.separator.
     */
    @Override
    public String toString() {
        String horizontalLine = HORIZONTAL_LINE.repeat(MAX_COUNT_OF_ELEMENTS);
        String separator = System.lineSeparator();
        String boardString =
                IntStream.range(0, SIZE).mapToObj(row -> VERTICAL_LINE + " " +
                        IntStream.range(0, SIZE).mapToObj(col -> String.format("%c ", board[row][col]))
                                .collect(Collectors.joining()) + VERTICAL_LINE).collect(Collectors.joining(separator));

        return horizontalLine + separator + boardString + separator + horizontalLine + separator;
    }

    public static int getSize() {
        return SIZE;
    }

    public char getCell(int x, int y) {
        return board[x][y];
    }

    public void setCell(int x, int y, char val) {
        board[x][y] = val;
    }

    public char[][] getCurrentState() {
        return board;
    }
}
