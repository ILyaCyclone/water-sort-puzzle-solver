package cyclone.games.watersortpuzzle.solver;

import java.util.Arrays;

/**
 * Check SOLE_COLOR win condition.
 */
public class SoleColorSolutionCheck implements SolutionCheck {

    private final Color soleColor;

    public SoleColorSolutionCheck(Color soleColor) {
        this.soleColor = soleColor;
    }

    @Override
    public boolean isSolved(Color[][] tubes) {
        int capacity = tubes[0].length;
        for (Color[] tube : tubes) {
            if (Arrays.stream(tube).filter(color -> color == soleColor).count() == capacity) return true;
        }
        return false;
    }
}
