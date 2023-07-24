package halq.misericordia.fun.gui.guibars;

import halq.misericordia.fun.executor.modules.client.Console;
import halq.misericordia.fun.executor.modules.client.InteligentGui;

/**
 * @author Halq
 * @since 19/06/2023 at 17:38
 */

public class ClickBar {

    public static void mouseClicked(int mouseX, int mouseY, int mouseButton){
        if(mouseX >= 290 && mouseX <= 310 && mouseY >= 0 && mouseY <= 10 && mouseButton == 0){
            Console.INSTANCE.setDisabled();
            InteligentGui.INSTANCE.setEnabled();
        }

        if(mouseX >= 310 && mouseX <= 345 && mouseY >= 0 && mouseY <= 10 && mouseButton == 0){
            InteligentGui.INSTANCE.setDisabled();
            Console.INSTANCE.setEnabled();
        }
    }
}
