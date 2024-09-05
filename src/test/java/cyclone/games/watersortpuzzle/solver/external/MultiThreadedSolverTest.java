package cyclone.games.watersortpuzzle.solver.external;

import cyclone.games.watersortpuzzle.solver.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

class MultiThreadedSolverTest {

    @ParameterizedTest(name = "{2}")
    @MethodSource("puzzlesProvider")
    void solve(Puzzle puzzle, int expectedMovesCount, String puzzleName) {
        Solver solver = new MultiThreadedSolver();
        Solution solution = solver.solve(puzzle);
        int actualMovesCount = solution.moves().size();
        Assertions.assertEquals(expectedMovesCount, actualMovesCount, "moves count mismatch");
    }

    static Stream<Arguments> puzzlesProvider() {
        return Stream.of(
                arguments(Puzzles.AUG18_EASY, 19, "AUG18_EASY"),
                arguments(Puzzles.AUG22_MEDIUM, 23, "AUG22_MEDIUM"),
                arguments(Puzzles.AUG11_CHALLENGE, 39, "AUG11_CHALLENGE") // sole color
        );
    }

}