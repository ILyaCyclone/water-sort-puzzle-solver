package cyclone.games.watersortpuzzle.solver;

public class SolutionVerifiers {

    private SolutionVerifiers() {
    }

    public static SolutionVerifier forPuzzle(Puzzle puzzle) {
        return switch (puzzle.winCondition()) {
            case STANDARD -> new StandardSolutionVerifier();
            case SOLE_COLOR -> new SoleColorSolutionVerifier(puzzle.soleColor());
        };
    }

}
