package me.bananababoo.battlebets.Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.bananababoo.battlebets.Arena;
import me.bananababoo.battlebets.BattleBets;
import me.bananababoo.battlebets.Extra;
import me.bananababoo.battlebets.SubCommands.StartStop;
import me.bananababoo.battlebets.TeamM;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


public class StorageUtil {
    static List<Arena> list = new ArrayList<>(); // list of all loaded arenas
    static Arena arena;


    public static void addArena(String arenaName, String team){
        arena = new Arena(arenaName, team, new Location(Bukkit.getWorld("battlebets"), 0, 100, 0), 99);
        for(Arena a : list){
            if(arenaExistsance()){
                return;
            }
        }
        arena = new Arena(arenaName, team,  new Location(Bukkit.getWorld("battlebets"), 0, 100, 0), 99);
        list.add(arena);
        saveFile();
    }
    public static void addArena(String arenaName, String team, int x, int y, int z){
        arena = new Arena(arenaName, team, new Location(Bukkit.getWorld("battlebets"), x, y, z), 99);
        list.add(arena);
        saveFile();
    }
    public static void setArena(Arena arena){
        try {
            getArena();
        }catch(Exception e){
            Bukkit.getLogger().warning(e.getMessage());
        }
        int i = 0;
        for(Arena a : list){
            if(a.getName().equals(arena.getName()) && a.getTeam().equals(arena.getTeam())){

                list.set(i, arena);
                Bukkit.getLogger().info("saved arena "+ arena);
            }
            i++;
        }
        saveFile();
    }

    public static void getArena() throws IOException {
        Gson gson = new Gson();
        File file = new File(BattleBets.getPlugin().getDataFolder().getAbsoluteFile() + "/Arenas.json");
        Reader reader = Files.newBufferedReader(file.toPath());
        Type typeOf = new TypeToken<List<Arena>>() {}.getType();
        list = gson.fromJson(reader, typeOf);
        Bukkit.getLogger().info(list.toString());
    }


    public static Arena getArena(String arena, String team) {
        Gson gson = new Gson();
        File file = new File(BattleBets.getPlugin().getDataFolder().getAbsoluteFile() + "/Arenas.json");
        try {
            Reader reader = Files.newBufferedReader(file.toPath());
            Type typeOf = new TypeToken<List<Arena>>() {}.getType();  // TODO redo to allow for multilevel classes ei Location inside of Arena
            list = gson.fromJson(reader, typeOf);
            Bukkit.getLogger().info(list.toString());
            for (Arena a : list) {
                if (a.getName().equals(arena) && a.getTeam().equalsIgnoreCase(team)) {
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
            getArena();
        }catch (Exception e){
            Extra.warn(e);
        }
        Bukkit.getLogger().info(list.toString());
        for(Arena a : list){
            teamList.add(a.getName());
        }
        return teamList;
    }

    public static boolean arenaExistsance(){
        if(!arenaFileExists()){
            return false;
        }
        for(Arena a : list){
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
        File file = new File(BattleBets.getPlugin().getDataFolder().getAbsoluteFile() + "/Arenas.json");
        file.getParentFile().mkdir();
        Writer writer;
        try {
            writer = new FileWriter(file, false);
            Bukkit.getLogger().info("2");
            gson.toJson(list, writer);
            Bukkit.getLogger().info("3");
            writer.flush();
            writer.close();
            Bukkit.getLogger().info("Arena's Saved");
        } catch (IOException e) {
            Bukkit.getLogger().warning("Arena's not saved for some reason go ask banana idk man sorry");
        }
    }
    public static void outputFile(){
        Gson gson = new Gson();
        Bukkit.getLogger().info(list.toString());
        String json = gson.toJson(list);
        Bukkit.getLogger().info(json);
    }
    public static boolean arenaFileExists(){
        File file = new File(BattleBets.getPlugin().getDataFolder().getAbsoluteFile() + "/Arenas.json");
        return file.exists();
    }
    public static Arena getArenaFromPlayer(Player p){
        return(StartStop.getArena(TeamM.Team(p)));

    }
}
