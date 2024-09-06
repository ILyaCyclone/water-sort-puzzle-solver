package cyclone.games.watersortpuzzle.solver.cli;

import cyclone.games.watersortpuzzle.solver.Color;
import cyclone.games.watersortpuzzle.solver.WinCondition;
import org.apache.commons.cli.*;

import java.util.List;

public class CLI {

    public static void main(String[] args) {
        TypedParser typedParser = new TypedParser();
        Options options = new Options();
        Option puzzleOption = Option.builder()
                .option("p")
                .longOpt("puzzle")
                .hasArg()
                .argName("puzzle")
                .desc("A puzzle, where colors are represented in a form of integers " +
                        "or color names, each tube written top-down. " +
                        "E.g.: [1,2,3][4,5,6][0,0,0] - 3 tubes, the first have color (top-down) [1, 2, 3], the second [4, 5, 6], " +
                        "the third is an empty tube of 3 capacity. Or: " +
                        "[red,green,blue][yellow,white,gray][empty,empty,empty]."
                )
                .type(Color[][].class)
                .converter(typedParser::parsePuzzleOption)
                .build();
        options.addOption(puzzleOption);

        Option winConditionOption = Option.builder()
                .option("w")
                .longOpt("win-condition")
                .hasArg()
                .argName("condition")
                .desc("Win condition. Supported values: " +
                        "\"standard\" - standard win condition; \"sole-color:<color>\" - sort only <color> into separate tube, where " +
                        "<color> is an integer or a name, representing a color. Default: standard.")
                .type(WinCondition.class)
                .converter(typedParser::parseWinCondition)
                .build();
        options.addOption(winConditionOption);

        options.addOption("a", "algorithm", true, "Choose a solver algorithm. Supported values: " +
                "\"single\" - single-threaded solver; \"multi\" - multi-threaded solver. Default: multi.");

        Option movesOption = Option.builder()
                .longOpt("moves")
                .option("m")
//                .type(List<int[]>.class)
                .type(List.class)
                .converter(typedParser::parseMoves)
                .hasArg()
                .argName("moves")
                .desc("Moves in a form of from-to pairs, 1-based tubes indexes. E.g.: 1-3,2-4,1-2.")
                .build();
        options.addOption(movesOption);

        Option coloredOption = Option.builder()
                .longOpt("colored-output")
                .option("c")
                .argName("true|false")
                .optionalArg(true)
                .desc("Colorize the output? Default: true")
                .build();
        options.addOption(coloredOption);

        options.addOption(Option.builder()
                .longOpt("install-ansi")
                .desc("Sometimes a console (e.g. Windows CMD) won't support colored output, showing weird symbols instead. " +
                        "Turn on this option then. Default: no action.")
                .build()
        );
        CommandLine cmd;
        try {
            CommandLineParser parser = new DefaultParser();
            cmd = parser.parse(options, args);
        } catch (Exception e) {
            System.err.println("Could not parse start parameters: " + e);
            System.exit(1);
            return;
        }

        List<String> argList = cmd.getArgList();
        if (argList.isEmpty()) {
            new CLIActionHelp(options).process(cmd);
            return;
        }

        String action = argList.get(0);
        switch (action) {
            case "solve" -> new CLIActionSolve(typedParser).process(cmd);
            case "replay" -> new CLIActionReplay(typedParser).process(cmd);
            case "print" -> new CLIActionPrint(typedParser).process(cmd);
            case "colors" -> new CLIActionListColors(typedParser).process(cmd);
            case "version" -> new CLIActionVersion().process(cmd);
            default -> new CLIActionHelp(options).process(cmd);
        }
    }

}
