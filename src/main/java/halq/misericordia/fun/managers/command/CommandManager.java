package halq.misericordia.fun.managers.command;

import halq.misericordia.fun.core.commandcore.Command;
import halq.misericordia.fun.executor.commands.*;
import halq.misericordia.fun.utils.utils.MessageUtil;
import halq.misericordia.fun.utils.Minecraftable;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author accessmodifier364
 * @since 24-Nov-2021
 */

public class CommandManager implements Minecraftable {

    public static CommandManager INSTANCE;
    private final List<Command> commands = new ArrayList<>();
    private String clientMessage = "Misericordia";
    private String prefix = "~";

    public CommandManager() {
        MinecraftForge.EVENT_BUS.register(this);
        commands.add(new Bind());
        commands.add(new Toggle());
        commands.add(new Save());
        commands.add(new Help());
    }

    public static String[] removeElement(String[] input, int indexToDelete) {
        LinkedList<String> result = new LinkedList<>();
        for (int i = 0; i < input.length; ++i) {
            if (i == indexToDelete) continue;
            result.add(input[i]);
        }
        return result.toArray(input);
    }

    private static String strip(String str, String key) {
        if (str.startsWith(key) && str.endsWith(key)) {
            return str.substring(key.length(), str.length() - key.length());
        }
        return str;
    }

    /**
     * This is important asf, this detect every message sended by the player.
     *
     * @param event
     */
    @SubscribeEvent
    public void onChat(ClientChatEvent event) {
        if (event.getMessage().startsWith(prefix)) {
            try {
                mc.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
                if (event.getMessage().length() > 1) {
                    CommandManager.INSTANCE.executeCommand(event.getMessage().substring(prefix.length() - 1));
                } else {
                    MessageUtil.sendMessage("Invalid command.");
                }
            } catch (Exception ignored) {
            }
            event.setCanceled(true);
        }
    }

    public void executeCommand(String command) {
        String[] parts = command.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        String name = parts[0].substring(1);
        String[] args = CommandManager.removeElement(parts, 0);
        for (int i = 0; i < args.length; ++i) {
            if (args[i] == null) continue;
            args[i] = CommandManager.strip(args[i], "\"");
        }
        for (Command c : commands) {
            if (!c.getName().equalsIgnoreCase(name)) continue;
            c.execute(parts);
            return;
        }
        MessageUtil.sendMessage("Unknown command. try " + prefix + "help for a list of commands.");
    }

    public Command getCommandByName(String name) {
        for (Command command : commands) {
            if (!command.getName().equals(name)) continue;
            return command;
        }
        return null;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public String getClientMessage() {
        return clientMessage;
    }

    public void setClientMessage(String clientMessage) {
        this.clientMessage = clientMessage;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

}