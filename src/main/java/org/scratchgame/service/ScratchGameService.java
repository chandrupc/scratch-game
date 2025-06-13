package org.scratchgame.service;

import org.scratchgame.dto.GameResult;
import org.scratchgame.dto.ScratchGameConfig;

/* This interface defines the contract for the ScratchGameService. */
public interface ScratchGameService {

    ScratchGameConfig loadConfigFile(String configPath, double bettingAmount);

    GameResult playGame();
}
