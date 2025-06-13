package org.scratchgame.dto;

import lombok.Data;

import java.util.Map;

/* This class represents the configuration for the Scratch Game.
 * It contains the probability of each symbol and the reward multiplier for each symbol.
 */
@Data
public class BonusSymbols {
    Map<String, Integer> symbols;
}
