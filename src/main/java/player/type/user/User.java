package main.java.player.type.user;

import main.java.Board.Board;
import main.java.player.move.Move;
import main.java.game.Game;
import main.java.player.Player;

import java.util.Scanner;

public class User extends Player {

    private static final String ENTER_COORDINATES = "Enter the coordinates: ";
    private static final String ENTER_NUMBERS = "You should enter numbers!";

    private static final String CELL_OCCUPIED = "This cell is occupied! Choose another one!";

    private static final String COORDINATES_INTERVAL = "Coordinates should be from 1 to 3!";
    private static final String RID_SPACES = "\\s+";
    private static final String DIGITS = "\\d";
    private static final String INTERVAL = "[1-3]";
    private static final int SIGN_OF_ZERO = 0;
    private static final int SIGN_OF_ONE = 1;
    private static final int SIGN_OF_TWO = 2;

    public void move() {
        Board board = Game.getBoard();
        Move move = readInput(board);
        char currentPlayer = Game.getCurrentPlayer();
        board.setCell(move.x, move.y, currentPlayer);
    }

    private Move readInput(Board board) {
        while (true) {
            String[] coordinates = getCoordinatesFromInput();
            if (coordinates.length != SIGN_OF_TWO) {
                System.out.println(ENTER_NUMBERS);
                continue;
            }
            boolean valid = true;
            for (String coordinate : coordinates) {
                if (!coordinate.matches(DIGITS)) {
                    System.out.println(ENTER_NUMBERS);
                    valid = false;
                    break;
                }
                if (!coordinate.matches(INTERVAL)) {
                    System.out.println(COORDINATES_INTERVAL);
                    valid = false;
                    break;
                }
            }
            if (!valid) {
                continue;
            }
            int x = Integer.parseInt(coordinates[SIGN_OF_ZERO]) - SIGN_OF_ONE;
            int o = Integer.parseInt(coordinates[SIGN_OF_ONE]) - SIGN_OF_ONE;
            if (!board.isEmpty(x, o)) {
                System.out.println(CELL_OCCUPIED);
                continue;
            }
            return new Move(x, o);
        }
    }

    /**
     * This method getting coordinates from input
     */
    private String[] getCoordinatesFromInput() {
        System.out.println(ENTER_COORDINATES);
        Scanner reader = new Scanner(System.in);
        return reader.nextLine().split(RID_SPACES);
    }
}
