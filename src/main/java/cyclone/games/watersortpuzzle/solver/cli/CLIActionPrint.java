package cyclone.games.watersortpuzzle.solver.cli;

import cyclone.games.watersortpuzzle.solver.Color;
import cyclone.games.watersortpuzzle.solver.TubesFormatter;
import org.apache.commons.cli.CommandLine;

public class CLIActionPrint implements CLIAction {

    private final TypedParser typedParser;

    public CLIActionPrint(TypedParser typedParser) {
        this.typedParser = typedParser;
    }

    @Override
    public void process(CommandLine cmd) {
        Color[][] tubes = typedParser.getTubes(cmd);
        TubesFormatter tubesFormatter = typedParser.parseTubesFormatter(cmd);
        System.out.println(tubesFormatter.format(tubes));
    }
}
