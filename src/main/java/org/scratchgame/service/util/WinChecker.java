package org.scratchgame.service.util;

import org.scratchgame.dto.Position;
import org.scratchgame.dto.ScratchGameConfig;
import org.scratchgame.dto.WinCombination;
import org.scratchgame.dto.enums.SymbolType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* Utility class to check the winning combinations for the scratch game. */
public class WinChecker {

    public WinChecker() {
    }

    /**
     * @param board
     * @param config
     * @return
     */
    public Map<String, List<String>> checkWinningCombinations(String[][] board, ScratchGameConfig config) {
        Map<String, List<String>> winningCombinations = new HashMap<>();

        // Check same symbol combinations
        checkSameSymbolCombinations(board, config, winningCombinations);

        // Check linear combinations
        checkLinearCombinations(board, config, winningCombinations);

        return winningCombinations;
    }

    /**
     * @param board
     * @param config
     * @param winningCombinations
     */
    private void checkSameSymbolCombinations(String[][] board, ScratchGameConfig config,
                                             Map<String, List<String>> winningCombinations) {

        // Count occurrences of each standard symbol
        Map<String, Integer> symbolCounts = new HashMap<>();

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                String symbol = board[row][col];
                if (config.getSymbols().get(symbol) != null &&
                        config.getSymbols().get(symbol).getType().equals(SymbolType.STANDARD)) {
                    symbolCounts.put(symbol, symbolCounts.getOrDefault(symbol, 0) + 1);
                }
            }
        }

        // Check against win combinations
        for (Map.Entry<String, WinCombination> each : config.getWinCombinations().entrySet()) {
            if (each.getValue().isSameSymbolCombination()) {
                String key = each.getKey();
                WinCombination combo = each.getValue();
                symbolCounts.entrySet().stream()
                        .filter(entry -> entry.getValue() >= combo.getCount())
                        .forEach(entry -> {
                            winningCombinations.computeIfAbsent(entry.getKey(), k -> new ArrayList<>())
                                    .add(key);
                        });
            }
        }
    }

    /**
     * @param board
     * @param config
     * @param winningCombinations
     */
    private void checkLinearCombinations(String[][] board, ScratchGameConfig config,
                                         Map<String, List<String>> winningCombinations) {

        config.getWinCombinations().entrySet().stream()
                .filter(entry -> entry.getValue().isLinearCombination())
                .forEach(entry -> {
                    String combinationName = entry.getKey();
                    WinCombination combination = entry.getValue();

                    if (combination.getCoveredAreas() != null) {
                        combination.getCoveredAreas().forEach(area -> {
                            if (isLinearCombinationMatching(board, area)) {
                                String symbol = board[Position.fromString(area.get(0)).getRow()]
                                        [Position.fromString(area.get(0)).getColumn()];

                                if (config.getSymbols().get(symbol) != null &&
                                        config.getSymbols().get(symbol).getType().equals(SymbolType.STANDARD)) {
                                    winningCombinations.computeIfAbsent(symbol, k -> new ArrayList<>())
                                            .add(combinationName);
                                }
                            }
                        });
                    }
                });
    }

    /**
     * @param board
     * @param positions
     * @return
     */
    private boolean isLinearCombinationMatching(String[][] board, List<String> positions) {
        if (positions.isEmpty()) return false;

        Position firstPos = Position.fromString(positions.get(0));
        String firstSymbol = board[firstPos.getRow()][firstPos.getColumn()];

        return positions.stream()
                .map(Position::fromString)
                .allMatch(pos -> {
                    if (pos.getRow() >= board.length || pos.getColumn() >= board[0].length) {
                        return false;
                    }
                    return firstSymbol.equals(board[pos.getRow()][pos.getColumn()]);
                });
    }
}