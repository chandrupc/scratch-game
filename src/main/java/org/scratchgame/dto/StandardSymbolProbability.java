package org.scratchgame.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

/* *
 * This class represents the probability of a standard symbol in the game.
 * It contains the row, column, and a map of symbols with their probabilities.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StandardSymbolProbability {
    private int row;
    private int column;
    private Map<String, Integer> symbols;

}
