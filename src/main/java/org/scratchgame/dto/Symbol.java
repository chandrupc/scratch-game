package org.scratchgame.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.scratchgame.dto.enums.Impact;
import org.scratchgame.dto.enums.SymbolType;


/* Represents a symbol in the game configuration.

Example:
"reward_multiplier": 0.5,
"type": "standard",
"extra": 0,
"impact": "multiply"
*/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Symbol {
    @JsonProperty("reward_multiplier")
    double rewardMultiplier;
    SymbolType type;
    float extra;
    Impact impact;


}
