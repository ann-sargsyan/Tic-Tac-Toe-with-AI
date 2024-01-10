package main.java.player.type;

import main.java.Board.Board;
import main.java.Board.value.BoardValue;
import main.java.game.Game;
import main.java.player.Player;
import main.java.player.move.Move;

public class Medium extends Player {
    @Override
    public void move() {
        System.out.println("Medium");
        Board board = Game.getBoard();
        char currentPlayer = Game.getCurrentPlayer();
        Move move = findNextMove(currentPlayer);
        board.setCell(move.x, move.y, currentPlayer);
    }

    private Move findNextMove(char currentPlayer) {
        Move move = Game.findEmptyCell(currentPlayer);
        if (move.x == -1) {
            if (currentPlayer == BoardValue.O.symbol) {
                move = Game.findEmptyCell(BoardValue.X.symbol);
            } else {
                move = Game.findEmptyCell(BoardValue.O.symbol);
            }
            if (move.x == -1) {
                move = Game.getRandomMove();
            }
        }
        return move;
    }
}
