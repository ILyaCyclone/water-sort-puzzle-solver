package cyclone.games.watersortpuzzle.solver;

/**
 * Win condition.
 */
public interface WinCondition {

    WinCondition STANDARD = new StandardWinCondition();

    boolean check(Color[][] tubes);
}
