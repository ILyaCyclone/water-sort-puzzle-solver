package cyclone.games.watersortpuzzle.solver;

import java.util.Arrays;

public record Puzzle(
        Color[][] tubes,
        Task task,
        Color soleColor
) {

    public Puzzle(Color[][] tubes) {
        this(tubes, Task.STANDARD, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Puzzle puzzle = (Puzzle) o;

        if (!Arrays.deepEquals(tubes, puzzle.tubes)) return false;
        if (task != puzzle.task) return false;
        return soleColor == puzzle.soleColor;
    }

    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(tubes);
        result = 31 * result + (task != null ? task.hashCode() : 0);
        result = 31 * result + (soleColor != null ? soleColor.hashCode() : 0);
        return result;
    }
}
