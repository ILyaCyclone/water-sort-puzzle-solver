package cyclone.games.watersortpuzzle.solver;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * State of tubes, ignoring tubes order.
 */
public record TubesState(Color[][] tubes) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TubesState that = (TubesState) o;

        List<Integer> thisTubesHashes = Arrays.stream(this.tubes)
                .map(Arrays::hashCode)
                .sorted()
                .toList();

        List<Integer> thatTubesHashes = Arrays.stream(that.tubes)
                .map(Arrays::hashCode)
                .sorted()
                .toList();

        return thisTubesHashes.equals(thatTubesHashes);
    }

    @Override
    public int hashCode() {
        List<Integer> sortedTubesHashes = Arrays.stream(tubes)
                .map(Arrays::hashCode)
                .sorted()
                .toList();

        return Objects.hash(sortedTubesHashes);
    }
}
