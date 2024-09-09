package cyclone.games.watersortpuzzle.solver.cli;

import org.apache.commons.cli.CommandLine;

public class CLIActionVersion implements CLIAction {

    @Override
    public void process(CommandLine cmd) {
        System.out.println("1.2.0");
    }
}
