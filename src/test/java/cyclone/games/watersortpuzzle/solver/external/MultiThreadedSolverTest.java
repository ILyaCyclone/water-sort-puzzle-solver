package cyclone.games.watersortpuzzle.solver.external;

import cyclone.games.watersortpuzzle.solver.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

class MultiThreadedSolverTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("puzzlesProvider")
    void solve(String puzzleName, Puzzle puzzle, int expectedMovesCount) {
        Solver solver = new MultiThreadedSolver();
        Solution solution = solver.solve(puzzle);

        int actualMovesCount = solution.moves().size();

        Assertions.assertEquals(expectedMovesCount, actualMovesCount, "moves count mismatch");
    }

    static Stream<Arguments> puzzlesProvider() {
        return Stream.of(TestPuzzles.AUG18_EASY, TestPuzzles.AUG22_MEDIUM, TestPuzzles.AUG11_CHALLENGE)
                .map(testPuzzle -> arguments(testPuzzle.name(), testPuzzle.puzzle(), testPuzzle.expectedMovesCount()));
    }

}