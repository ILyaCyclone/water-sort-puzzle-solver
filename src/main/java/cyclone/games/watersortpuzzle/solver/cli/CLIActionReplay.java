package cyclone.games.watersortpuzzle.solver.cli;

import cyclone.games.watersortpuzzle.solver.*;
import org.apache.commons.cli.CommandLine;

import java.util.List;

public class CLIActionReplay implements CLIAction {

    private final TypedParser typedParser;

    public CLIActionReplay(TypedParser typedParser) {
        this.typedParser = typedParser;
    }

    @Override
    public void process(CommandLine cmd) {
        Color[][] tubes = typedParser.getTubes(cmd);
        WinCondition winCondition = typedParser.getWinCondition(cmd);
        Puzzle puzzle = new Puzzle(tubes, winCondition);

        List<int[]> moves = typedParser.getMoves(cmd);

        TubesFormatter tubesFormatter = typedParser.parseTubesFormatter(cmd);
        Replay replay = new Replay(tubesFormatter);
        String replayOutput = replay.replay(puzzle, moves);
        System.out.println(replayOutput);
    }
}
