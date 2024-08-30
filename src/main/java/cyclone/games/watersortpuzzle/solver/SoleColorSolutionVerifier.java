package cyclone.games.watersortpuzzle.solver;

import java.util.Arrays;

public class SoleColorSolutionVerifier implements SolutionVerifier {

    private final Color soleColor;

    public SoleColorSolutionVerifier(Color soleColor) {
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
