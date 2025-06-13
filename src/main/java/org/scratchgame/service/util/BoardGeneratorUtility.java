package org.scratchgame.service.util;

import org.scratchgame.dto.ScratchGameConfig;
import org.scratchgame.dto.StandardSymbolProbability;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;

/* *
 * Utility class to generate the board for the scratch game.
 */
public class BoardGeneratorUtility {

    Random random;

    public BoardGeneratorUtility() {
        this.random = new Random();
    }

    /* *
     * Generates the board based on the configuration.
     * @param config
     * @return
     */
    public String[][] generateBoard(ScratchGameConfig config) {
        int rows = config.getRows();
        int columns = config.getColumns();
        String[][] board = new String[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                Map<String, Integer> weights = getAllPossibleSymbolsForCell(config, row, col);
                board[row][col] = getRandomSymbol(weights);
            }
        }
        return board;
    }

    /* *
     * Utility method to get all possible symbols for a cell.
     * @param config
     * @param row
     * @param col
     * @return
     */
    private Map<String, Integer> getAllPossibleSymbolsForCell(ScratchGameConfig config, int row, int col) {
        for (StandardSymbolProbability entry : config.getProbabilities().getStandardSymbols()) {
            if (entry.getRow() == row && entry.getColumn() == col) {
                return entry.getSymbols();
            }
        }
        // fill in the bonus symbols if it's not matching the row column pair
        return config.getProbabilities().getBonusSymbols().getSymbols();
    }

    /* *
     * Utility method to get the random symbol based on the weights.
     * @param weights
     * @return
     */
    private String getRandomSymbol(Map<String, Integer> weights) {
        int total = weights.values().stream().mapToInt(i -> i).sum();
        int r = random.nextInt(total);

        int cumulative = 0;
        for (Map.Entry<String, Integer> entry : weights.entrySet()) {
            cumulative += entry.getValue();
            if (r < cumulative) {
                return entry.getKey();
            }
        }
        // default fallback first symbol if not matching
        return weights.keySet().iterator().next();
    }

    /* *
     * Utility method to print the board.
     * @param board
     */
    public void printBoard(String[][] board) {
        for (String[] row : board) {
            System.out.println(Arrays.toString(row));
        }
    }
}
