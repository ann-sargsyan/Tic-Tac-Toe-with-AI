package main.java.player.type;

import main.java.Board.Board;
import main.java.Board.value.BoardValue;
import main.java.game.Game;
import main.java.player.Player;
import main.java.player.move.Move;

public class Hard extends Player {
    @Override
    public void move() {
        System.out.println("Hard");
        Board board = Game.getBoard();
        char currentPlayer = Game.getCurrentPlayer();
        Minimax minimax = new Minimax(currentPlayer);
        Move move = minimax.findBestMove(Game.getBoard().getCurrentState());
        move = minimax.handleMoveToCentre(move, board);
        board.setCell(move.x, move.y, currentPlayer);
    }

    static class Minimax {

        private char player = BoardValue.X.symbol;
        private char opponent = BoardValue.O.symbol;
        private static final int SIGN_OF_ZERO = 0;
        private static final int SIGN_OF_ONE = 1;
        private static final int SIGN_OF_TWO = 2;

        public Minimax(char player) {
            this.player = player;
            opponent = player == BoardValue.X.symbol ? BoardValue.O.symbol : BoardValue.X.symbol;
        }

        private Move handleMoveToCentre(Move move, Board board){
            if (move.x == -1) {
                if (board.isEmpty(2, 2)) {
                    move.x = 2;
                    move.y = 2;
                } else {
                    move = Game.getRandomMove();
                }
            }
            return move;
        }

        private Move findBestMove(char[][] board) {
            int bestVal = -1000;
            Move bestMove = new Move(-1, -1);

            for (int i = 0; i < Board.SIZE; i++) {
                for (int j = 0; j < Board.SIZE; j++) {
                    if (board[i][j] == BoardValue.EMPTY.symbol) {
                        board[i][j] = player;
                        int moveVal = minimax(board, 0, false);
                        board[i][j] = BoardValue.EMPTY.symbol;

                        if (moveVal > bestVal) {
                            bestMove.x = i;
                            bestMove.y = j;
                            bestVal = moveVal;
                        }
                    }
                }
            }
            return bestMove;
        }

        private int minimax(char[][] board, int depth, boolean isMax) {
            int score = evaluate(board);

            if (score != 0 || !isMovesLeft(board)) {
                return score;
            }

            int best = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;

            for (int i = 0; i < Board.SIZE; i++) {
                for (int j = 0; j < Board.SIZE; j++) {
                    if (board[i][j] == BoardValue.EMPTY.symbol) {
                        char currentPlayer = isMax ? player : opponent;
                        board[i][j] = currentPlayer;

                        int currentScore = minimax(board, depth + 1, !isMax);
                        best = isMax ? Math.max(best, currentScore) : Math.min(best, currentScore);

                        board[i][j] = BoardValue.EMPTY.symbol;
                    }
                }
            }
            return best;
        }

        private boolean isMovesLeft(char[][] board) {
            for (int i = 0; i < Board.SIZE; i++) {
                for (int j = 0; j < Board.SIZE; j++) {
                    if (board[i][j] == BoardValue.EMPTY.symbol) {
                        return true;
                    }
                }
            }
            return false;
        }

        private int evaluate(char[][] board) {
            for (int row = 0; row < Board.SIZE; row++) {
                if (board[row][SIGN_OF_ZERO] == board[row][SIGN_OF_ONE] &&
                        board[row][SIGN_OF_ONE] == board[row][SIGN_OF_TWO]) {
                    if (board[row][SIGN_OF_ZERO] == player) {
                        return +10;
                    } else if (board[row][SIGN_OF_ZERO] == opponent) {
                        return -10;
                    }
                }
            }

            for (int col = 0; col < Board.SIZE; col++) {
                if (board[SIGN_OF_ZERO][col] == board[SIGN_OF_ONE][col] &&
                        board[SIGN_OF_ONE][col] == board[SIGN_OF_TWO][col]) {
                    if (board[SIGN_OF_ZERO][col] == player) {
                        return +10;
                    } else if (board[SIGN_OF_ZERO][col] == opponent) {
                        return -10;
                    }
                }
            }

            if (checkDiagonal(board, player)) {
                return +10;
            } else if (checkDiagonal(board, opponent)) {
                return -10;
            }
            return 0;
        }

        private boolean checkDiagonal(char[][] board, char playerType) {
            boolean forwardDiagonal = true, backwardDiagonal = true;

            for (int i = 0; i < Board.SIZE; i++) {
                forwardDiagonal &= board[i][i] == playerType;
                backwardDiagonal &= board[i][Board.SIZE - i - SIGN_OF_ONE] == playerType;
            }
            return forwardDiagonal || backwardDiagonal;
        }
    }
}

