package cyclone.games.watersortpuzzle.solver;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SingleThreadedSolver implements Solver {

    private final Map<PuzzleState, Integer> states = new HashMap<>();
    private final TubesManipulator tubesManipulator = new TubesManipulator();

    private List<int[]> bestSolution;
    private int bestMovesMade = Integer.MAX_VALUE;
    private int solutions = 0;

    private SolutionVerifier solutionVerifier;

    @Override
    public Solution solve(Puzzle puzzle) {
        Color[][] tubes = puzzle.tubes();

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


        int capacity = tubes[0].length;
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

        makeMoves(tubes, Collections.emptyList());

        return Optional.ofNullable(bestSolution)
                .map(Solution::new)
                .orElse(null);
    }

    private void makeMoves(Color[][] tubes, List<int[]> previousMoves) {
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
                        if (solutionVerifier.isSolved(tryNewTubes)) {
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
                            makeMoves(tryNewTubes, moves);
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
