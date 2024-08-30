package cyclone.games.watersortpuzzle.solver;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MultiThreadedSolver implements Solver {

    private final TubesManipulator tubesManipulator = new TubesManipulator();
    private final PuzzleValidator puzzleValidator = new PuzzleValidator();
    private SolutionCheck solutionCheck;

    private final Map<PuzzleState, Integer> states = new ConcurrentHashMap<>();
    private List<int[]> bestSolution;
    private final AtomicInteger bestMovesMade = new AtomicInteger(Integer.MAX_VALUE);
    private int solutions = 0;

    private static final Object stdoutLock = new Object();

    @Override
    public Solution solve(Puzzle puzzle) {
        puzzleValidator.validate(puzzle);

        solutionCheck = SolutionChecks.forPuzzle(puzzle);

        Color[][] tubes = puzzle.tubes();
        states.put(new PuzzleState(tubes), 0);

//        printer.log(tubes);
//        System.out.println("=======================================");

        ForkJoinPool fjp = new ForkJoinPool();
        fjp.invoke(new MoveTask(tubes, Collections.emptyList()));
        fjp.shutdown();

        try {
            if (fjp.awaitTermination(5, TimeUnit.MINUTES)) {
                System.out.println("Finished in time");
            } else {
                System.out.println("~~~ Time out ~~~");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("~~~ interrupted ~~~");
        }

        return Optional.ofNullable(bestSolution)
                .map(Solution::new)
                .orElse(null);
    }

    private class MoveTask extends RecursiveTask<Void> {
        private final Color[][] tubes;
        private final List<int[]> previousMoves;

        public MoveTask(Color[][] tubes, List<int[]> previousMoves) {
            this.tubes = tubes;
            this.previousMoves = previousMoves;
        }

        @Override
        protected Void compute() {
            List<MoveTask> subtasks = new ArrayList<>();

            for (int i = 0; i < tubes.length; i++) {
                List<Integer> possibleMoves = tubesManipulator.possibleMoves(tubes, i);
                for (Integer possibleMove : possibleMoves) {
                    Color[][] tryNewTubes = tubesManipulator.makeAMove(tubes, possibleMove, i);
                    PuzzleState tryPuzzleState = new PuzzleState(tryNewTubes);

                    if (!states.containsKey(tryPuzzleState) || states.get(tryPuzzleState) > previousMoves.size() + 1) {
                        List<int[]> moves = new LinkedList<>(previousMoves);
                        int[] move = {i, possibleMove};
                        moves.add(move);
                        int movesMade = moves.size();
                        states.put(tryPuzzleState, movesMade);

                        if (solutionCheck.isSolved(tryNewTubes)) {
                            if (movesMade < bestMovesMade.get()) {
                                // Update bestMovesMade if the current solution is better
                                bestMovesMade.getAndUpdate(existingBest -> Math.min(existingBest, movesMade));
                                synchronized (MultiThreadedSolver.this) {
                                    if (movesMade == bestMovesMade.get()) {
                                        solutions++;
                                        bestSolution = new LinkedList<>(moves);
                                        synchronized (stdoutLock) {
                                            System.out.println("Solution " + solutions + ": " + movesMade + " moves: [" +
                                                    moves.stream().map(fromto -> (fromto[0] + 1) + "-" + (fromto[1] + 1))
                                                            .collect(Collectors.joining(", ")) + ']');
                                        }
                                    }
                                }
                            }
                            break;
                        }

                        if (movesMade < bestMovesMade.get()) {
                            MoveTask subTask = new MoveTask(tryNewTubes, moves);
                            subTask.fork();
                            subtasks.add(subTask);
                        }
                    }
                }
            }

            for (MoveTask subtask : subtasks) {
                subtask.join();
            }

            return null;
        }
    }

}
