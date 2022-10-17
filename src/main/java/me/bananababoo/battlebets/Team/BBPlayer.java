package me.bananababoo.battlebets.Team;

import me.bananababoo.battlebets.BattleBets;
import me.bananababoo.battlebets.TeamM;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class BBPlayer {

    public static void setTeam(Player sender, @NotNull String person, String team){
        for(Player ppl : Bukkit.getOnlinePlayers()) {
            if (ppl.getName().equals(person)) {
                Player p = ppl;
                team = team.toLowerCase();
                PersistentDataContainer data = p.getPersistentDataContainer();
                String currentTeam = data.get(new NamespacedKey(BattleBets.getPlugin(), "team"), PersistentDataType.STRING);
                assert currentTeam != null;
                if (currentTeam.equals(team)) {
                    sender.sendMessage(ChatColor.RED + "" + p.getName() + " is already on " + team + " team");
                } else {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " parent remove " + TeamM.Team(p));

                    data.set(new NamespacedKey(BattleBets.getPlugin(), "team"), PersistentDataType.STRING, team);
                    //set them to the team
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " parent add " + team);
                    // add new team perms
                    for (Player ppll : Bukkit.getOnlinePlayers()) {
                        if (ppll.isOp() && (!ppll.equals(p))) {
                            ppll.sendMessage(Component.text(p.getName() + " joined " + team + " team"));
                        }
                    }
                    p.sendMessage("You are on " + team + " team");
                    if (!p.equals(sender)) {
                        p.sendMessage("Joined Team " + team);    //alert players that they joined team
                    }
                    p.playerListName(Component.text(p.getName() + "").color(TeamM.teamColor(p)));
                }
            }
        }
    }
}
