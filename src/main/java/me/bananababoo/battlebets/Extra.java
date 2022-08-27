package me.bananababoo.battlebets;

import org.bukkit.Bukkit;

import java.util.Arrays;

public class
Extra {
    public static boolean isNumber(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public static String capitalize(String str){
        return str.substring(0,1).toUpperCase() + str.substring(1);
    }
    public static void warn(Exception e){
        Bukkit.getLogger().warning("Something broke " + e.getMessage() + "[" + e.getCause() + "] \n" + Arrays.toString(e.getStackTrace()));
    }
}
