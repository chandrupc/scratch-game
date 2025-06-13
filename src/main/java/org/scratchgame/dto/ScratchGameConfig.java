package org.scratchgame.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

/*
 *
 * This class represents the configuration of a game.
 * It contains information about the game board dimensions,
 * symbols, probabilities, and win combinations.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class ScratchGameConfig {

    int columns = 3; // optional
    int rows = 3; // optional

    Map<String, Symbol> symbols;
    Probabilities probabilities;
    @JsonProperty("win_combinations")
    private Map<String, WinCombination> winCombinations;

    public int getTotalCells() {
        return columns * rows;
    }

}
