package com.scratchgame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.scratchgame.dto.GameResult;
import org.scratchgame.dto.ScratchGameConfig;
import org.scratchgame.dto.enums.Impact;
import org.scratchgame.dto.enums.SymbolType;
import org.scratchgame.service.impl.ScratchGameServiceImpl;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ScratchGameTest {

    private ScratchGameServiceImpl gameService;
    private ScratchGameConfig testConfig;

    @BeforeEach
    void setUp() throws IOException {
        gameService = new ScratchGameServiceImpl();
        testConfig = gameService.loadConfigFile("src/test/resources/config.json", 100.0);
    }

    @Test
    void testGameConfigLoading() {
        assertNotNull(testConfig);
        assertEquals(3, testConfig.getColumns());
        assertEquals(3, testConfig.getRows());
        assertEquals(9, testConfig.getTotalCells());

        // Test symbols
        assertNotNull(testConfig.getSymbols().get("A"));
        assertEquals(5.0, testConfig.getSymbols().get("A").getRewardMultiplier());
        assertTrue(testConfig.getSymbols().get("A").getType().equals(SymbolType.STANDARD));

        assertNotNull(testConfig.getSymbols().get("+1000"));
        assertTrue(testConfig.getSymbols().get("+1000").getType().equals(SymbolType.BONUS));
        assertTrue(testConfig.getSymbols().get("+1000").getImpact().equals(Impact.EXTRA_BONUS));
        assertEquals(1000, testConfig.getSymbols().get("+1000").getExtra());
    }

    @Test
    void testGamePlay() {
        GameResult result = gameService.playGame();

        assertNotNull(result);
        assertNotNull(result.getBoard());
        assertEquals(3, result.getBoard().length);
        assertEquals(3, result.getBoard()[0].length);
        assertNotNull(result.getWinningCombinations());
        assertNotNull(result.getReward());
        assertTrue(result.getReward() >= 0);
    }

    @Test
    void testJsonOutput() throws IOException {
        GameResult result = gameService.playGame();
        String jsonOutput = gameService.convertResultToJson(result);

        assertNotNull(jsonOutput);
        assertTrue(jsonOutput.contains("board"));
        assertTrue(jsonOutput.contains("reward"));
        assertTrue(jsonOutput.contains("winning_combinations"));
    }

    @Test
    void testMultipleGames() {
        // Play multiple games to test randomness
        for (int i = 0; i < 5; i++) {
            GameResult result = gameService.playGame();
            assertNotNull(result);
            System.out.println("Game " + (i + 1) + " reward: " + result.getReward());
        }
    }
}