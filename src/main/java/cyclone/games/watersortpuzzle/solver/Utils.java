package cyclone.games.watersortpuzzle.solver;

import java.lang.reflect.Array;

public class Utils {

    private Utils() {
    }

    public static <T> T[][] deepCopy(T[][] original) {
        T[][] copy = (T[][]) Array.newInstance(original.getClass().getComponentType(), original.length);
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }

    public static int[][] deepCopy(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }

}
