package halq.misericordia.fun.executor.commands;

import halq.misericordia.fun.core.commandcore.Command;
import halq.misericordia.fun.managers.command.CommandManager;
import halq.misericordia.fun.utils.utils.MessageUtil;

/**
 * @author accessmodifier364
 * @since 25-Nov-2021
 */

public class Help extends Command {

    public Help() {
        super("help");
    }

    @Override
    public void execute(String[] commands) {

        MessageUtil.sendMessage("Available commands:");

        for (Command command : CommandManager.INSTANCE.getCommands()) {
            MessageUtil.sendMessage(getCommandPrefix() + command.getName());
        }

    }
}
