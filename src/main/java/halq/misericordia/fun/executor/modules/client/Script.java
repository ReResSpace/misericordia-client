package halq.misericordia.fun.executor.modules.client;

import halq.misericordia.fun.core.modulecore.Category;
import halq.misericordia.fun.core.modulecore.Module;
import halq.misericordia.fun.gui.console.core.ConsoleAPI;
import halq.misericordia.fun.gui.console.core.ConsoleScript;

/**
 * @author Halq
 * @since 19/06/2023 at 20:17
 */

public class Script extends Module {

    public static Script INSTANCE;

    public Script() {
        super("Script", Category.OTHER);
        INSTANCE = this;
    }

    @Override
    public void onUpdate() {
        if(ConsoleScript.scriptable){
            ConsoleScript.excSubexcc(ConsoleScript.bov,  ConsoleScript.exv, "lol");
        }

    }
}
