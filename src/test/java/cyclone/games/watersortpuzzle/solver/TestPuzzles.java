package cyclone.games.watersortpuzzle.solver;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestPuzzles {

    public static final TestPuzzle AUG18_EASY = new TestPuzzle("AUG18_EASY", Puzzles.AUG18_EASY,
            19, List.of(new int[][]{{1, 8}, {1, 9}, {3, 9}, {6, 9}, {6, 8}, {2, 6}, {2, 6}, {3, 2}, {4, 3}, {4, 3}, {1, 4}, {5, 1}, {5, 1}, {0, 5}, {0, 8}, {0, 4}, {7, 1}, {7, 5}, {7, 2}}));

    public static final TestPuzzle AUG22_MEDIUM = new TestPuzzle("AUG22_MEDIUM", Puzzles.AUG22_MEDIUM,
            23, List.of((new int[][]{{1, 7}, {4, 8}, {4, 8}, {4, 7}, {1, 4}, {1, 8}, {2, 4}, {2, 7}, {2, 1}, {2, 4}, {5, 8}, {5, 1}, {5, 7}, {0, 5}, {0, 1}, {3, 5}, {3, 2}, {3, 5}, {2, 3}, {6, 0}, {6, 3}, {6, 0}, {6, 3}})));

    public static final TestPuzzle AUG11_CHALLENGE = new TestPuzzle("AUG11_CHALLENGE", Puzzles.AUG11_CHALLENGE,
            39, List.of((new int[][]{{1, 7}, {1, 8}, {1, 8}, {2, 7}, {2, 8}, {4, 1}, {3, 4}, {2, 3}, {5, 1}, {2, 5}, {1, 2}, {1, 2}, {1, 2}, {1, 8}, {6, 7}, {6, 7}, {6, 2}, {6, 8}, {1, 6}, {0, 1}, {3, 1}, {3, 1}, {3, 0}, {3, 7}, {3, 1}, {3, 6}, {2, 3}, {2, 3}, {2, 3}, {2, 3}, {2, 3}, {2, 6}, {0, 2}, {0, 2}, {4, 2}, {4, 2}, {4, 1}, {4, 0}, {4, 6}})));

    private TestPuzzles() {
    }

    public static boolean movesEqual(List<int[]> moves1, List<int[]> moves2) {
        return moves1.size() == moves2.size() &&
                IntStream.range(0, moves1.size())
                        .allMatch(i -> Arrays.equals(moves1.get(i), moves2.get(i)));
    }

    public static String formatMoves(List<int[]> moves) {
        return moves.stream().map(move -> move[0] + "-" + move[1]).collect(Collectors.joining(", "));
    }

    public record TestPuzzle(String name, Puzzle puzzle, int expectedMovesCount, List<int[]> expectedMoves) {
    }
}
