package halq.misericordia.fun.executor.commands;

import halq.misericordia.fun.core.commandcore.Command;
import halq.misericordia.fun.managers.config.ConfigManager;
import halq.misericordia.fun.utils.utils.MessageUtil;

/**
 * @author accessmodifier364
 * @since 25-Nov-2021
 */

public class Save extends Command {

    public Save() {
        super("save");
    }

    @Override
    public void execute(String[] commands) {
        ConfigManager.INSTANCE.saveConfigs();
        MessageUtil.sendMessage("Saved config.");
    }
}
