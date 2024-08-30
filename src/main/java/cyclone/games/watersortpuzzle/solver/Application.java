package cyclone.games.watersortpuzzle.solver;

import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {
//        Puzzle puzzle = Puzzles.AUG18_EASY;
//        Puzzle puzzle = Puzzles.AUG11_CHALLENGE; // sole color
        Puzzle puzzle = Puzzles.AUG22_MEDIUM; // slow

        PrintStream out = System.out;

//        Solver solver = new SingleThreadedSolver();
        Solver solver = new MultiThreadedSolver();

        long startNano = System.nanoTime();
        Solution solution = solver.solve(puzzle);

        long elapsedNano = System.nanoTime() - startNano;
        float elapsedSeconds = elapsedNano / 1_000_000_000f;

        if (solution == null) {
            out.println("Elapsed %.2f sec".formatted(elapsedSeconds));
            out.println("Solution not found");
            return;
        }

        List<int[]> moves = solution.moves();
        TubesManipulator tubesManipulator = new TubesManipulator();
        out.println("==================================================");
        out.println("Best solution in " + moves.size() + " moves: [" +
                moves.stream().map(fromto -> (fromto[0] + 1) + "-" + (fromto[1] + 1))
                        .collect(Collectors.joining(", ")) + ']');
        Color[][] tubes = puzzle.tubes();

        Printer printer = new Printer(out);
        printer.log(tubes);

        Color[][] logTubes = Utils.deepCopy(tubes);
        for (int logMoveIndex = 0; logMoveIndex < moves.size(); logMoveIndex++) {
            int[] fromto = moves.get(logMoveIndex);
            out.println((logMoveIndex + 1) + ". move " + (fromto[0] + 1) + " to " + (fromto[1] + 1));
            logTubes = tubesManipulator.makeAMove(logTubes, fromto[1], fromto[0]);

            out.println("--------------------------------------------------");
            printer.log(logTubes);
        }
        out.println("==================================================");
        out.println("Elapsed %.2f sec".formatted(elapsedSeconds));
    }

}
