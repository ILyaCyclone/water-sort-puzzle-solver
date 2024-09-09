package cyclone.games.watersortpuzzle.solver.cli;

import cyclone.games.watersortpuzzle.solver.*;
import org.apache.commons.cli.CommandLine;

import java.util.List;
import java.util.stream.Collectors;

public class CLIActionSolve implements CLIAction {

    private final TypedParser typedParser;

    public CLIActionSolve(TypedParser typedParser) {
        this.typedParser = typedParser;
    }

    @Override
    public void process(CommandLine cmd) {
        Color[][] tubes = typedParser.getTubes(cmd);
        WinCondition winCondition = typedParser.getWinCondition(cmd);

        Puzzle puzzle = new Puzzle(tubes, winCondition);
        Solver solver = typedParser.getSolver(cmd);

        TubesFormatter tubesFormatter = typedParser.parseTubesFormatter(cmd);
        String puzzleFormatted = tubesFormatter.format(puzzle.tubes());
        System.out.println(puzzleFormatted);
        System.out.println("Looking for solution...");

        Solution solution = solver.solve(puzzle);

        if (solution != null) {
            List<int[]> moves = solution.moves();
            System.out.println("Best solution in " + moves.size() + " moves: [" +
                    moves.stream().map(move -> (move[0] + 1) + "-" + (move[1] + 1))
                            .collect(Collectors.joining(", ")) + ']');

            Replay replay = new Replay(tubesFormatter);
            String replayOutput = replay.replay(puzzle, moves);
            System.out.println(replayOutput);
        } else {
            System.out.println("SOLUTION NOT FOUND");
        }
    }
}
