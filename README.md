# Water Sort Puzzle Algorithm

An algorithm for solving "water sort puzzle" games.

## What is "water sort puzzle" game?

"Water sort puzzle" games are a popular type of game, mostly found on mobile devices or browser-based platforms, where
you need to sort colored liquids or balls into separate tubes. The goal is usually to solve the puzzle in the fewest
possible moves.

## What is this project?

This project provides an algorithm for solving "water sort puzzle" games, guaranteeing solutions with the minimal
possible moves while trying its best to be fast.  
It features a simpler single-threaded variant and a more performant multithreaded variant.

It also supports easily pluggable non-standard win conditions. Currently available are:
- sort all colors into separate tubes;
- sort only a selected color into an exclusive tube.

## Example

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
Solver solver = new MultiThreadedSolver();

Solution solution = solver.solve(puzzle);
```

Output:

```
Solution 1: 23 moves: [2-9, 2-10, 4-10, 7-10, 7-9, 3-7, 3-7, 3-4, 1-3, 1-9, 1-2, 4-1, 4-1, 5-4, 5-4, 5-2, 6-5, 6-5, 3-6, 8-3, 8-6, 3-5, 8-1]
Solution 2: 22 moves: [2-9, 2-10, 4-10, 7-10, 7-9, 3-7, 3-7, 3-4, 1-3, 1-9, 1-2, 4-1, 4-1, 5-4, 5-4, 5-2, 6-5, 6-5, 3-6, 8-5, 8-6, 8-1]
Solution 3: 21 moves: [2-9, 2-10, 4-10, 7-10, 7-9, 3-7, 3-7, 3-4, 1-3, 1-9, 1-2, 6-1, 6-1, 3-6, 8-1, 8-6, 4-8, 4-8, 5-4, 5-4, 5-2]
Solution 4: 20 moves: [2-9, 2-10, 4-10, 7-10, 7-9, 3-7, 3-7, 3-4, 6-3, 6-3, 1-6, 1-9, 1-2, 8-3, 8-6, 4-8, 4-8, 5-4, 5-4, 5-2]
Solution 5: 19 moves: [2-9, 2-10, 4-10, 7-10, 7-9, 3-7, 3-7, 4-3, 5-4, 5-4, 2-5, 6-2, 6-2, 1-6, 1-9, 1-5, 8-2, 8-6, 8-3]
Finished in time
==================================================
Best solution in 19 moves: [2-9, 2-10, 4-10, 7-10, 7-9, 3-7, 3-7, 4-3, 5-4, 5-4, 2-5, 6-2, 6-2, 1-6, 1-9, 1-5, 8-2, 8-6, 8-3]
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
==================================================
Elapsed 1,99 sec
```
