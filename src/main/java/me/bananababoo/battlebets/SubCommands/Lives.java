package me.bananababoo.battlebets.SubCommands;

import me.bananababoo.battlebets.Arena;
import me.bananababoo.battlebets.Scoreboard;
import me.bananababoo.battlebets.TeamM;
import me.bananababoo.battlebets.utils.StorageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Lives {
    public static int redLives = 0;
    public static int blueLives;

    public static void Init(Arena red, Arena blue){
        initLives(red);
        initLives(blue);
        redLives = 0;
        blueLives = 0;
    }
    public static void Init(){
        for(Player p : Bukkit.getOnlinePlayers()){
            if(TeamM.Team(p).equals("red")){
                redLives = 0;
            }else if(TeamM.Team(p).equals("blue")) {
                blueLives = 0;
            }
        }
    }
//    public static void unInit(){
//        livesList.clear();
//    }

    public static void setMaxLives(int Lives, Arena a ){
        Arena arena = new Arena(a.getName(), a.getTeam(), a.getLocation(), Lives);
        StorageUtil.setArena(arena);
        if(a.getTeam().equals("red")){
            redLives = Lives;
        }else if(a.getTeam().equals("blue")){
            blueLives = Lives;
        }
    }
    public static void initLives(Arena a){
        if(a.getTeam().equals("red")){
            redLives = a.getLives();
        }else if(a.getTeam().equals("blue")){
            blueLives = a.getLives();
        }

    }

    public static void setLivesFromArena(String arenaName){
        Arena red = StorageUtil.getArena(arenaName, "red");
        Arena blue = StorageUtil.getArena(arenaName, "blue");
        redLives = red.getLives();
        blueLives = blue.getLives();
//        Scoreboard.updateScoreBoard();
    }

    public static void removeLives(String team){
        if(team.equals("red")){
            redLives -= 1;
        }else if(team.equals("blue")){
            blueLives -= 1;
        }
        Scoreboard.updateScoreBoard();

    }
    public static int getLivesFromTeam(String team){
        if(team.equals("red")){
            return redLives;
        }else if(team.equals("blue")){
            return blueLives;
        }
        return 0;
    }

    public static void resetLives(Arena a){
        Arena redArena = new Arena(a.getName(), "red", a.getLocation(), a.getLives());
        Arena blueArena = new Arena(a.getName(), "blue",a.getLocation(), a.getLives());
        setRedMaxLives(redArena.getLives());
        setBlueMaxLives(blueArena.getLives());
        Init();
    }

    public static int getRedMaxLives() {
        return redLives;
    }

    public static void setRedMaxLives(int redLives) {
        Lives.redLives = redLives;
    }

    public static int getBlueMaxLives() {
        return blueLives;
    }

    public static void setBlueMaxLives(int blueLives) {
        Lives.blueLives = blueLives;
    }
}
