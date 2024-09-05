package cyclone.games.watersortpuzzle.solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static cyclone.games.watersortpuzzle.solver.Color.EMPTY;

/**
 * Main logic of manipulating and examining tubes.
 */
public class TubesManipulator {

    /**
     * Move one unit from {@code fromTube} to {@code toTube}. 0-based indexes;
     * Does not modify original {@code tubes}.
     *
     * @return new state of tubes.
     */
    public Color[][] makeAMove(Color[][] tubes, int toTube, int fromTube) {
        Color[][] tryNewTubes = Utils.deepCopy(tubes);
        Color color = null;
        for (int i = 0; i < tryNewTubes[fromTube].length; i++) {
            Color possibleColor = tryNewTubes[fromTube][i];
            if (possibleColor != EMPTY) {
                color = possibleColor;
                tryNewTubes[fromTube][i] = EMPTY;
                break;
            }
        }
        if (color == null) throw new RuntimeException("Could not find top color from " + fromTube + " to " + toTube);

        boolean placed = false;
        for (int i = tryNewTubes[toTube].length - 1; i >= 0; i--) {
            if (tryNewTubes[toTube][i] == EMPTY) {
                tryNewTubes[toTube][i] = color;
                placed = true;
                break;
            }
        }
        if (!placed) throw new RuntimeException("Could not place new color from " + fromTube + " to " + toTube);
        return tryNewTubes;
    }

    /**
     * Find possible moves from {@code tubeIndex}.
     *
     * @return tubes' indexes, available for a move (0-based).
     */
    public List<Integer> possibleMoves(Color[][] tubes, int tubeIndex) {
        Color[] tube = tubes[tubeIndex];
        int capacity = tubes[0].length;

        Color topColor = topColor(tube);
        boolean soleColor = isSoleColor(tube);

        // condition 1: skip if current tube is empty
        if (topColor == EMPTY) return Collections.emptyList();
        // condition 2: skip if current tube is complete
        if (isSoleColor(tube)) {
            if (countBalls(tube) == capacity) return Collections.emptyList();
        }

        List<Integer> moves = new ArrayList<>();
        for (int i = 0; i < tubes.length; i++) {
            // condition 3: skip tube itself
            if (i == tubeIndex) continue;

            Color[] possibleTube = tubes[i];
            int possibleCountBalls = countBalls(possibleTube);
            // condition 4: skip full tube
            if (possibleCountBalls == capacity) continue;

            Color possibleTopColor = topColor(possibleTube);
            // condition 5: skip tube not suitable by color or empty space
            if (!(possibleTopColor == topColor || possibleTopColor == EMPTY)) continue;

            // optimization 1: skip tube if making move will it same as current
            Color[][] tryMoveTubes = makeAMove(tubes, i, tubeIndex);
            if (Arrays.deepEquals(tryMoveTubes[i], tube)) continue;

            if (soleColor) {
                // optimization 2: don't move to empty tube if current tube has sole color
                if (possibleTopColor == EMPTY) continue;

                // [this is wrong] optimization 3: skip if current tube is sole and target is not
//                boolean possibleTubeSoleColor = isSoleColor(possibleTube);
//                if (!possibleTubeSoleColor) continue;

                // optimization 4: don't move if current tube has more of sole color balls
                if (countBalls(tube) > possibleCountBalls) continue;
            }

            moves.add(i);
        }
        return moves;
    }

    public int countBalls(Color[] tube) {
        return (int) Arrays.stream(tube).filter(color -> color != EMPTY).count();
    }

    private boolean isSoleColor(Color[] tube) {
        return Arrays.stream(tube).filter(color -> color != EMPTY).distinct().count() == 1;
    }

    private Color topColor(Color[] tube) {
        return Arrays.stream(tube)
                .filter(color -> color != EMPTY)
                .findFirst()
                .orElse(EMPTY);
    }

}
