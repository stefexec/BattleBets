package me.bananababoo.battlebets.tabComplete;

import com.Zrips.CMI.CMI;
import me.bananababoo.battlebets.Extra;
import me.bananababoo.battlebets.Utils.StorageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


public class BattleTabComplete implements TabCompleter {


    private static final List<String> COMMANDS = List.of(new String[]{"arena", "team", "start", "stop", "mode"});

    static List<String> completions = new ArrayList<>();

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        completions = new ArrayList<>();


        if (args.length == 1) {
            CPM(args[0], COMMANDS);

        } else if (args.length  == 2) {
            switch (args[0]) {
                case "arena" -> CPM(args[1], List.of("add", "set"));
                case "team" -> CPM(args[1], List.of("setplayer"));
                case "start" -> CPM(args[1], StorageUtil.getArenas());
                case "mode" -> CPM(args[1], List.of("rebirth", "lives"));
            }
        } else if (args.length == 3) {
            if(args[0].equals("arena")){
                if(args[1].equals("set")){
                    CPM(args[2], StorageUtil.getArenas());
                }
            } else if(args[0].equals("team")){
                if(args[1].equals("setplayer")){
                    List<String> people = new ArrayList<>();
                    for(Player p : Bukkit.getOnlinePlayers()){
                        people.add(p.getName());
                    }
                    Bukkit.getLogger().info(people.toString());
                    CPM(args[2],people);
                }
            }
        } else if (args.length == 4) {
            if(args[0].equals("team") ) {
                if (args[1].equals("setplayer")) {
                    CPM(args[3], List.of("red", "blue"));

                }
            }
            else if(args[1].equals("set")) CPM(args[3], List.of("red", "blue","both"));

        } else if (args.length == 5) {
            CPM(args[4], List.of("lives", "kit", "mode", "spawn", "deathspawn"));                  //TODO add ~ ~ ~ position support
        } else if (args.length == 6){
            if(args[4].equals("kits")){
                CPM(args[5], CMI.getInstance().getKitsManager().getKitMap().keySet().stream().toList());  // maybe this magic will work but who knows :D
            }
        } else if(args[0].equals("arena")){
                if(args[1].equals("set") && !List.of("lives", "kit","mode", "spawn", "deathspawn").contains(args[4]) && !Extra.isNumber(args[5])){
                    CPM(args[3], List.of("red", "blue","both"));
                }
            }
        return completions;
    }

    public static void CPM(String in, List<String> list){
        StringUtil.copyPartialMatches(in, list, completions);
    }
}
