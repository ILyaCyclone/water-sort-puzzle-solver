package cyclone.games.watersortpuzzle.solver;

import java.io.OutputStream;
import java.io.PrintStream;

import static cyclone.games.watersortpuzzle.solver.Color.EMPTY;

public class Printer {

    private final PrintStream out;

    public Printer(PrintStream out) {
        this.out = out;
    }

    public Printer(OutputStream out) {
        this.out = new PrintStream(out);
    }

    public void log(Color[][] tubes) {
        int capacity = tubes[0].length;

        for (int i = 0; i < capacity; i++) {
            for (Color[] tube : tubes) {
                out.print(" %10s".formatted(tube[i] == EMPTY ? "-" : tube[i]));
                out.print(" |");
            }
            out.println();
        }
        for (int i = 0; i < tubes.length; i++) {
            out.print(" %10d".formatted(i + 1));
            out.print(" |");
        }
        out.println();
    }

}
