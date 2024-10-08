package cyclone.games.watersortpuzzle.solver;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Main business logic.
 * Solve puzzle using single thread, breadth-first.
 */
public class SingleThreadedSolverV2 implements Solver {

    private final TubesManipulator tubesManipulator = new TubesManipulator();
    private final PuzzleValidator puzzleValidator = new PuzzleValidator();
    private final Set<TubesState> states = new HashSet<>();

    @Override
    public Solution solve(Puzzle puzzle) {
        puzzleValidator.validate(puzzle);

        Color[][] tubes = puzzle.tubes();
        states.add(new TubesState(tubes));

        List<SolutionLevel> solutionLevels = List.of(new SolutionLevel(puzzle, Collections.emptyList()));

        return seekSolution(solutionLevels);
    }

    private Solution seekSolution(List<SolutionLevel> solutionLevels) {
        List<SolutionLevel> newSolutionLevels = new ArrayList<>();
        for (SolutionLevel solutionLevel : solutionLevels) {
            Puzzle puzzle = solutionLevel.puzzle();
            Color[][] tubes = puzzle.tubes();
            WinCondition winCondition = puzzle.winCondition();
            List<int[]> previousMoves = solutionLevel.previousMoves();

            List<int[]> possibleMoves = IntStream.range(0, tubes.length)
                    .boxed()
                    .flatMap(from -> tubesManipulator.possibleMoves(tubes, from).stream()
                            .map(to -> new int[]{from, to}))
                    .toList();

            for (int[] possibleMove : possibleMoves) {
                Color[][] newTubes = tubesManipulator.makeAMove(tubes, possibleMove[1], possibleMove[0]);
                TubesState newTubesState = new TubesState(newTubes);
                if(states.add(newTubesState)) {
                    List<int[]> newMoves = new ArrayList<>(previousMoves);
                    newMoves.add(possibleMove);
                    if (winCondition.check(newTubes)) {
                        return new Solution(newMoves);
                    }

                    newSolutionLevels.add(new SolutionLevel(new Puzzle(newTubes, winCondition), newMoves));
                }
            }
        }

        if(newSolutionLevels.isEmpty()) {
            return null;
        }

        return seekSolution(newSolutionLevels);
    }

    record SolutionLevel(Puzzle puzzle, List<int[]> previousMoves) {
    }

}
