package cyclone.games.watersortpuzzle.solver;

/**
 * Format tubes as string.
 */
public interface TubesFormatter {

    String format(Color[][] tubes);

    default String format(Color color) {
        return color.name();
    }

}
