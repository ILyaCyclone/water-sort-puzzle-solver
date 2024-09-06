package cyclone.games.watersortpuzzle.solver.cli;

import cyclone.games.watersortpuzzle.solver.*;
import org.apache.commons.cli.CommandLine;

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
        Solution solution = solver.solve(puzzle);

        if (solution != null) {
            TubesFormatter tubesFormatter = typedParser.parseTubesFormatter(cmd);
            Replay replay = new Replay(tubesFormatter);
            String replayOutput = replay.replay(puzzle, solution.moves());
            System.out.println(replayOutput);
        } else {
            System.out.println("SOLUTION NOT FOUND");
        }
    }
}
