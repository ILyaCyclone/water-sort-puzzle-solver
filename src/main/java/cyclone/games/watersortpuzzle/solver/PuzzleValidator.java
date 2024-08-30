package cyclone.games.watersortpuzzle.solver;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Validate puzzle.
 * Will throw exception if puzzle is written down with errors.
 */
public class PuzzleValidator {

    public void validate(Puzzle puzzle) {
        validateEqualTubesLength(puzzle);

        validateColorsCount(puzzle);
    }

    private void validateEqualTubesLength(Puzzle puzzle) {
        Color[][] tubes = puzzle.tubes();

        if (Arrays.stream(tubes).map(tube -> tube.length).distinct().count() > 1) {
            int[] longerTube = IntStream.range(0, tubes.length)
                    .mapToObj(i -> new int[]{i, tubes[i].length})
                    .max(Comparator.comparingInt(pair -> pair[1]))
                    .get();

            throw new IllegalArgumentException("Unequal tubes length: tube " + (longerTube[0] + 1) + " has length " + longerTube[1]);
        }
    }

    // check color numbers according to tubes length
    private void validateColorsCount(Puzzle puzzle) {
        Color[][] tubes = puzzle.tubes();

        Map<Color, Integer> totalBallsCount = Arrays.stream(tubes).flatMap(Arrays::stream)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));

        int capacity = tubes[0].length;
        boolean ballsCountError = false;
        StringJoiner ballsCountErrorJoiner = new StringJoiner("; ");
        for (Map.Entry<Color, Integer> colorCount : totalBallsCount.entrySet()) {
            if (colorCount.getValue() % capacity != 0) {
                ballsCountError = true;
                ballsCountErrorJoiner.add(colorCount.getKey() + ": " + colorCount.getValue());
            }
        }
        if (ballsCountError)
            throw new IllegalArgumentException("Color count is not divisible by tubes' length (" + capacity + "): " +
                    ballsCountErrorJoiner);
    }

}
