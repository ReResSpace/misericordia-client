package halq.misericordia.fun.gui.console.core;

import halq.misericordia.fun.gui.console.DrawConsole;
import halq.misericordia.fun.utils.Minecraftable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Halq
 * @since 18/06/2023 at 19:28
 */

public class ConsoleAPI {

    public static List<String> suggestions = new ArrayList<>();
    public static int suggestionIndex;
    public static boolean chatlog = false;
    static boolean prefix = true;
    static String prefixString = "<Misericordia Console> | ";

    public static void log(String message) {
        String[] lines = message.split("\n");
        int maxWidth = (int) (390 * 2);
        for (String line : lines) {
            List<String> wrappedLines = Minecraftable.mc.fontRenderer.listFormattedStringToWidth(line, maxWidth);
            DrawConsole.logs.addAll(wrappedLines);
        }

        while (DrawConsole.logs.size() > DrawConsole.maxLogs) {
            DrawConsole.logs.remove(0);
        }
    }
}
