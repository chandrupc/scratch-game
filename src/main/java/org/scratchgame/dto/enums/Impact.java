package org.scratchgame.dto.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/* Enum representing the impact of a symbol on the game's outcome. */
public enum Impact {
    MULTIPLY_REWARD, EXTRA_BONUS, MISS;

    @JsonCreator
    public static Impact fromString(String value) {
        return value != null ? Impact.valueOf(value.toUpperCase()) : null;
    }
}
