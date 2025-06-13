package org.scratchgame.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/* This class represents the probabilities of different symbols and bonus symbols in the game. */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Probabilities {
    @JsonProperty("standard_symbols")
    private List<StandardSymbolProbability> standardSymbols;
    @JsonProperty("bonus_symbols")
    private BonusSymbols bonusSymbols;
}
