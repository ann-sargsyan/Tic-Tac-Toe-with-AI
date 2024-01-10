package main.java.player.factory;

import main.java.player.Player;
import main.java.player.type.Easy;
import main.java.player.type.Medium;
import main.java.player.type.Hard;
import main.java.player.type.user.User;

public final class PlayerFactory {
    private static final String USER = "user";
    private static final String EASY = "easy";
    private static final String MEDIUM = "medium";
    private static final String HARD = "hard";

    private PlayerFactory() {
    }

    /**
     * Via this method we can dynamically choose type of player
     */
    public static Player create(String playerType) {
        return switch (playerType) {
            case USER -> new User();
            case EASY -> new Easy();
            case MEDIUM -> new Medium();
            case HARD -> new Hard();
            default -> null;
        };
    }

}
