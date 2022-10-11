package me.bananababoo.battlebets.Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.bananababoo.battlebets.*;
import me.bananababoo.battlebets.BattleItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


public class StorageUtil {
    static List<Arena> arenaList = new ArrayList<>(); // arenaList of all loaded arenas
    static List<BattleItem> itemList = new ArrayList<>(); // arenaList of all loaded arenas
    static Arena arena;


    public static void addArena(String arenaName, String team){
        arena = new Arena(arenaName, team, new Location(Bukkit.getWorld("battlebets"), 0, 100, 0), 99);

        if(arenaExistsance()){ //here
            return;
        }

        arena = new Arena(arenaName, team,  new Location(Bukkit.getWorld("battlebets"), 0, 100, 0), 99);
        arenaList.add(arena);
        saveFile();
    }
    public static void addArena(String arenaName, String team, int x, int y, int z){
        arena = new Arena(arenaName, team, new Location(Bukkit.getWorld("battlebets"), x, y, z), 99);
        arenaList.add(arena);
        saveFile();
    }
    public static void setArena(Arena arena){
        try {
            LoadFiles();
        }catch(Exception e){
            Bukkit.getLogger().warning(e.getMessage());
        }
        int i = 0;
        for(Arena a : arenaList){
            if(a.getName().equals(arena.getName()) && a.getTeam().equals(arena.getTeam())){

                arenaList.set(i, arena);
                Bukkit.getLogger().info("saved arena "+ arena);
            }
            i++;
        }
        saveFile();
    }

    public static void LoadFiles() throws IOException {
        Gson gson = new Gson();
        File file = new File(BattleBets.getPlugin().getDataFolder().getAbsoluteFile() + "/Arenas.json");
        Reader reader = Files.newBufferedReader(file.toPath());
        Type typeOf = new TypeToken<List<Arena>>() {}.getType();
        arenaList = gson.fromJson(reader, typeOf);
        Bukkit.getLogger().info(arenaList.toString());

        file = new File(BattleBets.getPlugin().getDataFolder().getAbsoluteFile() + "/Items.json");
        reader = Files.newBufferedReader(file.toPath());
        typeOf = new TypeToken<List<BattleItem>>() {}.getType();
        itemList = gson.fromJson(reader, typeOf);
        Bukkit.getLogger().info(itemList.toString());
    }


    public static Arena getArena(String arena, String team) {
        Gson gson = new Gson();
        File file = new File(BattleBets.getPlugin().getDataFolder().getAbsoluteFile() + "/Arenas.json");
        try {
            Reader reader = Files.newBufferedReader(file.toPath());
            Type typeOf = new TypeToken<List<Arena>>() {}.getType();  // TODO redo to allow for multilevel classes ie Location inside of Arena
            arenaList = gson.fromJson(reader, typeOf);
            Bukkit.getLogger().info(arenaList.toString());
            for (Arena a : arenaList) {
                if (a.getName().equals(arena) && a.getTeam().equalsIgnoreCase(team)) {
                    return a;
                }
            }
        } catch (IOException e) {
            Extra.warn(e);
        }
        return null;
    }
    public static void storeBattleItem(BattleItem item){
        itemList.add(item);
        saveFile();
    }

    public static BattleItem getBattleItem(String arena) {
        Gson gson = new Gson();
        File file = new File(BattleBets.getPlugin().getDataFolder().getAbsoluteFile() + "/Items.json");
        try {
            Reader reader = Files.newBufferedReader(file.toPath());
            Type typeOf = new TypeToken<List<Arena>>() {}.getType();
            arenaList = gson.fromJson(reader, typeOf);
            Bukkit.getLogger().info(arenaList.toString());
            Bukkit.getLogger().info(itemList.toString());
            for (BattleItem a : itemList) {
                if(a.getArena().equals(arena)) {
                    return a;
                }
            }
        } catch (IOException e) {
            Extra.warn(e);
        }
        return null;
    }


    public static List<String> getArenas(){
        List<String> teamList = new ArrayList<>();
        if(!arenaFileExists()){
            Bukkit.getLogger().info("no file?, report to banaaanana");
            return teamList;
        }
        try {
            LoadFiles();
        }catch (Exception e){
            Extra.warn(e);
        }
        Bukkit.getLogger().info(arenaList.toString());
        for(Arena a : arenaList){
            teamList.add(a.getName());
        }
        return teamList;
    }

    public static boolean arenaExistsance(){
        if(!arenaFileExists()){
            return false;
        }
        for(Arena a : arenaList){
            if(a.getName().equals(arena.getName()) && a.getTeam().equals(arena.getTeam())){
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void saveFile(){
        Bukkit.getLogger().info("0");
        Gson gson = new Gson();
        File arenas = new File(BattleBets.getPlugin().getDataFolder().getAbsoluteFile() + "/Arenas.json");
        File items = new File(BattleBets.getPlugin().getDataFolder().getAbsoluteFile() + "/Items.json");
        arenas.getParentFile().mkdir();
        Writer arenaWriter;
        Writer itemWriter;
        try {
            arenaWriter = new FileWriter(arenas, false);
            itemWriter = new FileWriter(items, false);
            Bukkit.getLogger().info("2");
            gson.toJson(arenaList, arenaWriter);
            Bukkit.getLogger().info("3");
            arenaWriter.flush();
            arenaWriter.close();
            Bukkit.getLogger().info("Arena's Saved");
            gson.toJson(itemList, itemWriter);
            itemWriter.flush();
            itemWriter.close();
            Bukkit.getLogger().info("BattleItem's Saved");
        } catch (IOException e) {
            Bukkit.getLogger().warning("Arena's not saved for some reason go ask banana idk man sorry");
        }
    }
//    public static void outputFile(){
//        Gson gson = new Gson();
//        Bukkit.getLogger().info(arenaList.toString());
//        String json = gson.toJson(arenaList);
//        Bukkit.getLogger().info(json);
//    }
    public static boolean arenaFileExists(){
        File file = new File(BattleBets.getPlugin().getDataFolder().getAbsoluteFile() + "/Arenas.json");
        return file.exists();
    }
//    public static Arena getArenaFromPlayer(Player p){
//        return(StartStop.getArena(TeamM.Team(p)));
//
//    }
}
