package cyclone.games.watersortpuzzle.solver;

public class SolutionChecks {

    private SolutionChecks() {
    }

    public static SolutionCheck forPuzzle(Puzzle puzzle) {
        return switch (puzzle.winCondition()) {
            case STANDARD -> new StandardSolutionCheck();
            case SOLE_COLOR -> new SoleColorSolutionCheck(puzzle.soleColor());
        };
    }

}
