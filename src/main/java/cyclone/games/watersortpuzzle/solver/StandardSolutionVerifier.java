package cyclone.games.watersortpuzzle.solver;

import java.util.Arrays;

import static cyclone.games.watersortpuzzle.solver.Color.EMPTY;

public class StandardSolutionVerifier implements SolutionVerifier {

    @Override
    public boolean isSolved(Color[][] tubes) {
        int capacity = tubes[0].length;

        for (Color[] tube : tubes) {
            if (Arrays.stream(tube).allMatch(color -> color == EMPTY)) continue;
            if (countBalls(tube) != capacity) return false;

            if (Arrays.stream(tube).distinct()
                    .filter(color -> color != EMPTY)
                    .count() > 1) return false;
        }
        return true;
    }

    private int countBalls(Color[] tube) {
        return (int) Arrays.stream(tube).filter(color -> color != EMPTY).count();
    }
}
