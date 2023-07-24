package halq.misericordia.fun.gui.console.core;

import halq.misericordia.fun.gui.console.DrawConsole;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Halq
 * @since 18/06/2023 at 19:27
 */

public class TabComplete {

    public static int index = 0;

    public static void tabComplete(String input) {
        List<String> commands = CommandExc.getCommands();
        List<String> suggestions = new ArrayList<>();

        for (String command : commands) {
            if (command.startsWith(input)) {
                suggestions.add(command);
            }
        }

        if (suggestions.size() == 1) {
            DrawConsole.inputString = suggestions.get(0);
        } else if (suggestions.size() > 1) {
            int currentIndex = suggestions.indexOf(DrawConsole.inputString);
            if (currentIndex == -1 || currentIndex == suggestions.size() - 1) {
                DrawConsole.inputString = suggestions.get(0);
            } else {
                DrawConsole.inputString = suggestions.get(currentIndex + 1);
            }
        }
    }
}
