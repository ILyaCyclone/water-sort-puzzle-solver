package cyclone.games.watersortpuzzle.solver.cli;

import org.apache.commons.cli.CommandLine;

public interface CLIAction {
    void process(CommandLine cmd);
}
