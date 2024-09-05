package cyclone.games.watersortpuzzle.solver;

import java.util.List;

public class Replay {

    private final TubesManipulator tubesManipulator;
    private final TubesFormatter tubesFormatter;

    public Replay() {
        this(new TubesManipulator(), new ColoredTubesFormatter());
    }

    public Replay(TubesManipulator tubesManipulator, TubesFormatter tubesFormatter) {
        this.tubesManipulator = tubesManipulator;
        this.tubesFormatter = tubesFormatter;
    }

    public String replay(Puzzle puzzle, List<int[]> moves) {
        Color[][] tubes = puzzle.tubes();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(tubesFormatter.format(tubes)).append('\n');
        for (int moveIndex = 0; moveIndex < moves.size(); moveIndex++) {
            int[] fromto = moves.get(moveIndex);
            stringBuilder.append("%d. move %d to %d%n".formatted(moveIndex + 1, fromto[0] + 1, fromto[1] + 1));
            tubes = tubesManipulator.makeAMove(tubes, fromto[1], fromto[0]);

            stringBuilder.append("--------------------------------------------------").append('\n');
            stringBuilder.append(tubesFormatter.format(tubes)).append('\n');
        }

        boolean check = puzzle.winCondition().check(tubes);
        if (check) {
            stringBuilder.append("Win condition met");
        } else {
            stringBuilder.append("WIN CONDITION NOT MET");
        }

        return stringBuilder.toString();
    }

}
