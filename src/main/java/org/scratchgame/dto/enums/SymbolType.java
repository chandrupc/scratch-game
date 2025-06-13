package org.scratchgame.dto.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/* This enum represents the different types of symbols that can be present in the game.
 * STANDARD: Represents a standard symbol with a reward value.
 * BONUS: Represents a bonus symbol with a reward multiplier.
 */
public enum SymbolType {
    STANDARD, BONUS;

    @JsonCreator
    public static SymbolType fromString(String value) {
        return value != null ? SymbolType.valueOf(value.toUpperCase()) : null;
    }
}
