package cyclone.games.watersortpuzzle.solver;

import static cyclone.games.watersortpuzzle.solver.Color.EMPTY;

/**
 * Format tubes as string.
 */
public class TubesFormatter {

    public String format(Color[][] tubes) {
        int capacity = tubes[0].length;

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < capacity; i++) {
            for (Color[] tube : tubes) {
                stringBuilder.append(" %10s".formatted(tube[i] == EMPTY ? "-" : tube[i]));
                stringBuilder.append(" |");
            }
            stringBuilder.append("\n");
        }
        for (int i = 0; i < tubes.length; i++) {
            stringBuilder.append(" %10d".formatted(i + 1));
            stringBuilder.append(" |");
        }
        return stringBuilder.toString();
    }

}
