package main.java.game;

import main.java.Board.Board;
import main.java.game.state.GameState;
import main.java.player.Player;
import main.java.player.factory.PlayerFactory;
import main.java.player.move.Move;
import main.java.Board.value.BoardValue;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * Here is all main logic related to game
 */
public class Game {
    private static final char PLAYER_X = 'X';
    private static final char PLAYER_O = 'O';
    private static final char EMPTY_CELL = ' ';
    private static final int SIGN_OF_ONE = 1;
    private static final int SIGN_OF_THREE = 3;
    private static final String MATCH_WHITESPACES = "\\s+";
    private static final String START = "start";
    private static final List<String> VALID_VALUES = Arrays.asList("user", "easy", "medium", "hard");
    private static final String VALIDATION_MESSAGE = "Please enter valid command for start.";

    public static Board getBoard() {
        return board;
    }

    public static final Board board = new Board();


    public void newGame() {
        board.init();
    }

    public void start() {
        while (true) {
            String[] inputType = input();
            newGame();
            System.out.print(Game.board);
            Player firstPlayer = PlayerFactory.create(inputType[1]);
            Player secondPlayer = PlayerFactory.create(inputType[2]);
            int count = 0;
            int i = 0;
            while (count < 9) {
                i = count % 2;
                if (i == 0)
                    firstPlayer.move();
                else
                    secondPlayer.move();
                System.out.print(Game.board);
                if (currentState() != GameState.GAME_NOT_FINISHED) {
                    System.out.println(currentState().toString());
                    break;
                }
                count++;
            }
        }
    }

    public static Move findEmptyCell(char playerType) {
        for (int i = 0; i < Board.getSize(); i++) {
            if (board.getCell(i, 0) == playerType && board.getCell(i, 1) == playerType && board.isEmpty(i, 2)) {
                return new Move(i, 2);
            }
            if (board.getCell(i, 0) == playerType && board.getCell(i, 2) == playerType && board.isEmpty(i, 1)) {
                return new Move(i, 1);
            }
            if (board.getCell(i, 1) == playerType && board.getCell(i, 2) == playerType && board.isEmpty(i, 0)) {
                return new Move(i, 0);
            }
            if (board.getCell(0, i) == playerType && board.getCell(1, i) == playerType && board.isEmpty(2, i)) {
                return new Move(2, i);
            }
            if (board.getCell(0, i) == playerType && board.getCell(2, i) == playerType && board.isEmpty(1, i)) {
                return new Move(1, i);
            }
            if (board.getCell(1, i) == playerType && board.getCell(2, i) == playerType && board.isEmpty(0, i)) {
                return new Move(0, i);
            }
        }
        return new Move(-1, -1);
    }

    public static Move getRandomMove() {
        Random random = new Random();
        int x = random.nextInt(SIGN_OF_THREE);
        int y = random.nextInt(SIGN_OF_THREE);

        while (!board.isEmpty(x, y)) {
            x = random.nextInt(SIGN_OF_THREE);
            y = random.nextInt(SIGN_OF_THREE);
        }
        return new Move(x, y);
    }

    public static char getCurrentPlayer() {
        int countX = board.count(PLAYER_X);
        int countO = board.count(PLAYER_O);
        if (countX == countO) {
            return PLAYER_X;
        }
        if (countX > countO) {
            return PLAYER_O;
        }
        return EMPTY_CELL;
    }

    public static GameState currentState() {
        if (isWinner(PLAYER_X)) {
            return GameState.X_WINS;
        }
        if (isWinner(PLAYER_O)) {
            return GameState.O_WINS;
        }
        int countEmpty = board.count(BoardValue.EMPTY.symbol);
        if (countEmpty == 0) {
            return GameState.DRAW;
        }
        return GameState.GAME_NOT_FINISHED;
    }

    private String[] input() {
        Scanner scanner = new Scanner(System.in);
        String[] inputArray = convertInputToString(scanner);
        while (!validateInput(inputArray)) {
            inputArray = convertInputToString(scanner);
        }
        return inputArray;
    }

    private String[] convertInputToString(Scanner scanner) {
        String inputLine = scanner.nextLine();
        return inputLine.trim().split(MATCH_WHITESPACES);
    }

    private boolean validateInput(String[] inputArray) {
        return validateLengthOfInput(inputArray) && validateInputOfStart(inputArray) &&
                validateInputOfPlayersType(inputArray, 1) && validateInputOfPlayersType(inputArray, 2);
    }

    private boolean validateInputOfStart(String[] inputArray) {
        if (!START.equals(inputArray[0])) {
            System.out.println(VALIDATION_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean validateInputOfPlayersType(String[] inputType, int index) {
        if (!VALID_VALUES.contains(inputType[index])) {
            System.out.println(VALIDATION_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean validateLengthOfInput(String[] inputArray) {
        if (inputArray.length != SIGN_OF_THREE) {
            System.out.println(VALIDATION_MESSAGE);
            return false;
        }
        return true;
    }

    private static boolean isWinner(char valueType) {
        return isWinnerInRows(valueType) ||
                isWinnerInColumns(valueType) ||
                isWinnerInDiagonals(valueType);
    }

    private static boolean isWinnerInRows(char valueType) {
        return IntStream.range(0, Board.getSize())
                .filter(i -> IntStream.range(0, Board.getSize()).noneMatch(j -> isThereMatch(valueType, i, j)))
                .findFirst()
                .isPresent();
    }

    private static boolean isWinnerInColumns(char valueType) {
        return IntStream.range(0, Board.getSize())
                .filter(i -> IntStream.range(0, Board.getSize()).noneMatch(j -> isThereMatch(valueType, j, i)))
                .findFirst()
                .isPresent();
    }

    private static boolean isWinnerInDiagonals(char valueType) {
        //By using (Board.getSIZE() - i - SIGN_OF_ONE) we are getting index for diagonal from right to left
        return IntStream.range(0, Board.getSize())
                .noneMatch(i -> isThereMatch(valueType, i, i) && isThereMatch(valueType, i, Board.getSize() - i - SIGN_OF_ONE));
    }

    private static boolean isThereMatch(char valueType, int firstIndex, int secondIndex) {
        return board.getCell(firstIndex, secondIndex) != valueType;
    }
}
