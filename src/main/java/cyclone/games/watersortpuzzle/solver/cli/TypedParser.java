package cyclone.games.watersortpuzzle.solver.cli;

import cyclone.games.watersortpuzzle.solver.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Parse command line arguments.
 */
public class TypedParser {

    public Color[][] getTubes(CommandLine cmd) {
        return requireParsedValue(cmd, "puzzle");
    }

    public WinCondition getWinCondition(CommandLine cmd) {
        return optParsedValue(cmd, "win-condition", WinCondition.class)
                .orElse(WinCondition.STANDARD);
    }

    public Solver getSolver(CommandLine cmd) {
        String solverOption = cmd.getOptionValue("solver");
        Solver solver;
        if (solverOption == null || solverOption.isEmpty() || "multi".equals(solverOption)) {
            solver = new MultiThreadedSolver();
        } else if ("single".equals(solverOption)) {
            solver = new SingleThreadedSolver();
        } else {
            throw new IllegalArgumentException("Illegal parameter value \"solver\": " + solverOption);
        }
        return solver;
    }

    public List<int[]> getMoves(CommandLine cmd) {
        return requireParsedValue(cmd, "moves");
    }

    public Color[][] parsePuzzleOption(String puzzleOption) {
        String[] puzzleOptionSplit = puzzleOption
                .replace("][", "&SEP")
                .replace("[", "")
                .replace("]", "")
                .split("&SEP");

        return Arrays.stream(puzzleOptionSplit)
                .map(tube -> tube.split("\\s*,\\s*"))
                .map(colors -> Arrays.stream(colors)
                        .map(this::parseColor)
                        .toArray(Color[]::new)
                )
                .toArray(Color[][]::new);
    }

    public List<int[]> parseMoves(String movesOption) {
        return Arrays.stream(movesOption.split("\\s*,\\s*"))
                .map(moveString -> moveString.split("-"))
                .map(fromtoStrings -> new int[]{Integer.parseInt(fromtoStrings[0]) - 1, Integer.parseInt(fromtoStrings[1]) - 1})
                .toList();
    }

    public WinCondition parseWinCondition(String winConditionOption) {
        if (winConditionOption == null || winConditionOption.isEmpty() || "standard".equals(winConditionOption)) {
            return WinCondition.STANDARD;
        }

        if (winConditionOption.startsWith("sole-color:")) {
            String soleColorString = winConditionOption.replace("sole-color:", "");
            Color soleColor = parseColor(soleColorString);
            return new SoleColorWinCondition(soleColor);
        }

        throw new IllegalArgumentException("Illegal parameter value \"win-condition\": " + winConditionOption);
    }

    public TubesFormatter parseTubesFormatter(CommandLine cmd) {
        String colorOptionValue = cmd.getOptionValue("colored-output");
        if (colorOptionValue == null || colorOptionValue.isEmpty() || "true".equals(colorOptionValue)) {
            boolean installAnsiCmd = cmd.hasOption("install-ansi");
            return new ColoredTubesFormatter(installAnsiCmd);
        }
        return new StandardTubesFormatter();
    }

    private Color parseColor(String s) {
        return s.matches("\\d+") ? Color.values()[Integer.parseInt(s)] : Color.valueOf(s.toUpperCase());
    }

    private <T> T requireParsedValue(CommandLine cmd, String optionName) {
        final String errorMessage = "Could not parse \"" + optionName + "\" argument";
        T parsedOptionValue = null;
        try {
            parsedOptionValue = cmd.getParsedOptionValue(optionName);
        } catch (ParseException e) {
            System.err.println(errorMessage);
            System.exit(1);
        }

        if (parsedOptionValue != null) return parsedOptionValue;

        throw new IllegalArgumentException("Argument \"" + optionName + "\" is required");
    }

    private <T> Optional<T> optParsedValue(CommandLine cmd, String optionName, Class<T> clazz) {
        String errorMessage = "Could not parse \"" + optionName + "\" argument";
        try {
            return Optional.ofNullable(cmd.getParsedOptionValue(optionName));
        } catch (ParseException e) {
            System.err.println(errorMessage);
            System.exit(1);
        }
        throw new IllegalArgumentException(errorMessage);
    }
}
