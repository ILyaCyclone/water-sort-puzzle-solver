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
        formatter.printHelp("<action> [options]",
                """
                        Action is one of:
                        - solve - solve the puzzle;
                        - replay - replay moves;
                        - print - just print the puzzle;
                        - colors - list all colors;
                        - help - print help;
                        - version.
                                                    
                        Options:""",
                options, null);
    }
}
