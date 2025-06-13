package org.scratchgame.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Map;

/* This class represents the result of a game, including the board, reward, winning combinations, and applied bonus symbols. */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GameResult {

    @JsonProperty("board")
    private String[][] board;

    @JsonProperty("reward")
    private Double reward;

    @JsonProperty("winning_combinations")
    private Map<String, List<String>> winningCombinations;


    @JsonProperty("applied_bonus_symbol")
    private List<String> appliedBonusSymbol;
}