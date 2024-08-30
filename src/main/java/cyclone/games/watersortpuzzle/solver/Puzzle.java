package cyclone.games.watersortpuzzle.solver;

import java.util.Arrays;

public record Puzzle(
        Color[][] tubes
) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Puzzle puzzle = (Puzzle) o;

        return Arrays.deepEquals(tubes, puzzle.tubes);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(tubes);
    }
}
