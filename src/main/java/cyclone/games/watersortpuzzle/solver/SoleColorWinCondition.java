package cyclone.games.watersortpuzzle.solver;

import java.util.Arrays;

/**
 * Selected color in an exclusive tube.
 */
public class SoleColorWinCondition implements WinCondition {

    private final Color soleColor;

    public SoleColorWinCondition(Color soleColor) {
        this.soleColor = soleColor;
    }

    @Override
    public boolean check(Color[][] tubes) {
        int capacity = tubes[0].length;
        for (Color[] tube : tubes) {
            if (Arrays.stream(tube).filter(color -> color == soleColor).count() == capacity) return true;
        }
        return false;
    }
}
