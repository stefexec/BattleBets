package me.bananababoo.battlebets;

import me.bananababoo.battlebets.SubCommands.Kits;
import me.bananababoo.battlebets.SubCommands.Lives;
import me.bananababoo.battlebets.SubCommands.StartStop;
import me.bananababoo.battlebets.Team.BBPlayer;
import me.bananababoo.battlebets.Utils.StorageUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Battle implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p) {
            if (args.length == 1) {
                if(args[0].equals("stop")){
                    StartStop.stop("Noone Won");
                }

            } else if (args.length  == 2) {
                if(args[0].equals("start") && StorageUtil.getArenas().contains(args[1])){
                    StartStop.start(StorageUtil.getArena(args[1], "red"), StorageUtil.getArena(args[1], "blue"));
                } else if (args[0].equals("mode")) {
                    if (args[1].equals("lives")) {            //TODO    AUTOCOMPLETE      ACTUAL CODE FOR SWITCHING BETWEEN THE TWO INTEGRATE WITH ARENA CLASS
                        StartStop.setMode("lives");
                    } else if (args[1].equals("rebirth")) {
                        StartStop.setMode("rebirth");
                    }
                }

            } else if (args.length == 3) {
                if(args[0].equals("arena")){
                    if(args[1].equals("add")){
                        ArenaCommands.addArena(p, args[2]);
                    }
                }

            } else if (args.length == 4) {
                if(args[0].equals("team") && args[1].equals("setplayer")) {
                    if(args[3].equals("both")){
                        BBPlayer.setTeam(p, args[2], "red");
                        BBPlayer.setTeam(p, args[2], "blue");
                    }else {
                        BBPlayer.setTeam(p, args[2], args[3]);
                    }
                }

            } else if(args.length == 6){
            if(args[0].equals("arena")){
                if(args[1].equals("set")){
                    if(StorageUtil.getArenas().contains(args[2])){
                        if(args[3].equalsIgnoreCase("red") || args[3].equalsIgnoreCase("blue") || args[3].equalsIgnoreCase("both")){
                            if(args[4].equals("lives") && Extra.isNumber(args[5]) ){
                                if(args[3].equals("both")){
                                    Lives.setMaxLives(Integer.parseInt(args[5]),redTeam(args[2]));
                                    Lives.setMaxLives(Integer.parseInt(args[5]),blueTeam(args[2]));
                                } else {
                                    Arena a = StorageUtil.getArena(args[2], args[3]);
                                    assert a != null;
                                    Lives.setMaxLives(Integer.parseInt(args[5]), a);
                                    p.sendMessage(args[3] + " set to " + args[5] + " lives");
                                }
                            } else if(args[4].equals("kit")){
                                if(args[3].equals("both")){
                                    Kits.setKit(args[2], "red", args[5]);
                                    Kits.setKit(args[2], "blue", args[5]);
                                }else {

                                    Kits.setKit(args[2], args[3], args[5]);
                                    p.sendMessage("should have kit to " + args[5]);
                                }
                            } else {
                                p.sendMessage(ChatColor.RED + "Not a number dumbo");
                            }

                        } else  p.sendMessage(ChatColor.RED + "Invalid Team name");
                    } else p.sendMessage(ChatColor.RED + "Invalid Arena name + " + StorageUtil.getArenas());
                }
            }
        } else if(args.length == 8){
                if(args[5].equals("~") && args[6].equals("~") && args[7].equals("~")){
                    if(StorageUtil.getArenas().contains(args[0])){
                        if(args[1].equalsIgnoreCase("red") || args[1].equalsIgnoreCase("blue")){
                            ArenaCommands.setSpawnPosition(args[2],args[3],args[4], (int) p.getLocation().getX(), (int) p.getLocation().getY(), (int) p.getLocation().getZ());
                        }
                    }else if(args[0].equalsIgnoreCase("both")){
                        ArenaCommands.setSpawnPosition(args[2],args[3],"blue", (int) p.getLocation().getX(), (int) p.getLocation().getY(), (int) p.getLocation().getZ());
                        ArenaCommands.setSpawnPosition(args[2],args[3],"red", (int) p.getLocation().getX(), (int) p.getLocation().getY(), (int) p.getLocation().getZ());
                    }
                }
                if(args[0].equals("arena")){
                    if(args[1].equals("set")){
                        if(StorageUtil.getArenas().contains(args[2])){
                            if(List.of("red","blue","both").contains(args[3].toLowerCase())){
                                if(Extra.isNumber(args[5]) && Extra.isNumber(args[6]) && Extra.isNumber(args[7]) && (args[4].equals("spawn") || args[4].equals("deathspawn"))){
                                    if(args[3].equals("both")){
                                        ArenaCommands.setSpawnPosition(args[2],"red",args[4], Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]));
                                        ArenaCommands.setSpawnPosition(args[2],"blue",args[4], Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]));
                                        Bukkit.getLogger().info("got + " + args[2] + args[3] + args[4] + Integer.parseInt(args[5]) + "" + Integer.parseInt(args[6]) + "" + Integer.parseInt(args[7]));
                                        p.sendMessage("arena: " + args[2] + " team: " + args[3] + args[4] +" set to " + Integer.parseInt(args[5]) + " " + Integer.parseInt(args[6]) + " " + Integer.parseInt(args[7]));
                                    } else {
                                        ArenaCommands.setSpawnPosition(args[2], args[3], args[4], Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]));
                                        Bukkit.getLogger().info("got + " + args[2] + args[3] + args[4] + Integer.parseInt(args[5]) + "" + Integer.parseInt(args[6]) + "" + Integer.parseInt(args[7]));
                                        p.sendMessage("arena: " + args[2] + " team: " + args[3] + args[4] + " set to " + Integer.parseInt(args[5]) + " " + Integer.parseInt(args[6]) + " " + Integer.parseInt(args[7]));
                                    }
                                }else p.sendMessage(ChatColor.RED + "invalid coords");

                            } else  p.sendMessage(ChatColor.RED + "Invalid Team name");
                        } else p.sendMessage(ChatColor.RED + "Invalid Arena name, current arenas:  " + StorageUtil.getArenas());
                    }
                }
            }
        }
        return true;
    }
    public static Arena redTeam(String arena){
        return StorageUtil.getArena(arena,"red");
    }
    public static Arena blueTeam(String arena){
        return StorageUtil.getArena(arena,"blue");
    }
}
