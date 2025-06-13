package org.scratchgame.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.scratchgame.dto.GameResult;
import org.scratchgame.dto.ScratchGameConfig;
import org.scratchgame.dto.enums.Impact;
import org.scratchgame.dto.enums.SymbolType;
import org.scratchgame.service.ScratchGameService;
import org.scratchgame.service.util.BoardGeneratorUtility;
import org.scratchgame.service.util.RewardCalculator;
import org.scratchgame.service.util.WinChecker;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class ScratchGameServiceImpl implements ScratchGameService {

    private ObjectMapper objectMapper;

    private double bettingAmount;

    private ScratchGameConfig scratchGameConfig;
    private BoardGeneratorUtility boardGeneratorUtility;

    private WinChecker winchecker;

    private RewardCalculator rewardCalculator;

    public ScratchGameServiceImpl() {
        this.objectMapper = new ObjectMapper();
        this.bettingAmount = 0;
        this.boardGeneratorUtility = new BoardGeneratorUtility();
        this.winchecker = new WinChecker();
        this.rewardCalculator = new RewardCalculator();
    }


    @Override
    public ScratchGameConfig loadConfigFile(String configPath, double bettingAmount) {
        // set the betting amount
        this.bettingAmount = bettingAmount;
        // read the content from configPath
        try {
            ScratchGameConfig scratchGameConfig = objectMapper.readValue(new File(configPath), ScratchGameConfig.class);
            System.out.println("Loaded config file from path : " + configPath);
            this.scratchGameConfig = scratchGameConfig;
        } catch (Exception e) {
            System.err.println("Error reading config file: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return scratchGameConfig;
    }

    @Override
    public GameResult playGame() {
        System.out.println("Starting a new game with betting amount: " + this.bettingAmount);
        System.out.println("Configurations: " + this.scratchGameConfig.toString().substring(0, 100) + ".....");

        // 1. Generate the board
        String[][] board = boardGeneratorUtility.generateBoard(scratchGameConfig);

//        System.out.println("Generated Board is ");
//        boardGeneratorUtility.printBoard(board);

        // 2. Evaluate win conditions
        Map<String, List<String>> winningCombinations = winchecker.checkWinningCombinations(board, scratchGameConfig);

        // 3. Calculate reward
        double reward = rewardCalculator.calculateReward(bettingAmount, board, winningCombinations, scratchGameConfig);

        // Find applied bonus symbol
        List<String> appliedBonusSymbol = rewardCalculator.findBonusSymbol(board, scratchGameConfig);

        System.out.println("Game completed. Reward: " + reward + " Winning combinations: " + winningCombinations.size());

        GameResult results = GameResult.builder()
                .board(board)
                .winningCombinations(winningCombinations)
                .reward(reward)
                .appliedBonusSymbol(appliedBonusSymbol)
                .build();

        printGameResultAsJson(results);
        return results;

    }

    public String convertResultToJson(GameResult result) throws IOException, JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
    }

    public void printGameResultAsJson(GameResult result) {
        try {
            System.out.println("\n=== GAME RESULT (JSON) ===");
            System.out.println(convertResultToJson(result));
            System.out.println("==========================");
        } catch (IOException e) {
            System.err.println("Error on converting json ");
        }
    }
}
