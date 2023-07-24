package halq.misericordia.fun.gui.console.core;

import baritone.api.BaritoneAPI;
import halq.misericordia.fun.core.modulecore.Module;
import halq.misericordia.fun.executor.Misercordia;
import halq.misericordia.fun.executor.modules.client.Console;
import halq.misericordia.fun.executor.modules.client.Script;
import halq.misericordia.fun.gui.console.ConsoleScreen;
import halq.misericordia.fun.gui.console.DrawConsole;
import halq.misericordia.fun.managers.module.ModuleManager;
import halq.misericordia.fun.utils.Minecraftable;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Halq
 * @since 18/06/2023 at 03:47
 */

public class CommandExc {

    public static void excString(String input) {
        DrawConsole.lastInputString.add(input);
        DrawConsole.lastInputStringIndex = DrawConsole.lastInputString.size();
        makeCommands(input);
    }

    public static void makeCommands(String input) {
        ConsoleAPI.log(TextFormatting.GREEN + "> " + input.trim());

        String trimmedInput = input.trim();

        if (trimmedInput.equalsIgnoreCase("help")) {
            ConsoleAPI.log(TextFormatting.GOLD + "Commands: " + "help -  clear - exit - enable<module> - disable<module> - bind - friend<playeraname> - baritone_help - misericordia_modules - misericordia_v - misericordia_devs - console_v - console_history - chat");
        } else if (trimmedInput.equalsIgnoreCase("clear")) {
            DrawConsole.logs.clear();
        } else if (trimmedInput.equalsIgnoreCase("baritone_help")) {
            BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("help");
        } else if (trimmedInput.equalsIgnoreCase("exit")) {
            Minecraftable.mc.displayGuiScreen(null);
        } else if (trimmedInput.equalsIgnoreCase("bind")) {
            String[] parts = trimmedInput.split(" ");
            if (parts.length != 3) {
                ConsoleAPI.log(TextFormatting.RED + "Invalid command format. Use: bind <bind> <module>");
            } else {
                String bind = parts[1];
                String module = parts[2];
                for (Module m : ModuleManager.INSTANCE.getModules()) {
                    if (m.getName().equalsIgnoreCase(module.toLowerCase())) {
                        int key = Keyboard.getKeyIndex(bind.toUpperCase());
                        m.setKey(key);
                        ConsoleAPI.log(TextFormatting.GREEN + "Bind " + bind + " set for module " + m.getName());
                    }
                }
            }
        } else if (trimmedInput.startsWith("enable")) {
            String[] parts = trimmedInput.split(" ");
            if (parts.length == 2) {
                String module = parts[1];
                for(Module m : ModuleManager.INSTANCE.getModules()) {
                    if(m.getName().equalsIgnoreCase(module.toLowerCase())) {
                        if(m.isEnabled()) {
                            ConsoleAPI.log(TextFormatting.RED + "Module is already enabled.");
                        } else {
                            m.setEnabled();
                            ConsoleAPI.log(TextFormatting.GREEN + "Module " + m.getName() + " enabled.");
                        }
                    }
                }
            } else {
                ConsoleAPI.log(TextFormatting.RED + "Invalid command format. Use: enable <module>");
            }
        } else if (trimmedInput.toLowerCase().startsWith("disable")) {
            String[] parts = trimmedInput.split(" ");
            if (parts.length != 2) {
                ConsoleAPI.log(TextFormatting.RED + "Invalid command format. Use: disable <module>");
            } else {
                String module = parts[1];
                for(Module m : ModuleManager.INSTANCE.getModules()) {
                    if(m.getName().equalsIgnoreCase(module.toLowerCase())) {
                        if(!m.isEnabled()) {
                            ConsoleAPI.log(TextFormatting.RED + "Module is already disabled.");
                        } else {
                            m.setDisabled();
                            ConsoleAPI.log(TextFormatting.GREEN + "Module " + m.getName() + " disabled.");
                        }
                    }
                }
            }
        } else if(trimmedInput.equalsIgnoreCase("misericordia_v")){
            ConsoleAPI.log("Misericordia version = " + Misercordia.VERSION);
        } else if(trimmedInput.equalsIgnoreCase("misericordia_devs")) {
            ConsoleAPI.log(TextFormatting.GOLD + "Devs: " + "Halq - acessmodifier");
        } else if(trimmedInput.equalsIgnoreCase("misericordia_modules")){
            int qtd = 0;
            List<String> mods = new ArrayList<>();
            for(Module m : ModuleManager.INSTANCE.getModules()) {
                mods.add(m.getName());
                qtd++;
            }
            String modules = mods.toString().replace("[", "").replace("]", "").replace(",", " - ");
            ConsoleAPI.log(TextFormatting.GOLD + "Modules (" + qtd + "):");
            ConsoleAPI.log(modules);

        } else if(trimmedInput.equalsIgnoreCase("retard")){
            ConsoleAPI.log(TextFormatting.YELLOW + "Potatosus is retarded");
        } else if(trimmedInput.equalsIgnoreCase("visivel")){
            ConsoleAPI.log("Visivel is renoited.");
        } else if(trimmedInput.equalsIgnoreCase("smx")){
            ConsoleAPI.log("smxlololololol");
        } else if(trimmedInput.equalsIgnoreCase("server_ip")){
            String mcserverip = "single player";
            ServerData serverData = Minecraftable.mc.getCurrentServerData();
            if (serverData != null) {
                mcserverip = serverData.serverIP;
            }
            ConsoleAPI.log("you actually server is " + mcserverip);

        }else if(trimmedInput.equalsIgnoreCase("console_history")) {
            ConsoleAPI.log(TextFormatting.GOLD + "Last commands:");
            for (String s : DrawConsole.lastInputString) {
                ConsoleAPI.log(s);
            }
        }else if(trimmedInput.equalsIgnoreCase("chat")){
         ConsoleAPI.log(TextFormatting.YELLOW + "To configure console chat, use : chat enable - chat disable - chat send <message> - chat prefix - chat prefix disable - chat prefix enable - chat prefix set <prefix>");
        } else if(trimmedInput.equalsIgnoreCase("chat enable")) {
            ConsoleAPI.chatlog = true;
            ConsoleAPI.log("Console chat enabled.");
        } else if(trimmedInput.equalsIgnoreCase("chat disable")) {
            ConsoleAPI.chatlog = false;
            ConsoleAPI.log("Console chat disabled.");
        } else if(trimmedInput.toLowerCase().startsWith("chat send")) {
            String[] parts = trimmedInput.split(" ");
            if (parts.length < 3) {
                ConsoleAPI.log(TextFormatting.RED + "Invalid command format. Use: chat send <message>");
            } else {
                String message = "";
                for (int i = 2; i < parts.length; i++) {
                    message += parts[i] + " ";
                }
                if (ConsoleAPI.prefix) {
                    Minecraftable.mc.player.sendChatMessage(ConsoleAPI.prefixString + message);
                } else {
                    Minecraftable.mc.player.sendChatMessage(message);
                }
                ConsoleAPI.log("Message sent.");
            }
        } else if(trimmedInput.equalsIgnoreCase("chat prefix")) {
            ConsoleAPI.log(TextFormatting.YELLOW + "You acturally prefix is " + ConsoleAPI.prefixString);
        } else if(trimmedInput.equalsIgnoreCase("chat prefix enable")) {
            ConsoleAPI.prefix = true;
            ConsoleAPI.log("Prefix enabled.");
        } else if(trimmedInput.equalsIgnoreCase("chat prefix disable")) {
            ConsoleAPI.prefix = false;
            ConsoleAPI.log("Prefix disabled.");
        } else if(trimmedInput.startsWith("chat prefix set")){
            String[] parts = trimmedInput.split(" ");
            if (parts.length < 4) {
                ConsoleAPI.log(TextFormatting.RED + "Invalid command format. Use: chat prefix set <prefix>");
            } else {
                String prefix = "";
                for (int i = 3; i < parts.length; i++) {
                    prefix += parts[i] + " ";
                }
                ConsoleAPI.prefixString = prefix;
                ConsoleAPI.log("Prefix set to " + prefix);
            }
        } else if(trimmedInput.equalsIgnoreCase("console_v")){
            ConsoleAPI.log("Console version = " + ConsoleScreen.VERSION);
        } else if(trimmedInput.startsWith("script = ")){
            String[] parts = trimmedInput.split(" ");
            if (parts.length != 5) {
                ConsoleAPI.log(TextFormatting.RED + "Invalid command format. Use: script = <<boolean : exc>>");
            } else {
                    ConsoleAPI.log("prefix and booleans is ok");
                    if(parts[3].equalsIgnoreCase(":")){
                        ConsoleAPI.log("prefix, divisor and booleans is ok");
                        if(ConsoleScript.excs().contains(parts[4])){
                            ConsoleAPI.log("prefix, booleans, divisor and excs is ok");
                            ConsoleAPI.log("script = " + parts[2] + " : " + parts[4]);
                            ConsoleScript.bov = parts[2];
                            ConsoleScript.exv = parts[4];
                            ConsoleScript.scriptable = true;
                            Script.INSTANCE.setEnabled();
                        } else {
                            ConsoleAPI.log("this exc is bad");
                        }
                    } else {
                        ConsoleAPI.log("this divisor is bad");
                    }
               // } else {
               //     ConsoleAPI.log("It booleans is not in list");
              //  }
            }
        } else {
            ConsoleAPI.log(TextFormatting.RED + "Command not found.");
        }

        DrawConsole.isTyping = true;
        DrawConsole.inputString = "";
    }

    public static List<String> getCommands() {
        List<String> commands = new ArrayList<>();
        commands.add("help");
        commands.add("clear");
        commands.add("exit");
        commands.add("enable");
        commands.add("disable");
        commands.add("bind");
        commands.add("friend<playeraname>");
        commands.add("baritone_help");
        commands.add("misericordia_modules");
        commands.add("misericordia_v");
        commands.add("misericordia_devs");
        commands.add("console_v");
        commands.add("console_history");
        commands.add("server_ip");
        commands.add("chat");
        return commands;
    }
}

