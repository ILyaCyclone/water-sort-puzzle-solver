package cyclone.games.watersortpuzzle.solver;

import java.util.Arrays;

import static cyclone.games.watersortpuzzle.solver.Color.*;

/**
 * A set of puzzles from different games.
 */
public class Puzzles {

    private Puzzles() {
    }

    public static final Puzzle AUG11_CHALLENGE = new Puzzle(new Color[][]{
            {YELLOW, DARKPURPLE, GREEN, DARKPURPLE, WHITE, GREEN},
            {GRAY, WHITE, WHITE, PURPLE, WHITE, DARKRED},
            {GRAY, WHITE, YELLOW, GREEN, PURPLE, DARKRED},
            {DARKPURPLE, YELLOW, DARKPURPLE, GRAY, YELLOW, DARKRED},
            {PURPLE, DARKPURPLE, YELLOW, GREEN, DARKRED, DARKPURPLE},
            {PURPLE, GREEN, GREEN, PURPLE, GRAY, YELLOW},
            {GRAY, GRAY, PURPLE, WHITE, DARKRED, DARKRED},
            emptyTube(6),
            emptyTube(6),
    }, WinCondition.SOLE_COLOR, DARKRED);

    // 40 moves < 45 dev
    public static final Puzzle AUG13_CHALLENGE = new Puzzle(new Color[][]{
            {GRAY, GRAY, GREEN, DARKRED, GRAY, GRAY},
            {DARKBLUE, DARKPURPLE, DARKBLUE, DARKBLUE, DARKPURPLE, DARKBLUE},
            {PURPLE, DARKRED, GRAY, PURPLE, DARKPURPLE, PURPLE},
            {WHITE, WHITE, GREEN, WHITE, WHITE, GREEN},
            {PURPLE, GREEN, PURPLE, PURPLE, DARKRED, DARKRED},
            {GREEN, DARKBLUE, GRAY, WHITE, WHITE, GREEN},
            {DARKBLUE, DARKPURPLE, DARKRED, DARKRED, DARKPURPLE, DARKPURPLE},
            emptyTube(6),
            emptyTube(6)
    });

    // 22 moves = dev
    public static final Puzzle AUG17_MEDIUM = new Puzzle(new Color[][]{
            {GREEN, DARKPURPLE, GREEN},
            {RED, YELLOW, DARKGREEN},
            {YELLOW, YELLOW, ORANGE},
            {RED, ORANGE, BLUE},
            {DARKGREEN, DARKPURPLE, PURPLE},
            {GRAY, PURPLE, GRAY},
            {GREEN, BLUE, BLUE},
            {ORANGE, GRAY, RED},
            {DARKGREEN, DARKPURPLE, PURPLE},
            emptyTube(3),
            emptyTube(3)
    });

    // aug 18, easy = 19 moves < 20 dev
    public static final Puzzle AUG18_EASY = new Puzzle(new Color[][]{
            {DARKBLUE, GREEN, DARKGREEN},
            {GREEN, PURPLE, DARKGREEN},
            {DARKRED, DARKRED, YELLOW},
            {PURPLE, YELLOW, ORANGE},
            {ORANGE, ORANGE, DARKGREEN},
            {GRAY, GRAY, DARKBLUE},
            {PURPLE, GREEN, DARKRED},
            {GRAY, DARKBLUE, YELLOW},
            emptyTube(3),
            emptyTube(3)
    });

    // aug 20 easy = 18 moves = 18 dev
    public static final Puzzle AUG20_EASY = new Puzzle(new Color[][]{
            {ORANGE, BLUE, GREEN},
            {GREEN, DARKPURPLE, DARKRED},
            {DARKRED, ORANGE, BLUE},
            {WHITE, PURPLE, PURPLE},
            {DARKPURPLE, GRAY, GREEN},
            {ORANGE, WHITE, PURPLE},
            {GRAY, BLUE, DARKRED},
            {DARKPURPLE, WHITE, GRAY},
            emptyTube(3),
            emptyTube(3)
    });

    // aug 18, medium = 22 moves = dev
    public static final Puzzle AUG18_MEDIUM = new Puzzle(new Color[][]{
            {RED, PURPLE, WHITE},
            {PURPLE, RED, DARKBLUE},
            {WHITE, BLUE, RED},
            {GREEN, ORANGE, ORANGE},
            {GREEN, DARKRED, DARKBLUE},
            {YELLOW, DARKRED, ORANGE},
            {GREEN, WHITE, DARKBLUE},
            {YELLOW, YELLOW, BLUE},
            {DARKRED, BLUE, PURPLE},
            emptyTube(3),
            emptyTube(3)
    });

    // aug 20 challenge = 33 moves = 33 dev
    public static final Puzzle AUG20_CHALLENGE = new Puzzle(new Color[][]{
            {BLUE, GRAY, BLUE, GRAY, DARKBLUE},
            {DARKBLUE, PURPLE, RED, PURPLE, DARKRED},
            {DARKRED, GRAY, RED, RED, YELLOW},
            {PURPLE, DARKRED, YELLOW, YELLOW, DARKBLUE},
            {BLUE, YELLOW, DARKRED, DARKRED, PURPLE},
            {BLUE, PURPLE, RED, BLUE, RED},
            {DARKBLUE, GRAY, DARKBLUE, GRAY, YELLOW},
            emptyTube(5),
            emptyTube(5)
    });

    // [many balls] 17 moves = dev
    public static final Puzzle AUG21_EASY = new Puzzle(new Color[][]{
            {BLUE, GREEN, WHITE},
            {BLUE, BLUE, GRAY},
            {RED, WHITE, RED},
            {BLUE, BLUE, WHITE},
            {GRAY, GRAY, RED},
            {YELLOW, BLUE, YELLOW},
            {WHITE, GREEN, GREEN},
            {YELLOW, WHITE, WHITE},
            emptyTube(3),
            emptyTube(3)
    });

    // 15 moves < dev 16 moves
    public static final Puzzle AUG22_EASY = new Puzzle(new Color[][]{
            {BLUE, RED, PURPLE},
            {RED, RED, YELLOW},
            {BLUE, PURPLE, BLUE},
            {ORANGE, YELLOW, ORANGE},
            {ORANGE, PURPLE, DARKGREEN},
            {DARKGREEN, YELLOW, DARKGREEN},
            emptyTube(3),
            emptyTube(3)
    });

    // [many balls] 23 moves = dev
    public static final Puzzle AUG22_MEDIUM = new Puzzle(new Color[][]{
            {YELLOW, ORANGE, GRAY, GRAY},
            {GRAY, DARKGREEN, YELLOW, ORANGE},
            {DARKGREEN, GRAY, ORANGE, DARKGREEN},
            {YELLOW, RED, YELLOW, RED},
            {YELLOW, YELLOW, GRAY, DARKGREEN},
            {YELLOW, ORANGE, GRAY, YELLOW},
            {GRAY, RED, GRAY, RED},
            emptyTube(4),
            emptyTube(4)
    });

    public static final Puzzle WATER_SORT_PUZZLE_24 = new Puzzle(new Color[][]{
            {GREEN, PURPLE, YELLOW, DARKPURPLE},
            {PURPLE, DARKBLUE, DARKBLUE, YELLOW},
            {GREEN, DARKGREEN, GREEN, DARKGREEN},
            {DARKGREEN, DARKBLUE, YELLOW, PURPLE},
            {DARKGREEN, DARKBLUE, PINK, DARKPURPLE},
            {PINK, DARKPURPLE, DARKPURPLE, YELLOW},
            {PURPLE, PINK, GREEN, PINK},
            emptyTube(4),
            emptyTube(4)
    });

    public static final Puzzle WATER_SORT_PUZZLE_27 = new Puzzle(new Color[][]{
            {DARKGREEN, BLUE, DARKBLUE, ORANGE},
            {DARKBLUE, PURPLE, GREEN, DARKGREEN},
            {BLUE, YELLOW, PINK, YELLOW},
            {BLUE, ORANGE, PURPLE, PURPLE},
            {DARKBLUE, GREEN, YELLOW, RED},
            {PINK, BLUE, GREEN, ORANGE},
            {DARKGREEN, PINK, DARKBLUE, RED},
            {RED, PURPLE, PINK, ORANGE},
            {GREEN, DARKGREEN, YELLOW, RED},
            emptyTube(4),
            emptyTube(4),
            emptyTube(4)
    });


    static Color[] emptyTube(int capacity) {
        Color[] tube = new Color[capacity];
        Arrays.fill(tube, EMPTY);
        return tube;
    }

}
