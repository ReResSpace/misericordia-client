package halq.misericordia.fun.executor.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import halq.misericordia.fun.core.commandcore.Command;
import halq.misericordia.fun.core.modulecore.Module;
import halq.misericordia.fun.managers.module.ModuleManager;
import halq.misericordia.fun.utils.utils.MessageUtil;

/**
 * @author accessmodifier364
 * @since 25-Nov-2021
 */

public class Toggle extends Command {

    public Toggle() {
        super("toggle", new String[]{"[module]"});
    }

    @Override
    public void execute(String[] commands) {

        if (commands.length == 1) {
            MessageUtil.sendMessage(getCommandPrefix() + "toggle " + ChatFormatting.AQUA + "[module]");
            return;
        }

        Module module = ModuleManager.INSTANCE.getModule(commands[0]);

        if (module == null) {
            MessageUtil.sendMessage("That module does not exist.");
            return;
        }
        module.toggle();
    }
}