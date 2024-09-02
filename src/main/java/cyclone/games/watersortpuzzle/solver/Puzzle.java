package cyclone.games.watersortpuzzle.solver;

import java.util.Arrays;

/**
 * One puzzle and its win condition.
 * Tubes' colors are written top-down.
 */
public record Puzzle(
        Color[][] tubes,
        WinCondition winCondition,
        Color soleColor
) {

    public Puzzle(Color[][] tubes) {
        this(tubes, WinCondition.STANDARD, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Puzzle puzzle = (Puzzle) o;

        if (!Arrays.deepEquals(tubes, puzzle.tubes)) return false;
        if (winCondition != puzzle.winCondition) return false;
        return soleColor == puzzle.soleColor;
    }

    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(tubes);
        result = 31 * result + (winCondition != null ? winCondition.hashCode() : 0);
        result = 31 * result + (soleColor != null ? soleColor.hashCode() : 0);
        return result;
    }
}
