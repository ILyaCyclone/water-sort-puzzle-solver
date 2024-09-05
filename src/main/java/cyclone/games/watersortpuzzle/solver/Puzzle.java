package cyclone.games.watersortpuzzle.solver;

import java.util.Arrays;

/**
 * One puzzle and its win condition.
 * Tubes' colors are written top-down.
 */
public record Puzzle(
        Color[][] tubes,
        WinCondition winCondition
) {

    public Puzzle(Color[][] tubes) {
        this(tubes, WinCondition.STANDARD);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Puzzle puzzle = (Puzzle) o;

        if (!Arrays.deepEquals(tubes, puzzle.tubes)) return false;
        return winCondition.equals(puzzle.winCondition);
    }

    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(tubes);
        result = 31 * result + winCondition.hashCode();
        return result;
    }
}
