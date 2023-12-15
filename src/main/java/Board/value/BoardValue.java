package main.java.Board.value;

public enum BoardValue {
    EMPTY(' '),
    X('X'),
    O('O');

    public final char symbol;

    BoardValue(char cellSymbol) {
        this.symbol = cellSymbol;
    }
}
