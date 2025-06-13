package org.scratchgame.service;

import org.scratchgame.dto.GameResult;
import org.scratchgame.dto.ScratchGameConfig;

public interface ScratchGameService {

    ScratchGameConfig loadConfigFile(String configPath, double bettingAmount);

    GameResult playGame();
}
