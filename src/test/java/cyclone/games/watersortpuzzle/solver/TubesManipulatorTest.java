package cyclone.games.watersortpuzzle.solver;

import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static cyclone.games.watersortpuzzle.solver.Color.*;

class TubesManipulatorTest {

    private final TubesManipulator tubesManipulator = new TubesManipulator();
    private final TubesFormatter tubesFormatter = new TubesFormatter();

    @Test
    void makeAMove() {
        Color[][] tubes = {
                {RED, GREEN},
                {EMPTY, RED}
        };
        Color[][] expectedTubes = {
                {EMPTY, GREEN},
                {RED, RED}
        };

        Color[][] actualTubes = tubesManipulator.makeAMove(tubes, 1, 0);

        boolean actualMatchesExpected = Arrays.deepEquals(expectedTubes, actualTubes);

        Assertions.assertTrue(actualMatchesExpected, () -> {
            String expectedTubesString = tubesFormatter.format(expectedTubes);
            String actualTubesString = tubesFormatter.format(actualTubes);
            return "Tubes mismatch. Expected:\n" + expectedTubesString + "\nbut was:\n" + actualTubesString;
        });
    }

    @Test
    void makeAMove_originalNotModified() {
        Color[][] tubes = {
                {RED, GREEN},
                {EMPTY, RED}
        };
        final Color[][] tubesBackup = {
                {RED, GREEN},
                {EMPTY, RED}
        };

        tubesManipulator.makeAMove(tubes, 1, 0);

        boolean originalNotModified = Arrays.deepEquals(tubes, tubesBackup);

        Assertions.assertTrue(originalNotModified, () -> {
            String expectedTubesString = tubesFormatter.format(tubesBackup);
            String actualTubesString = tubesFormatter.format(tubes);
            return "Original tubes should not be modified. Expected:\n" + expectedTubesString + "\nbut was:\n" + actualTubesString;
        });
    }

    @Test
    void possibleMove() {
        Color[][] tubes = {
                {RED, GREEN, GREEN},
                {EMPTY, RED, RED}
        };

        List<Integer> possibleMoves = tubesManipulator.possibleMoves(tubes, 0);

        Assertions.assertTrue(!possibleMoves.isEmpty() && possibleMoves.get(0) == 1,
                "Should have move from 0 to 1, but was: " + possibleMoves);
    }

    @Nested
    @IndicativeSentencesGeneration(separator = ", if ", generator = DisplayNameGenerator.ReplaceUnderscores.class)
    class Make_no_move {

        @Test
        void current_tube_is_empty() {
            Color[][] tubes = {
                    {EMPTY, EMPTY},
                    {EMPTY, RED}
            };

            List<Integer> possibleMoves = tubesManipulator.possibleMoves(tubes, 0);

            Assertions.assertTrue(possibleMoves.isEmpty(), "Should not have moves from an empty tube, but was: " + possibleMoves);
        }

        @Test
        void current_tube_is_complete() {
            Color[][] tubes = {
                    {RED, RED, RED},
                    {EMPTY, EMPTY, RED}
            };

            List<Integer> possibleMoves = tubesManipulator.possibleMoves(tubes, 0);

            Assertions.assertTrue(possibleMoves.isEmpty(), "Should not have moves from a complete tube, but was: " + possibleMoves);
        }

        @Test
        void no_free_space() {
            Color[][] tubes = {
                    {EMPTY, RED},
                    {RED, RED},
                    {RED, RED},
            };

            List<Integer> possibleMoves = tubesManipulator.possibleMoves(tubes, 0);

            Assertions.assertTrue(possibleMoves.isEmpty(), "Should not have moves as there's no free space, but was: " + possibleMoves);
        }

        @Test
        void will_not_change_state() {
            Color[][] tubes = {
                    {RED, RED, GREEN},
                    {EMPTY, RED, GREEN},
            };

            List<Integer> possibleMoves = tubesManipulator.possibleMoves(tubes, 0);

            Assertions.assertTrue(possibleMoves.isEmpty(), "Should not have moves as new state will be the same as current, but was: " + possibleMoves);
        }

        @Test
        void current_tube_has_sole_color_and_target_is_empty() {
            Color[][] tubes = {
                    {EMPTY, RED, RED},
                    Puzzles.emptyTube(3)
            };

            List<Integer> possibleMoves = tubesManipulator.possibleMoves(tubes, 0);

            Assertions.assertTrue(possibleMoves.isEmpty(), "Should not move from a sole color tube, but was: " + possibleMoves);
        }

        @Test
        void current_tube_has_more_sole_color_than_target() {
            Color[][] tubes = {
                    {EMPTY, RED, RED, RED},
                    {EMPTY, EMPTY, EMPTY, RED}
            };

            List<Integer> possibleMoves = tubesManipulator.possibleMoves(tubes, 0);

            Assertions.assertTrue(possibleMoves.isEmpty(), "Should not move from a sole color tube with more color units: " + possibleMoves);
        }
    }

}