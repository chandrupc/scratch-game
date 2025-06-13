package org.scratchgame.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/* *
 * This class represents a winning combination in the game.
 * It contains the reward multiplier, the condition for winning, the group of symbols,
 * the count of symbols required, and the covered areas where the winning combination applies.
 * Example:
{
    "reward_multiplier": 2,
    "when": "same_symbols",
    "count": 2,
    "group": "same_symbols",
    "covered_areas": [
        [
            "0,0",
            "0,1"
        ]
    ]
}
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WinCombination {

    @JsonProperty("reward_multiplier")
    private double rewardMultiplier;
    private String when;
    private String group;
    private Integer count;
    @JsonProperty("covered_areas")
    private List<List<String>> coveredAreas;

    public boolean isSameSymbolCombination() {
        return "same_symbols".equals(when);
    }

    public boolean isLinearCombination() {
        return "linear_symbols".equals(when);
    }
}
