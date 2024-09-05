package cyclone.games.watersortpuzzle.solver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static cyclone.games.watersortpuzzle.solver.Color.*;

class TubesManipulatorTest {

    private TubesManipulator tubesManipulator = new TubesManipulator();

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
            String expectedTubesString = tubesToString(expectedTubes);
            String actualTubesString = tubesToString(actualTubes);
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
            String expectedTubesString = tubesToString(tubesBackup);
            String actualTubesString = tubesToString(tubes);
            return "Original tubes should not be modified. Expected:\n" + expectedTubesString + "\nbut was:\n" + actualTubesString;
        });
    }

    @Test
    void possibleMoves_fromEmpty() {
        Color[][] tubes = {
                {EMPTY, EMPTY},
                {EMPTY, RED}
        };

        List<Integer> possibleMoves = tubesManipulator.possibleMoves(tubes, 0);

        Assertions.assertTrue(possibleMoves.isEmpty(), "Should not have moves from an empty tube, but was: " + possibleMoves);
    }

    @Test
    void possibleMoves_fromComplete() {
        Color[][] tubes = {
                {RED, RED, RED},
                {EMPTY, EMPTY, RED}
        };

        List<Integer> possibleMoves = tubesManipulator.possibleMoves(tubes, 0);

        Assertions.assertTrue(possibleMoves.isEmpty(), "Should not have moves from a complete tube, but was: " + possibleMoves);
    }

    @Test
    void possibleMoves_noSpace() {
        Color[][] tubes = {
                {EMPTY, RED},
                {RED, RED},
                {RED, RED},
        };

        List<Integer> possibleMoves = tubesManipulator.possibleMoves(tubes, 0);

        Assertions.assertTrue(possibleMoves.isEmpty(), "Should not have moves as there's no free space, but was: " + possibleMoves);
    }

    @Test
    void possibleMoves_willMakeTheSame() {
        Color[][] tubes = {
                {RED, RED, GREEN},
                {EMPTY, RED, GREEN},
        };

        List<Integer> possibleMoves = tubesManipulator.possibleMoves(tubes, 0);

        Assertions.assertTrue(possibleMoves.isEmpty(), "Should not have moves as new state will be the same as current, but was: " + possibleMoves);
    }

    @Test
    void possibleMoves_dontMoveSoleColor() {
        Color[][] tubes = {
                {EMPTY, RED, RED},
                Puzzles.emptyTube(3)
        };

        List<Integer> possibleMoves = tubesManipulator.possibleMoves(tubes, 0);

        Assertions.assertTrue(possibleMoves.isEmpty(), "Should not move from a sole color tube, but was: " + possibleMoves);
    }

    @Test
    void possibleMoves_dontMoveMoreSoleColors() {
        Color[][] tubes = {
                {EMPTY, RED, RED, RED},
                {EMPTY, EMPTY, EMPTY, RED}
        };

        List<Integer> possibleMoves = tubesManipulator.possibleMoves(tubes, 0);

        Assertions.assertTrue(possibleMoves.isEmpty(), "Should not move from a sole color tube with more color units: " + possibleMoves);
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

    private String tubesToString(Color[][] tubes) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            new Printer(baos).log(tubes);
            return baos.toString();
        } catch (IOException e) {
            System.err.println("Could not print tubes: " + e);
        }
        return Arrays.deepToString(tubes);
    }

}