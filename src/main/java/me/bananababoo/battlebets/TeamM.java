package me.bananababoo.battlebets;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class TeamM {
    private TeamM() {
        throw new IllegalStateException("Utility class");
    }
    public static String Team(Player p) {
        PersistentDataContainer data = p.getPersistentDataContainer();
        return(data.get(new NamespacedKey(BattleBets.getPlugin(), "team"), PersistentDataType.STRING));
    }
    public static TextColor teamColor(Player p){
        if( Team(p).equals("blue"))    { return TextColor.color(0,0,255); }
        else if (Team(p).equals("red"))   { return TextColor.color(255,0,0); }
        else return NamedTextColor.GRAY;
    }
    public static ChatColor teamChatColor(Player p){
        if(Team(p).equals("blue")){ return ChatColor.BLUE;}
        else if(Team(p).equals("red")){ return ChatColor.RED;}
        else return ChatColor.GRAY;
    }
    public static int numPeopleOnTeam(String team){
        int i = 0;
        for(Player p: Bukkit.getOnlinePlayers()){
            if(Team(p).equals(team)){
                i++;
            }
        }
        return i;
    }
    public static List<Player> peopleOnTeam(String team){
        List<Player> list = new ArrayList<>();
        for(Player p: Bukkit.getOnlinePlayers())
            if(Team(p).equals(team)){
                list.add(p);
            }
        return list;
    }
    public static boolean onValidTeam(Player p){
        return List.of("blue","red").contains(Team(p));
    }

    //todo make a reset all teams function


}
