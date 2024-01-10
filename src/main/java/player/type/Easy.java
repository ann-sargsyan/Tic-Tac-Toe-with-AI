package main.java.player.type;

import main.java.Board.Board;
import main.java.player.move.Move;
import main.java.game.Game;
import main.java.player.Player;

public class Easy extends Player {

    public void move() {
        System.out.println("Easy");
        Board board = Game.getBoard();
        char currentPlayer = Game.getCurrentPlayer();
        Move move = Game.getRandomMove();
        board.setCell(move.x, move.y, currentPlayer);
    }

}
