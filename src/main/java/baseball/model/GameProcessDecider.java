package baseball.model;

import java.util.Arrays;

import static baseball.utils.ExceptionConstants.GameProcessCommandException.INVALID_COMMAND;

public enum GameProcessDecider {
    GAME_RESTART(1),
    GAME_END(2),
    ;

    private final int command;

    GameProcessDecider(final int command) {
        this.command = command;
    }

    public static GameProcessDecider getDecider(final int userCommand) {
        return Arrays.stream(values())
                .filter(restartDecider -> restartDecider.command == userCommand)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_COMMAND.message));
    }
}