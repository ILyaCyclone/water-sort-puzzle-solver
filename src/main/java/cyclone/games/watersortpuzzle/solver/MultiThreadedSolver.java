package cyclone.games.watersortpuzzle.solver;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MultiThreadedSolver implements Solver {

    private final Map<PuzzleState, Integer> states = new ConcurrentHashMap<>();
    private final TubesManipulator tubesManipulator = new TubesManipulator();

    private List<int[]> bestSolution;
    private final AtomicInteger bestMovesMade = new AtomicInteger(Integer.MAX_VALUE);
    private int solutions = 0;

    private SolutionVerifier solutionVerifier;

    private static final Object stdoutLock = new Object();

    @Override
    public Solution solve(Puzzle puzzle) {
        Color[][] tubes = puzzle.tubes();
        int capacity = tubes[0].length;

        if (Arrays.stream(tubes).map(tube -> tube.length).distinct().count() > 1) {
            int[] longerTube = IntStream.range(0, tubes.length)
                    .mapToObj(i -> new int[]{i, tubes[i].length})
                    .max(Comparator.comparingInt(pair -> pair[1]))
                    .get();

            throw new IllegalArgumentException("Unequal tubes length: tube " + (longerTube[0] + 1) + " has length " + longerTube[1]);
        }


        // check color numbers according to tubes length
        Map<Color, Integer> totalBallsCount = Arrays.stream(tubes).flatMap(Arrays::stream)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));

        boolean ballsCountError = false;
        StringJoiner ballsCountErrorJoiner = new StringJoiner("; ");
        for (Map.Entry<Color, Integer> colorCount : totalBallsCount.entrySet()) {
            if (colorCount.getValue() % capacity != 0) {
                ballsCountError = true;
                ballsCountErrorJoiner.add(colorCount.getKey() + ": " + colorCount.getValue());
            }
        }
        if (ballsCountError)
            throw new IllegalArgumentException("Color count is not divisible by tubes' length (" + capacity + "): " +
                    ballsCountErrorJoiner);

        solutionVerifier = SolutionVerifiers.forPuzzle(puzzle);

        states.put(new PuzzleState(tubes), 0);

//        log(tubes);
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

                        if (solutionVerifier.isSolved(tryNewTubes)) {
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
