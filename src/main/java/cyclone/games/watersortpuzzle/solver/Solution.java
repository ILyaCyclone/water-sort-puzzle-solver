package cyclone.games.watersortpuzzle.solver;

import java.util.List;

/**
 * A variant of solution, represented in made moves.
 * Moves are 0-based indexes of {@code [fromTube, toTube]}.
 */
public record Solution(List<int[]> moves) {
}
