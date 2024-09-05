package cyclone.games.watersortpuzzle.solver.external;

import cyclone.games.watersortpuzzle.solver.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

class SingleThreadedSolverTest {

    Solver solver = new SingleThreadedSolver();

    @ParameterizedTest
    @MethodSource("puzzlesProvider")
    void solve(Puzzle puzzle, int expectedMovesCount) {
        Solution solution = solver.solve(puzzle);
        int actualMovesCount = solution.moves().size();
        Assertions.assertEquals(expectedMovesCount, actualMovesCount, "moves count mismatch");
    }

    static Stream<Arguments> puzzlesProvider() {
        return Stream.of(
                arguments(Puzzles.AUG18_EASY, 19),
                arguments(Puzzles.AUG22_MEDIUM, 23)
//                arguments(Puzzles.AUG11_CHALLENGE, 39) // too long
        );
    }

}