package cyclone.games.watersortpuzzle.solver;

import java.util.Arrays;

import static cyclone.games.watersortpuzzle.solver.Color.EMPTY;

/**
 * Standard win condition.
 * All colors in separate tubes.
 */
public class StandardWinCondition implements WinCondition {

    private final TubesManipulator tubesManipulator;

    public StandardWinCondition() {
        this(new TubesManipulator());
    }

    public StandardWinCondition(TubesManipulator tubesManipulator) {
        this.tubesManipulator = tubesManipulator;
    }

    @Override
    public boolean check(Color[][] tubes) {
        int capacity = tubes[0].length;

        for (Color[] tube : tubes) {
            if (Arrays.stream(tube).allMatch(color -> color == EMPTY)) continue;
            if (tubesManipulator.countBalls(tube) != capacity) return false;

            if (Arrays.stream(tube).distinct()
                    .filter(color -> color != EMPTY)
                    .count() > 1) return false;
        }
        return true;
    }
}
