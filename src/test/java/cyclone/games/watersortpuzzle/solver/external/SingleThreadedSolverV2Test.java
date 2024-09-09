package cyclone.games.watersortpuzzle.solver.external;

import cyclone.games.watersortpuzzle.solver.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

class SingleThreadedSolverV2Test {

    @ParameterizedTest(name = "{0}")
    @MethodSource("puzzlesProvider")
    void solve(String puzzleName, Puzzle puzzle, int expectedMovesCount, List<int[]> expectedMoves) {
        Solver solver = new SingleThreadedSolverV2();
        Solution solution = solver.solve(puzzle);

        List<int[]> actualMoves = solution.moves();
        int actualMovesCount = actualMoves.size();

        Assertions.assertEquals(expectedMovesCount, actualMovesCount, "moves count mismatch");
        Assertions.assertTrue(TestPuzzles.movesEqual(actualMoves, expectedMoves),
                () -> "moves mismatch: expected " + TestPuzzles.formatMoves(expectedMoves) + ", but was " + TestPuzzles.formatMoves(actualMoves));
    }

    static Stream<Arguments> puzzlesProvider() {
        return Stream.of(TestPuzzles.AUG18_EASY, TestPuzzles.AUG22_MEDIUM, TestPuzzles.AUG11_CHALLENGE)
                .map(testPuzzle -> arguments(testPuzzle.name(), testPuzzle.puzzle(), testPuzzle.expectedMovesCount(), testPuzzle.expectedMoves()));
    }

}