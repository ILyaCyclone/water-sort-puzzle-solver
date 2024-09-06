package cyclone.games.watersortpuzzle.solver.cli;

import cyclone.games.watersortpuzzle.solver.Color;
import cyclone.games.watersortpuzzle.solver.TubesFormatter;
import org.apache.commons.cli.CommandLine;

public class CLIActionListColors implements CLIAction {

    private final TypedParser typedParser;

    public CLIActionListColors(TypedParser typedParser) {
        this.typedParser = typedParser;
    }

    @Override
    public void process(CommandLine cmd) {
        Color[] allColors = Color.values();
        TubesFormatter tubesFormatter = typedParser.parseTubesFormatter(cmd);

        for (int i = 0; i < allColors.length; i++) {
            System.out.printf("%2d - %s%n", i, tubesFormatter.format(allColors[i]));
        }
    }
}
