package org.scratchgame.service.util;

import lombok.extern.slf4j.Slf4j;
import org.scratchgame.dto.ScratchGameConfig;
import org.scratchgame.dto.Symbol;
import org.scratchgame.dto.enums.Impact;
import org.scratchgame.dto.enums.SymbolType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RewardCalculator {

    public RewardCalculator() {
    }

    public double calculateReward(double betAmount, String[][] board,
                                  Map<String, List<String>> winningCombinations,
                                  ScratchGameConfig config) {

        double totalReward = 0.0;

        // Calculate rewards for each winning symbol
        for (Map.Entry<String, List<String>> entry : winningCombinations.entrySet()) {
            String symbol = entry.getKey();
            List<String> combinations = entry.getValue();

            double symbolReward = calculateSymbolReward(betAmount, symbol, combinations, config);
            totalReward += symbolReward;

        }

        // Apply bonus symbol effects
        List<String> bonusSymbol = findBonusSymbol(board, config);
        if (bonusSymbol != null && bonusSymbol.size() > 0) {
            totalReward = applyBonusSymbol(totalReward, bonusSymbol, config);
        }

        return totalReward;
    }

    private double calculateSymbolReward(double betAmount, String symbolName,
                                         List<String> combinations, ScratchGameConfig config) {

        Symbol symbol = config.getSymbols().get(symbolName);
        if (symbol == null) {
            return 0.0;
        }

        double symbolMultiplier = symbol.getRewardMultiplier();
        double combinationMultiplier = 1.0 * betAmount * symbolMultiplier;

        // Multiply all the winning combinations reward multipliers
        for (String combinationName : combinations) {
            var winCombination = config.getWinCombinations().get(combinationName);
            if (winCombination != null) {
                combinationMultiplier *= winCombination.getRewardMultiplier();
            }
        }

        return combinationMultiplier;
    }

    public List<String> findBonusSymbol(String[][] board, ScratchGameConfig config) {
        List<String> bonusSymbols = new ArrayList<>();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                String symbol = board[row][col];
                Symbol symbolConfig = config.getSymbols().get(symbol);
                if (symbolConfig != null && symbolConfig.getType().equals(SymbolType.BONUS)
                        && !symbolConfig.getImpact().equals(Impact.MISS)) {
                    bonusSymbols.add(symbol);
                }
            }
        }
        return bonusSymbols;
    }

    private double applyBonusSymbol(double currentReward, List<String> bonusSymbols, ScratchGameConfig config) {
        for (String bonusSymbol : bonusSymbols) {
            Symbol symbol = config.getSymbols().get(bonusSymbol);
            if (symbol == null) {
                continue;
            }

            if (symbol.getImpact().equals(Impact.MULTIPLY_REWARD)) {
                currentReward = currentReward * symbol.getRewardMultiplier();
            } else if (symbol.getImpact().equals(Impact.EXTRA_BONUS)) {
                currentReward = currentReward + symbol.getExtra();
            }

        }
        return currentReward;
    }
}