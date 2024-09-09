package cyclone.games.watersortpuzzle.solver.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class CLIActionHelp implements CLIAction {
    private final Options options;

    public CLIActionHelp(Options options) {
        this.options = options;
    }

    @Override
    public void process(CommandLine cmd) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.setWidth(150);
        formatter.printHelp("water-sorter-puzzle-solver <action> [options]",
                """
                        Action is one of:
                        - solve - solve the puzzle (required options: puzzle);
                        - replay - replay moves (required options: puzzle, moves);
                        - print - just print the puzzle (required options: puzzle);
                        - list-colors - list supported color names with respective number;
                        - help - print help;
                        - version - print app version.
                                                    
                        Options:""",
                options, null);
    }
}
