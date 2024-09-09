# Water Sort Puzzle Algorithm

An algorithm for solving "water sort puzzle" games.

## What is "water sort puzzle" game?

"Water sort puzzle" games are a popular type of games, mostly found on mobile devices or browser-based platforms, where
you need to sort colored liquids or balls into separate tubes. The goal is usually to sort all colors into separate tubes
in the fewest possible moves.

## What is this project?

This project provides algorithms for solving "water sort puzzle" games, guaranteeing solutions with the minimal
possible moves while trying its best to be fast.  
Available algorithms are:
- single-threaded, depth-first;
- multi-threaded, depth-first;
- single-threaded, breadth-first (default).

It also supports easily pluggable non-standard win conditions. Currently available are:
- sort all colors into separate tubes;
- sort only a selected color into an exclusive tube.

## Usage

### CLI

With fancy colors too!

![image](https://github.com/user-attachments/assets/b5a582f7-85e6-449f-9bd6-be9bc6defcf9)

```
usage: water-sorter-puzzle-solver <action> [options]
Action is one of:
- solve - solve the puzzle (required options: puzzle);
- replay - replay moves (required options: puzzle, moves);
- print - just print the puzzle (required options: puzzle);
- list-colors - list supported color names with respective number;
- help - print help;
- version - print app version.

Options:
 -p,--puzzle <puzzle>               A puzzle, where colors are represented in a form of integers or
                                    color names, each tube written top-down.
                                    E.g.: [1,2,3][4,5,6][0,0,0] - 3 tubes, the first tube have color
                                    (top-down) [1, 2, 3], the second tube [4, 5, 6], the third tube is
                                    empty with capacity of 3.
                                    Or: [red,green,blue][yellow,white,gray][empty,empty,empty].
                                    For color names see "list-colors" action.
 -w,--win-condition <condition>     Win condition. Supported values:
                                    - "standard" - all colors into separate tubes;
                                    - "sole-color:<color>" - sort only <color> into separate tube,
                                    where <color> is an integer or a name of a color.
                                    Default: standard.
 -a,--algorithm <arg>               Choose a solver algorithm. Supported values:
                                    - "single" - single-threaded solver, depth-first;
                                    - "multi" - multi-threaded solver, depth-first;
                                    - "singleV2" - single-threaded solver, breadth-first.
                                    Default: singleV2.
 -m,--moves <moves>                 Moves in a form of from-to pairs, 1-based tubes indexes. E.g.:
                                    1-3,2-4,1-2.
 -c,--colored-output <true|false>   Colorize the output? Default: true
                                    See also: "install-ansi option.
    --install-ansi                  Sometimes a console (e.g. Windows CMD) won't support colored
                                    output, showing weird symbols instead. Turn on this option then.
                                    Default: no action.
```

### Code

```java
Puzzle puzzle = new Puzzle(new Color[][]{
    {DARKBLUE, GREEN, DARKGREEN}, // from top to bottom
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

// Solver solver = new SingleThreadedSolver();
//Solver solver = new MultiThreadedSolver();
Solver solver = new SingleThreadedSolverV2();

long startNano = System.nanoTime();

Solution solution = solver.solve(puzzle);

long endNano = System.nanoTime();

Replay replay = new Replay();
System.out.println(replay.replay(puzzle, solution.moves()));

System.out.printf("Elapsed %.2f sec".formatted((endNano - startNano)/1_000_000_000f));
```

Output:

```
   DARKBLUE |      GREEN |    DARKRED |     PURPLE |     ORANGE |       GRAY |     PURPLE |       GRAY |          - |          - |
      GREEN |     PURPLE |    DARKRED |     YELLOW |     ORANGE |       GRAY |      GREEN |   DARKBLUE |          - |          - |
  DARKGREEN |  DARKGREEN |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |     YELLOW |          - |          - |
          1 |          2 |          3 |          4 |          5 |          6 |          7 |          8 |          9 |         10 |
1. move 2 to 9
--------------------------------------------------
   DARKBLUE |          - |    DARKRED |     PURPLE |     ORANGE |       GRAY |     PURPLE |       GRAY |          - |          - |
      GREEN |     PURPLE |    DARKRED |     YELLOW |     ORANGE |       GRAY |      GREEN |   DARKBLUE |          - |          - |
  DARKGREEN |  DARKGREEN |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |     YELLOW |      GREEN |          - |
          1 |          2 |          3 |          4 |          5 |          6 |          7 |          8 |          9 |         10 |
2. move 2 to 10
--------------------------------------------------
   DARKBLUE |          - |    DARKRED |     PURPLE |     ORANGE |       GRAY |     PURPLE |       GRAY |          - |          - |
      GREEN |          - |    DARKRED |     YELLOW |     ORANGE |       GRAY |      GREEN |   DARKBLUE |          - |          - |
  DARKGREEN |  DARKGREEN |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |     YELLOW |      GREEN |     PURPLE |
          1 |          2 |          3 |          4 |          5 |          6 |          7 |          8 |          9 |         10 |
3. move 4 to 10
--------------------------------------------------
   DARKBLUE |          - |    DARKRED |          - |     ORANGE |       GRAY |     PURPLE |       GRAY |          - |          - |
      GREEN |          - |    DARKRED |     YELLOW |     ORANGE |       GRAY |      GREEN |   DARKBLUE |          - |     PURPLE |
  DARKGREEN |  DARKGREEN |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |     YELLOW |      GREEN |     PURPLE |
          1 |          2 |          3 |          4 |          5 |          6 |          7 |          8 |          9 |         10 |
4. move 7 to 10
--------------------------------------------------
   DARKBLUE |          - |    DARKRED |          - |     ORANGE |       GRAY |          - |       GRAY |          - |     PURPLE |
      GREEN |          - |    DARKRED |     YELLOW |     ORANGE |       GRAY |      GREEN |   DARKBLUE |          - |     PURPLE |
  DARKGREEN |  DARKGREEN |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |     YELLOW |      GREEN |     PURPLE |
          1 |          2 |          3 |          4 |          5 |          6 |          7 |          8 |          9 |         10 |
5. move 7 to 9
--------------------------------------------------
   DARKBLUE |          - |    DARKRED |          - |     ORANGE |       GRAY |          - |       GRAY |          - |     PURPLE |
      GREEN |          - |    DARKRED |     YELLOW |     ORANGE |       GRAY |          - |   DARKBLUE |      GREEN |     PURPLE |
  DARKGREEN |  DARKGREEN |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |     YELLOW |      GREEN |     PURPLE |
          1 |          2 |          3 |          4 |          5 |          6 |          7 |          8 |          9 |         10 |
6. move 3 to 7
--------------------------------------------------
   DARKBLUE |          - |          - |          - |     ORANGE |       GRAY |          - |       GRAY |          - |     PURPLE |
      GREEN |          - |    DARKRED |     YELLOW |     ORANGE |       GRAY |    DARKRED |   DARKBLUE |      GREEN |     PURPLE |
  DARKGREEN |  DARKGREEN |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |     YELLOW |      GREEN |     PURPLE |
          1 |          2 |          3 |          4 |          5 |          6 |          7 |          8 |          9 |         10 |
7. move 3 to 7
--------------------------------------------------
   DARKBLUE |          - |          - |          - |     ORANGE |       GRAY |    DARKRED |       GRAY |          - |     PURPLE |
      GREEN |          - |          - |     YELLOW |     ORANGE |       GRAY |    DARKRED |   DARKBLUE |      GREEN |     PURPLE |
  DARKGREEN |  DARKGREEN |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |     YELLOW |      GREEN |     PURPLE |
          1 |          2 |          3 |          4 |          5 |          6 |          7 |          8 |          9 |         10 |
8. move 4 to 3
--------------------------------------------------
   DARKBLUE |          - |          - |          - |     ORANGE |       GRAY |    DARKRED |       GRAY |          - |     PURPLE |
      GREEN |          - |     YELLOW |          - |     ORANGE |       GRAY |    DARKRED |   DARKBLUE |      GREEN |     PURPLE |
  DARKGREEN |  DARKGREEN |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |     YELLOW |      GREEN |     PURPLE |
          1 |          2 |          3 |          4 |          5 |          6 |          7 |          8 |          9 |         10 |
9. move 5 to 4
--------------------------------------------------
   DARKBLUE |          - |          - |          - |          - |       GRAY |    DARKRED |       GRAY |          - |     PURPLE |
      GREEN |          - |     YELLOW |     ORANGE |     ORANGE |       GRAY |    DARKRED |   DARKBLUE |      GREEN |     PURPLE |
  DARKGREEN |  DARKGREEN |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |     YELLOW |      GREEN |     PURPLE |
          1 |          2 |          3 |          4 |          5 |          6 |          7 |          8 |          9 |         10 |
10. move 5 to 4
--------------------------------------------------
   DARKBLUE |          - |          - |     ORANGE |          - |       GRAY |    DARKRED |       GRAY |          - |     PURPLE |
      GREEN |          - |     YELLOW |     ORANGE |          - |       GRAY |    DARKRED |   DARKBLUE |      GREEN |     PURPLE |
  DARKGREEN |  DARKGREEN |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |     YELLOW |      GREEN |     PURPLE |
          1 |          2 |          3 |          4 |          5 |          6 |          7 |          8 |          9 |         10 |
11. move 2 to 5
--------------------------------------------------
   DARKBLUE |          - |          - |     ORANGE |          - |       GRAY |    DARKRED |       GRAY |          - |     PURPLE |
      GREEN |          - |     YELLOW |     ORANGE |  DARKGREEN |       GRAY |    DARKRED |   DARKBLUE |      GREEN |     PURPLE |
  DARKGREEN |          - |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |     YELLOW |      GREEN |     PURPLE |
          1 |          2 |          3 |          4 |          5 |          6 |          7 |          8 |          9 |         10 |
12. move 6 to 2
--------------------------------------------------
   DARKBLUE |          - |          - |     ORANGE |          - |          - |    DARKRED |       GRAY |          - |     PURPLE |
      GREEN |          - |     YELLOW |     ORANGE |  DARKGREEN |       GRAY |    DARKRED |   DARKBLUE |      GREEN |     PURPLE |
  DARKGREEN |       GRAY |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |     YELLOW |      GREEN |     PURPLE |
          1 |          2 |          3 |          4 |          5 |          6 |          7 |          8 |          9 |         10 |
13. move 6 to 2
--------------------------------------------------
   DARKBLUE |          - |          - |     ORANGE |          - |          - |    DARKRED |       GRAY |          - |     PURPLE |
      GREEN |       GRAY |     YELLOW |     ORANGE |  DARKGREEN |          - |    DARKRED |   DARKBLUE |      GREEN |     PURPLE |
  DARKGREEN |       GRAY |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |     YELLOW |      GREEN |     PURPLE |
          1 |          2 |          3 |          4 |          5 |          6 |          7 |          8 |          9 |         10 |
14. move 1 to 6
--------------------------------------------------
          - |          - |          - |     ORANGE |          - |          - |    DARKRED |       GRAY |          - |     PURPLE |
      GREEN |       GRAY |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |   DARKBLUE |      GREEN |     PURPLE |
  DARKGREEN |       GRAY |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |     YELLOW |      GREEN |     PURPLE |
          1 |          2 |          3 |          4 |          5 |          6 |          7 |          8 |          9 |         10 |
15. move 1 to 9
--------------------------------------------------
          - |          - |          - |     ORANGE |          - |          - |    DARKRED |       GRAY |      GREEN |     PURPLE |
          - |       GRAY |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |   DARKBLUE |      GREEN |     PURPLE |
  DARKGREEN |       GRAY |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |     YELLOW |      GREEN |     PURPLE |
          1 |          2 |          3 |          4 |          5 |          6 |          7 |          8 |          9 |         10 |
16. move 1 to 5
--------------------------------------------------
          - |          - |          - |     ORANGE |  DARKGREEN |          - |    DARKRED |       GRAY |      GREEN |     PURPLE |
          - |       GRAY |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |   DARKBLUE |      GREEN |     PURPLE |
          - |       GRAY |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |     YELLOW |      GREEN |     PURPLE |
          1 |          2 |          3 |          4 |          5 |          6 |          7 |          8 |          9 |         10 |
17. move 8 to 2
--------------------------------------------------
          - |       GRAY |          - |     ORANGE |  DARKGREEN |          - |    DARKRED |          - |      GREEN |     PURPLE |
          - |       GRAY |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |   DARKBLUE |      GREEN |     PURPLE |
          - |       GRAY |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |     YELLOW |      GREEN |     PURPLE |
          1 |          2 |          3 |          4 |          5 |          6 |          7 |          8 |          9 |         10 |
18. move 8 to 6
--------------------------------------------------
          - |       GRAY |          - |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |          - |      GREEN |     PURPLE |
          - |       GRAY |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |          - |      GREEN |     PURPLE |
          - |       GRAY |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |     YELLOW |      GREEN |     PURPLE |
          1 |          2 |          3 |          4 |          5 |          6 |          7 |          8 |          9 |         10 |
19. move 8 to 3
--------------------------------------------------
          - |       GRAY |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |          - |      GREEN |     PURPLE |
          - |       GRAY |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |          - |      GREEN |     PURPLE |
          - |       GRAY |     YELLOW |     ORANGE |  DARKGREEN |   DARKBLUE |    DARKRED |          - |      GREEN |     PURPLE |
          1 |          2 |          3 |          4 |          5 |          6 |          7 |          8 |          9 |         10 |
Win condition met
Elapsed 0,30 sec
```
