package cyclone.games.watersortpuzzle.solver;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static cyclone.games.watersortpuzzle.solver.Color.EMPTY;

public class Solver {

    private static int bestMovesMade = Integer.MAX_VALUE;
    private static List<int[]> bestSolution;
    private static int solutions = 0;
    private static int capacity;
    private static final Map<PuzzleState, Integer> states = new HashMap<>();

    public Solution solve(Puzzle puzzle) {
        Color[][] tubes = puzzle.tubes();

        if (Arrays.stream(tubes).map(tube -> tube.length).distinct().count() > 1) {
            int[] longerTube = IntStream.range(0, tubes.length)
                    .mapToObj(i -> new int[]{i, tubes[i].length})
                    .max(Comparator.comparingInt(pair -> pair[1]))
                    .get();

            throw new IllegalArgumentException("Unequal tubes length: tube " + (longerTube[0] + 1) + " has length " + longerTube[1]);
        }

        capacity = tubes[0].length;

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
                List<Integer> possibleMoves = possibleMoves(tubes, i);
                for (Integer possibleMove : possibleMoves) {
                    Color[][] tryNewTubes = makeAMove(tubes, possibleMove, i);
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
                        if (isSolved(tryNewTubes)) {
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

    public Color[][] makeAMove(Color[][] tubes, int toTube, int fromTube) {
        Color[][] tryNewTubes = Utils.deepCopy(tubes);
        Color color = null;
        for (int i = 0; i < tryNewTubes[fromTube].length; i++) {
            Color possibleColor = tryNewTubes[fromTube][i];
            if (possibleColor != EMPTY) {
                color = possibleColor;
                tryNewTubes[fromTube][i] = EMPTY;
                break;
            }
        }
        if (color == null) throw new RuntimeException("Could not find top color from " + fromTube + " to " + toTube);

        boolean placed = false;
        for (int i = tryNewTubes[toTube].length - 1; i >= 0; i--) {
            if (tryNewTubes[toTube][i] == EMPTY) {
                tryNewTubes[toTube][i] = color;
                placed = true;
                break;
            }
        }
        if (!placed) throw new RuntimeException("Could not place new color from " + fromTube + " to " + toTube);
        return tryNewTubes;
    }

    private List<Integer> possibleMoves(Color[][] tubes, int tubeIndex) {
        Color[] tube = tubes[tubeIndex];
        Color topColor = topColor(tube);
        boolean soleColor = isSoleColor(tube);

        // condition 1: skip if current tube is empty
        if (topColor == EMPTY) return Collections.emptyList();
        // condition 2: skip if current tube is complete
        if (isSoleColor(tube)) {
            if (countBalls(tube) == capacity) return Collections.emptyList();
        }

        List<Integer> moves = new ArrayList<>();
        for (int i = 0; i < tubes.length; i++) {
            // condition 3: skip tube itself
            if (i == tubeIndex) continue;

            Color[] possibleTube = tubes[i];
            int possibleCountBalls = countBalls(possibleTube);
            // condition 4: skip full tube
            if (possibleCountBalls == capacity) continue;

            Color possibleTopColor = topColor(possibleTube);
            // condition 5: skip tube not suitable by color or empty space
            if (!(possibleTopColor == topColor || possibleTopColor == EMPTY)) continue;

            // optimization 1: skip tube if making move will it same as current
            Color[][] tryMoveTubes = makeAMove(tubes, i, tubeIndex);
            if (Arrays.deepEquals(tryMoveTubes[i], tube)) continue;

            if (soleColor) {
                // optimization 2: don't move to empty tube if current tube has sole color
                if (possibleTopColor == EMPTY) continue;

                // [this is wrong] optimization 3: skip if current tube is sole and target is not
//                boolean possibleTubeSoleColor = isSoleColor(possibleTube);
//                if (!possibleTubeSoleColor) continue;

                // optimization 4: don't move if current tube has more of sole color balls
                if (countBalls(tube) > possibleCountBalls) continue;
            }

            moves.add(i);
        }
        return moves;
    }

    private boolean isSoleColor(Color[] tube) {
        return Arrays.stream(tube).filter(color -> color != EMPTY).distinct().count() == 1;
    }

    private Color topColor(Color[] tube) {
        return Arrays.stream(tube)
                .filter(color -> color != EMPTY)
                .findFirst()
                .orElse(EMPTY);
    }

    private int countBalls(Color[] tube) {
        return (int) Arrays.stream(tube).filter(color -> color != EMPTY).count();
    }

    private boolean isSolved(Color[][] tubes) {
        for (Color[] tube : tubes) {
            if (Arrays.stream(tube).allMatch(color -> color == EMPTY)) continue;
            if (countBalls(tube) != capacity) return false;

            if (Arrays.stream(tube).distinct()
                    .filter(color -> color != EMPTY)
                    .count() > 1) return false;
        }
        return true;
    }

    private boolean isSolved(Color[][] tubes, Color soleColor) {
        for (Color[] tube : tubes) {
            if (Arrays.stream(tube).filter(color -> color == soleColor).count() == capacity) return true;
        }
        return false;
    }

}
