package halq.misericordia.fun.gui.console.core;

import halq.misericordia.fun.utils.Minecraftable;
import halq.misericordia.fun.utils.utils.MessageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Halq
 * @since 19/06/2023 at 19:30
 */

public class ConsoleScript {

    public static boolean scriptable = false;
    public static String bov;
    public static String exv;
    public static boolean boov;

    public static void excWSubB(String booleanValue, int booleanValueSub, String executatorValue){
        boolean bv;
        bv = false;

        switch (booleanValue){
            case "health":
                bv = Minecraftable.mc.player.getHealth() <= booleanValueSub;
                break;
        }

        boov = bv;

        if(bv) {
            MessageUtil.sendClientMessage("it work");
        }
    }

    public static void excSubexcc(String booleanValue, String executatorValue, String executatorValueSub){
        boolean bv;
        int booleanSubValue;
        String ev = executatorValue;

        bv = false;
        
        switch (booleanValue) {
                case "health":
                    bv = Minecraftable.mc.player.getHealth() < 300;
                    break;
            }

            boov = bv;

            if(bv) {
                MessageUtil.sendClientMessage("it work");
        }
    }

    public static void exc(String booleanValue, String executatorValue) {
        boolean bv;
        String ev = executatorValue;

        bv = false;

        switch (booleanValue) {
                case "isInWater":
                    bv = Minecraftable.mc.player.isInWater();
                    break;

                case "isInLava":
                    bv = Minecraftable.mc.player.isInLava();
                    break;

                case "isInWeb":
                    bv = Minecraftable.mc.player.isInWeb;
                    break;
            }

            boov = bv;

            if(bv) {
                MessageUtil.sendClientMessage("it work");
        }
    }

    public static List<String> booleans(){
        List<String> booleansValues = new ArrayList<>();
        booleansValues.add("health");
        booleansValues.add("isInWater");
        booleansValues.add("isInLava");
        booleansValues.add("isInWeb");
        return booleansValues;
    }

    public static List<String> excs(){
        List<String> excsValues = new ArrayList<>();
        excsValues.add("sendMessage");
        excsValues.add("disconnect");
        return excsValues;
    }
}
