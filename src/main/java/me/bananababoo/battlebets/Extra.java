package me.bananababoo.battlebets;

import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.logging.Level;

public class Extra {
    private Extra(){
        throw new IllegalStateException("Utility class");
    }
    public static boolean isNumber(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public static String capitalize(String str){
        return str.substring(0,1).toUpperCase() + str.substring(1);
    }
    public static void warn(Exception e){
        Bukkit.getLogger().log(Level.WARNING, () -> "Something broke " + e.getMessage() + "[" + e.getCause() + "] \n" + Arrays.toString(e.getStackTrace()));
    }
}
