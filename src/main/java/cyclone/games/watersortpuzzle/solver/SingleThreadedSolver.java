package cyclone.games.watersortpuzzle.solver;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Main business logic.
 * Solve puzzle using single thread, depth-first.
 */
public class SingleThreadedSolver implements Solver {

    private final TubesManipulator tubesManipulator = new TubesManipulator();
    private final PuzzleValidator puzzleValidator = new PuzzleValidator();

    private final Map<TubesState, Integer> states = new HashMap<>();
    private List<int[]> bestSolution;
    private int bestMovesMade = Integer.MAX_VALUE;
    private int solutions = 0;


    @Override
    public Solution solve(Puzzle puzzle) {
        puzzleValidator.validate(puzzle);

        Color[][] tubes = puzzle.tubes();
        states.put(new TubesState(tubes), 0);

//        printer.log(tubes);
//        System.out.println("=======================================");

        seekSolutions(puzzle, Collections.emptyList());

        return Optional.ofNullable(bestSolution)
                .map(Solution::new)
                .orElse(null);
    }

    private void seekSolutions(Puzzle puzzle, List<int[]> previousMoves) {
        Color[][] tubes = puzzle.tubes();
        WinCondition winCondition = puzzle.winCondition();

        boolean solved = false;
        while (!solved) {
            boolean moveMade = false;
            for (int i = 0; i < tubes.length; i++) {
                List<Integer> possibleMoves = tubesManipulator.possibleMoves(tubes, i);
                for (Integer possibleMove : possibleMoves) {
                    Color[][] newTubes = tubesManipulator.makeAMove(tubes, possibleMove, i);
                    TubesState newTubesState = new TubesState(newTubes);
                    if (!states.containsKey(newTubesState) || states.get(newTubesState) > previousMoves.size() + 1) {
                        moveMade = true;
                        List<int[]> moves = new LinkedList<>(previousMoves);
                        moves.add(new int[]{i, possibleMove});
                        int movesMade = moves.size();
                        states.put(newTubesState, movesMade);

                        if (winCondition.check(newTubes)) {
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
                            seekSolutions(new Puzzle(newTubes, winCondition), moves);
                        }
//                        else {
//                            log(newTubes);
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
