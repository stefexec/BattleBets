package me.bananababoo.battlebets.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.bananababoo.battlebets.BattleItem;
import me.bananababoo.battlebets.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;


public class StorageUtil {
    private StorageUtil() {
        throw new IllegalStateException("Utility class");
    }
    static List<Arena> arenaList = new ArrayList<>(); // arenaList of all loaded arenas
    static HashSet<BattleItemArena> itemList = new HashSet<>(); // arenaList of all loaded battle items
    static Arena arena;

     static final String ARENAPATH = "/Arenas.json";
     static final String ITEMSPATH = "/Items.json";

    public static void addArena(String arenaName, String team){
        arena = new Arena(arenaName, team, new Location(Bukkit.getWorld("battlebets"), 0, 100, 0), 99);

        if(arenaExistence()){ //here
            return;
        }
        arena = new Arena(arenaName, team,  new Location(Bukkit.getWorld("battlebets"), 0, 100, 0), 99);
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
                Bukkit.getLogger().log(Level.INFO,"saved arena {0}", arena);
            }
            i++;
        }
        saveFile();
    }

    public static void LoadFiles() {
        try{
            Gson gson = new Gson();
            File file = new File(BattleBets.getPlugin().getDataFolder().getAbsoluteFile() + ARENAPATH);
            try(Reader reader = Files.newBufferedReader(file.toPath())) {
                Type typeOf = new TypeToken<List<Arena>>() {
                }.getType();
                arenaList = gson.fromJson(reader, typeOf);
                Bukkit.getLogger().log(Level.INFO,"Arena File loaded :{0}", arenaList);
            }
            file = new File(BattleBets.getPlugin().getDataFolder().getAbsoluteFile() + ITEMSPATH);
            try(Reader reader = Files.newBufferedReader(file.toPath())) {
                Type typeOf = new TypeToken<HashSet<BattleItemArena>>() {
                }.getType();
                itemList = gson.fromJson(reader, typeOf);
                Bukkit.getLogger().log(Level.INFO,"Items File loaded :{0}", arenaList);
            }
        }catch(Exception e){
            Extra.warn(e);
        }
    }


    public static Arena getArena(String arena, String team) {
        Gson gson = new Gson();
        File file = new File(BattleBets.getPlugin().getDataFolder().getAbsoluteFile() + ARENAPATH);
        try {
            Reader reader = Files.newBufferedReader(file.toPath());
            Type typeOf = new TypeToken<List<Arena>>() {}.getType();  // TODO redo to allow for multilevel classes ie Location inside of Arena
            arenaList = gson.fromJson(reader, typeOf);
            Bukkit.getLogger().log(Level.INFO, "Loaded Arenas :{0}",arenaList);
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
    public static void storeBattleItem(BattleItem item, String arenaName){
        for(BattleItemArena i : itemList){
            if(i.getArena().equals(arenaName)){
                itemList.remove(i);
                i.addBattleItem(item);
                itemList.add(i);
                saveFile();
                return;
            }
        }
        itemList.add(new BattleItemArena(arenaName, List.of(item)));
        saveFile();
    }

    public static BattleItemArena getBattleItemArena(String arena) {
        Bukkit.getLogger().log(Level.INFO, "Got Items : {0}" ,itemList.stream().toList());
        for (BattleItemArena a : itemList) {
            if(a.getArena().equals(arena)) {
                return a;
            }
        }
        return null;
    }


    public static List<String> getArenas(){
        List<String> teamList = new ArrayList<>();
        if(arenaFileNotExists()){
            Bukkit.getLogger().info("no file?, report to banana");
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

    public static boolean arenaExistence(){
        if(arenaFileNotExists()){
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
        File arenas = new File(BattleBets.getPlugin().getDataFolder().getAbsoluteFile() + ARENAPATH);
        File items = new File(BattleBets.getPlugin().getDataFolder().getAbsoluteFile() + ITEMSPATH);
        arenas.getParentFile().mkdir();
        try(Writer arenaWriter = new FileWriter(arenas, false)){
            gson.toJson(arenaList, arenaWriter);
            arenaWriter.flush();
            Bukkit.getLogger().info("Arena's Saved");
        }catch (IOException e){
        }
        try(Writer itemWriter = new FileWriter(items, false)){
            gson.toJson(itemList, itemWriter);
            itemWriter.flush();
            Bukkit.getLogger().info("BattleItem's Saved");
        } catch (IOException e) {
            Extra.warn(e);
        }
    }

    public static boolean arenaFileNotExists(){
        File file = new File(BattleBets.getPlugin().getDataFolder().getAbsoluteFile() + ARENAPATH);
        return !file.exists();
    }
}
