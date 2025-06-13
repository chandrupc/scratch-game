package org.scratchgame;

import org.scratchgame.dto.ScratchGameConfig;
import org.scratchgame.service.ScratchGameService;
import org.scratchgame.service.impl.ScratchGameServiceImpl;

import java.io.File;

/*
 * This is the main class of the application.
 * It serves as the entry point for the game.
 */
public class ScratchGameApplication {

    /* The main method is the entry point of the application.
     */
    public static void main(String[] args) {
        String configPath = null;
        double bettingAmount = -1;

        ScratchGameService scratchGameService = new ScratchGameServiceImpl();

        try {
            for (int i = 0; i < args.length; i++) {
                if ("--config".equals(args[i])) {
                    configPath = args[++i];
                } else if ("--betting-amount".equals(args[i])) {
                    bettingAmount = Double.parseDouble(args[++i]);
                } else {
                    throw new IllegalArgumentException("Unknown argument: " + args[i]);
                }
            }

            // Validation
            if (configPath == null || !new File(configPath).exists()) {
                throw new IllegalArgumentException("Missing or invalid --config file path");
            }
            if (bettingAmount <= 0) {
                throw new IllegalArgumentException("Invalid or missing --betting-amount");
            }

            System.out.println("Config File Path provided : " + configPath);
            System.out.println("Betting Amount by User: " + bettingAmount);

            scratchGameService.loadConfigFile(configPath, bettingAmount);

            scratchGameService.playGame();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("Usage: java -jar game.jar --config <file.json> --betting-amount <amount>");
            System.exit(1);
        }
    }
}