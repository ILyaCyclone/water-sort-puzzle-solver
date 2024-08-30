package cyclone.games.watersortpuzzle.solver;

import java.util.Arrays;

public class PuzzleState {
    private final Color[][] state;

    public PuzzleState(Color[][] state) {
        // Deep copy to ensure immutability
//        this.state = deepCopy(state);
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PuzzleState that = (PuzzleState) o;
        return Arrays.deepEquals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(state);
    }
}
