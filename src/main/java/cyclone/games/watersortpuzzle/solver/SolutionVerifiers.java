package cyclone.games.watersortpuzzle.solver;

public class SolutionVerifiers {

    public static SolutionVerifier forPuzzle(Puzzle puzzle) {
        return switch (puzzle.task()) {
            case STANDARD -> new StandardSolutionVerifier();
            case SOLE_COLOR -> new SoleColorSolutionVerifier(puzzle.soleColor());
        };
    }

}
