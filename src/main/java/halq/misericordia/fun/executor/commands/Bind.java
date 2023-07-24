package halq.misericordia.fun.executor.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import halq.misericordia.fun.core.commandcore.Command;
import halq.misericordia.fun.core.modulecore.Module;
import halq.misericordia.fun.managers.module.ModuleManager;
import halq.misericordia.fun.utils.utils.MessageUtil;
import org.lwjgl.input.Keyboard;

/**
 * @author accessmodifier364
 * @since 24-Nov-2021
 */

public class Bind extends Command {

    public Bind() {
        super("bind", new String[]{"[module]", "[key]"});
    }

    @Override
    public void execute(String[] commands) {

        if (commands.length == 1) {
            MessageUtil.sendMessage(getCommandPrefix() + "bind " + ChatFormatting.AQUA + "[module] [key]");
            return;
        }

        String rkey = commands[1];
        String moduleName = commands[0];
        Module module = ModuleManager.INSTANCE.getModule(moduleName);

        if (module == null) {
            MessageUtil.sendMessage("That module does not exist.");
            return;
        }

        if (rkey == null) {
            MessageUtil.sendMessage(getCommandPrefix() + "bind " + ChatFormatting.AQUA + "[module] [key]");
            return;
        }

        int key = Keyboard.getKeyIndex(rkey.toUpperCase());

        if (rkey.equalsIgnoreCase("none")) {
            key = 0;
        }

        if (key == 0) {
            module.setKey(key);
            MessageUtil.sendMessage(module.getName() + " keybind has been set to NONE.");
            return;
        }

        module.setKey(key);
        MessageUtil.sendMessage(module.getName() + " keybind has been set to " + rkey.toUpperCase() + ".");
    }

}
