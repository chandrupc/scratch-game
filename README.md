# Scratch Game Project

A Java Maven implementation of a scratch card game with configurable symbols, probabilities, and win combinations.

## Features

- **Configurable Game Rules**: JSON-based configuration for rows, columns, symbols, probabilities, and win combinations
- **Lombok Integration**: Clean, annotation-based model classes
- **Jackson JSON Processing**: Seamless JSON serialization/deserialization
- **Comprehensive Game Logic**:
    - Board generation based on probabilities
    - Win combination detection (same symbols, linear patterns)
    - Reward calculation with bonus symbol effects
- **Output Formats**: JSON output

## Project Structure

```
src/
├── main/java/org/scratchgame/
│   ├── ScratchGameApplication.java     # Main application entry point
│   ├── dto/
│   │   ├── enums/ 
│   │   │    ├── Impact.java            # Enum representing the impact of a symbol on the game's outcome.
│   │   │    └── SymbolType.java        # Different types of symbols that can be present in the game.
│   │   ├── BonusSymbols.java           # Bonus Symbols model
│   │   ├── Position.java               # Linear Symbols model
│   │   ├── ScratchGameConfig.java      # Main configuration model
│   │   ├── Symbol.java                 # Symbol configuration
│   │   ├── Probabilities.java          # Probability configurations
│   │   ├── WinCombination.java         # Win combination rules
│   │   └── GameResult.java             # Game result model
│   └── service/
│       ├── ScratchGameService.java     # Main game service
│       ├── MatrixGenerator.java        # Game matrix generation
│       ├── WinChecker.java            # Win combination detection
│       └── RewardCalculator.java       # Reward calculation logic
├── test/java
```

## Requirements

- Java 17+
- Maven 3.6+

## Dependencies

- **Lombok 1.18.38**: Annotation-based code generation
- **Jackson Databind 2.19.0**: JSON processing
- **JUnit 5.10.1**: Testing framework

## Setup and Installation

1. **Clone or create the project structure**
2. **Add your configuration file** to `src/main/resources/config.json`
3. **Build the project**:
   ```bash
   mvn clean compile
   ```
4. **Run tests**:
   ```bash
   mvn test
   ```
5. **Run the application**:
   ```bash
   mvn exec:java -Dexec.mainClass="com.scratchgame.ScratchGameApplication"
   ```

## Configuration Format

The game uses a JSON configuration file with the following structure:
For example please take a look at src/resources/config.json file

```json
{
  "columns": 3,
  "rows": 3,
  "symbols": {
    "A": {
      "reward_multiplier": 5,
      "type": "standard"
    },
    "+1000": {
      "extra": 1000,
      "type": "bonus",
      "impact": "extra_bonus"
    }
  },
  "probabilities": {
    "standard_symbols": [...],
    "bonus_symbols": {...}
  },
  "win_combinations": {
    "same_symbol_3_times": {
      "reward_multiplier": 1,
      "when": "same_symbols",
      "count": 3
    }
  }
}
```

## Game Output Format

The game produces output in the following JSON format:

```json
{
  "matrix": [
    ["A", "A", "B"],
    ["A", "+1000", "B"],
    ["A", "A", "B"]
  ],
  "reward": 6600,
  "applied_winning_combinations": {
    "A": ["same_symbol_5_times", "same_symbols_vertically"],
    "B": ["same_symbol_3_times", "same_symbols_vertically"]
  },
  "applied_bonus_symbol": "+1000"
}
```

## Key Classes and Annotations

### Model Classes (with Lombok)

- `@Data`: Generates getters, setters, toString, equals, hashCode
- `@NoArgsConstructor`: Generates no-argument constructor
- `@AllArgsConstructor`: Generates constructor with all arguments
- `@Builder`: Generates builder pattern implementation

### Jackson Annotations

- `@JsonProperty`: Maps JSON property names to Java fields
- Automatic serialization/deserialization of complex objects

## Game Logic

1. **Board Generation**: Creates a game matrix based on symbol probabilities
2. **Win Detection**: Checks for:
    - Same symbol combinations (3+ matching symbols)
    - Linear combinations (horizontal, vertical, diagonal)
3. **Reward Calculation**:
    - Base reward = bet × symbol multiplier × all combination multiplier
    - Bonus symbols can multiply rewards or add fixed amounts
4. **Result Output**: Provides both human-readable and JSON formats

## Usage Examples

### Programmatic Usage

```java
ScratchGameService gameService = new ScratchGameService();
GameConfig config = gameService.loadGameConfig("config.json");
GameResult result = gameService.playGame(100.0, config);

// Get JSON output
String jsonResult = gameService.convertResultToJson(result);
System.out.println(jsonResult);
```

### Command Line Usage

Run the application and follow the prompts:
```bash
 java -jar <your-jar-file> --config <config_file_path> --betting-amount <betting_amount>
```

## Testing

The project includes comprehensive tests covering:
- Configuration loading
- Game logic
- JSON serialization
- Multiple game scenarios

Run tests with:
```bash
mvn test
```

## Maven Commands

- **Clean and compile**: `mvn clean compile`
- **Run tests**: `mvn test`
- **Run application**: `mvn exec:java`
- **Package JAR**: `mvn package`
- **Install to local repository**: `mvn install`

## Customization

You can customize the game by modifying:
- **Symbols**: Add new symbols with different multipliers and types
- **Probabilities**: Adjust symbol occurrence probabilities
- **Win Combinations**: Define new winning patterns and multipliers
- **Matrix Size**: Change the game grid dimensions

The game is fully configurable through the JSON configuration file without requiring code changes.