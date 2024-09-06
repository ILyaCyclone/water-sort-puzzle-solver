package cyclone.games.watersortpuzzle.solver;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import static cyclone.games.watersortpuzzle.solver.Color.EMPTY;

/**
 * Ansi colored output tubes as string.
 */
public class ColoredTubesFormatter implements TubesFormatter {

    public ColoredTubesFormatter() {
        this(false);
    }

    public ColoredTubesFormatter(boolean installAnsiCmd) {
        if (installAnsiCmd && !AnsiConsole.isInstalled()) {
            AnsiConsole.systemInstall();
            Runtime.getRuntime().addShutdownHook(new Thread(AnsiConsole::systemUninstall));
        }
    }

    @Override
    public String format(Color[][] tubes) {
        int capacity = tubes[0].length;

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < capacity; i++) {
            for (Color[] tube : tubes) {
                Ansi ansi = ansiColor(tube[i]);
                Ansi formatted = ansi.format(" %10s", tube[i] == EMPTY ? "-" : tube[i]).reset();
                stringBuilder.append(formatted);
                stringBuilder.append(" |");
            }
            stringBuilder.append("\n");
        }
        for (int i = 0; i < tubes.length; i++) {
            stringBuilder.append(" %10d".formatted(i + 1));
            stringBuilder.append(" |");
        }
        return stringBuilder.toString();
    }

    public String format(Color color) {
        Ansi ansiColor = ansiColor(color);
        Ansi formatted = ansiColor.format(color.name()).reset();
        return formatted.toString();
    }

    private Ansi ansiColor(Color color) {
        Ansi ansi = Ansi.ansi();
        return switch (color) {
            case RED -> ansi.fg(Ansi.Color.RED);
            case GREEN -> ansi.fgRgb(0, 255, 0); // lime
            case BLUE -> ansi.fg(Ansi.Color.CYAN);
            case YELLOW -> ansi.fg(Ansi.Color.YELLOW);
            case WHITE -> ansi.fgRgb(211, 211, 211); // lightgray
            case DARKBLUE -> ansi.fg(Ansi.Color.BLUE);
            case GRAY -> ansi.fgRgb(128, 128, 128);
            case ORANGE -> ansi.fgRgb(255, 165, 0);
            case PURPLE -> ansi.fgRgb(128, 0, 128);
            case DARKRED -> ansi.fgRgb(139, 0, 0);
            case DARKGREEN -> ansi.fgRgb(0, 128, 0); // green
            case DARKPURPLE -> ansi.fgRgb(75, 0, 130); // indigo
            case PINK -> ansi.fgRgb(255, 192, 203);
            default -> ansi;
        };
    }

}
