package main.java.game.state;

public enum GameState {
    GAME_NOT_FINISHED("Game not finished"),
    DRAW("Draw"),
    X_WINS("X wins"),
    O_WINS("O wins");

    private final String message;

    GameState(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
