package cyclone.games.watersortpuzzle.solver;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Main business logic.
 * Solve puzzle using single thread.
 */
public class SingleThreadedSolver implements Solver {

    private final TubesManipulator tubesManipulator = new TubesManipulator();
    private final PuzzleValidator puzzleValidator = new PuzzleValidator();

    private WinCondition winCondition;

    private final Map<PuzzleState, Integer> states = new HashMap<>();
    private List<int[]> bestSolution;
    private int bestMovesMade = Integer.MAX_VALUE;
    private int solutions = 0;


    @Override
    public Solution solve(Puzzle puzzle) {
        puzzleValidator.validate(puzzle);

        winCondition = puzzle.winCondition();

        Color[][] tubes = puzzle.tubes();
        states.put(new PuzzleState(tubes), 0);

//        printer.log(tubes);
//        System.out.println("=======================================");

        seekSolutions(tubes, Collections.emptyList());

        return Optional.ofNullable(bestSolution)
                .map(Solution::new)
                .orElse(null);
    }

    private void seekSolutions(Color[][] tubes, List<int[]> previousMoves) {
        boolean solved = false;
        while (!solved) {
            boolean moveMade = false;
            for (int i = 0; i < tubes.length; i++) {
                List<Integer> possibleMoves = tubesManipulator.possibleMoves(tubes, i);
                for (Integer possibleMove : possibleMoves) {
                    Color[][] tryNewTubes = tubesManipulator.makeAMove(tubes, possibleMove, i);
                    PuzzleState tryPuzzleState = new PuzzleState(tryNewTubes);
                    if (!states.containsKey(tryPuzzleState) || states.get(tryPuzzleState) > previousMoves.size() + 1) {
                        moveMade = true;
                        List<int[]> moves = new LinkedList<>(previousMoves);
                        moves.add(new int[]{i, possibleMove});
                        int movesMade = moves.size();
                        states.put(tryPuzzleState, movesMade);
//                log(tubes);
//                System.out.println(movesMade + ". move " + (i + 1) + " to " + (possibleMove + 1));
//                System.out.println("----------------------------------------");
                        if (winCondition.check(tryNewTubes)) {
//                    log(tryNewTubes);
                            solved = true;
                            if (movesMade < bestMovesMade) {
                                solutions++;
                                bestSolution = Collections.unmodifiableList(moves);
                                bestMovesMade = movesMade;
                                System.out.println("==================================================");
                                System.out.println("Solution " + solutions + ": " + movesMade + " moves: [" +
                                        moves.stream().map(fromto -> (fromto[0] + 1) + "-" + (fromto[1] + 1))
                                                .collect(Collectors.joining(", ")) + ']');
                            }
                            break;
                        }
                        if (movesMade < bestMovesMade) {
                            seekSolutions(tryNewTubes, moves);
                        }
//                        else {
//                            log(tryNewTubes);
//                            System.out.println("Too many moves, abandon");
//                            System.out.println("==================================================");
//                        }
                    }
                }
            }
            if (!moveMade) {
//                log(tubes);
//                System.out.println("Stuck! No new moves");
//                System.out.println("==================================================");
                break;
            }
        }
    }

}
