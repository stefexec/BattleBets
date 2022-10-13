package me.bananababoo.battlebets;

import me.bananababoo.battlebets.SubCommands.Kits;
import me.bananababoo.battlebets.SubCommands.Lives;
import me.bananababoo.battlebets.SubCommands.StartStop;
import me.bananababoo.battlebets.Team.BBPlayer;
import me.bananababoo.battlebets.utils.StorageUtil;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static net.kyori.adventure.text.Component.text;

public class Battle implements CommandExecutor {

    MiniMessage mm =  MiniMessage.miniMessage();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p) {
            switch (args.length){
                case 1:
                    if(args[0].equals("stop")){
                        StartStop.stop("No one Won");
                    }break;
                case 2:
                    if(args[0].equals("start") && StorageUtil.getArenas().contains(args[1])){
                        StartStop.start(StorageUtil.getArena(args[1], "red"), StorageUtil.getArena(args[1], "blue"));
                    } else if (args[0].equals("mode")) {
                        if (args[1].equals("lives")) {
                            StartStop.setMode("lives");
                            p.sendMessage(text("Mode set to lives", NamedTextColor.GREEN));
                        } else if (args[1].equals("rebirth")) {
                            StartStop.setMode("rebirth");
                            p.sendMessage(text("Mode set to rebirth", NamedTextColor.GREEN));
                        }
                        // TODO CHECK IF PLAYER IS WITHEN 1 BLOCK OF A SPAWN AND SET THAT TEAMS SPAWN TO IT
                    }break;
                case 3:
                    if(args[0].equals("arena")){
                        if(args[1].equals("add")){
                            ArenaCommands.addArena(args[2]);
                            p.sendMessage(mm.deserialize("<gradient:dark_green:blue>Arena " + args[2]  +" Created</gradient>"));
                        }
                    }break;
                case 4:
                    if(args[0].equals("team") && args[1].equals("setplayer")) {
                        if(args[3].equals("both")){
                            BBPlayer.setTeam(p, args[2], "red");
                            BBPlayer.setTeam(p, args[2], "blue");
                            p.sendMessage(text("Player set to both teams altho idk why you would want to do that lmao", NamedTextColor.GREEN));
                        }else {
                            BBPlayer.setTeam(p, args[2], args[3]);
                            p.sendMessage(text("Player set to " + args[3] + " team", NamedTextColor.GREEN));
                        }
                    }else if(args[0].equals("team") && args[1].equals("setallplayers")){
                        StartStop.addAllPlayersToTeam();
                    }
                    break;
                case 5: {
                    if(args[4].equals("spawn") || args[4].equals("deathspawn")){
                        if(StorageUtil.getArenas().contains(args[2])){
                            Arena a = StorageUtil.getArena(args[2],args[3]);
                            if(args[3].equalsIgnoreCase("red") || args[3].equalsIgnoreCase("blue")){
                                ArenaCommands.setSpawnPosition(a,args[4],p.getLocation());
                                p.sendMessage(text("Set " + args[4] + " of " + args[3] + " team in arena " + p.getLocation(), NamedTextColor.GREEN));
                            }else if(args[3].equalsIgnoreCase("both")){
                                ArenaCommands.setSpawnPosition(StorageUtil.getArena(args[2], "red"),args[4], p.getLocation());
                                ArenaCommands.setSpawnPosition(StorageUtil.getArena(args[2], "blue"),args[4], p.getLocation());
                                p.sendMessage(text("Set " + args[4] + " of " + args[3] + " team in arena " + p.getLocation(), NamedTextColor.GREEN));
                            }
                        }
                    }break;
                }
                case 6:
                    if(args[0].equals("arena")){
                        if(args[1].equals("set")){
                            if(StorageUtil.getArenas().contains(args[2])){
                                if(args[3].equalsIgnoreCase("red") || args[3].equalsIgnoreCase("blue") || args[3].equalsIgnoreCase("both")){
                                    if(args[4].equals("lives") && Extra.isNumber(args[5]) ){
                                        if(args[3].equals("both")){
                                            Lives.setMaxLives(Integer.parseInt(args[5]),redTeam(args[2]));
                                            Lives.setMaxLives(Integer.parseInt(args[5]),blueTeam(args[2]));
                                            p.sendMessage(text("Set lives of both teams to " + args[5], NamedTextColor.GREEN));
                                        } else {
                                            Arena a = StorageUtil.getArena(args[2], args[3]);
                                            assert a != null;
                                            Lives.setMaxLives(Integer.parseInt(args[5]), a);
                                            p.sendMessage(text("Set lives of " + args[3] + " team to " + args[5], NamedTextColor.GREEN));
                                        }
                                    } else if(args[4].equals("kit")){
                                        if(args[3].equals("both")){
                                            Kits.setKit(args[2], "red", args[5]);
                                            Kits.setKit(args[2], "blue", args[5]);
                                            p.sendMessage(text("Set kit of both teams to " + args[5], NamedTextColor.GREEN));
                                        }else {
                                            Kits.setKit(args[2], args[3], args[5]);
                                            p.sendMessage(text("Set kit of " + args[3] + " team to " + args[5], NamedTextColor.GREEN));
                                        }
                                    } else {
                                        p.sendMessage(ChatColor.RED + "Not a number dumbo");
                                    }
                                } else  p.sendMessage(ChatColor.RED + "Invalid Team name");
                            } else p.sendMessage(ChatColor.RED + "Invalid Arena name + " + StorageUtil.getArenas());
                        }
            }break;




//                if(args[0].equals("arena")){
//                    if(args[1].equals("set")){
//                        if(StorageUtil.getArenas().contains(args[2])){
//                            if(List.of("red","blue","both").contains(args[3].toLowerCase())){
//                                if(Extra.isNumber(args[5]) && Extra.isNumber(args[6]) && Extra.isNumber(args[7]) && (args[4].equals("spawn") || args[4].equals("deathspawn"))){
//                                    if(args[3].equals("both")){
//                                        ArenaCommands.setSpawnPosition(args[2],"red",args[4], p.getLocation());
//                                        ArenaCommands.setSpawnPosition(args[2],"blue",args[4], Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]));
//                                        Bukkit.getLogger().info("got + " + args[2] + args[3] + args[4] + Integer.parseInt(args[5]) + "" + Integer.parseInt(args[6]) + "" + Integer.parseInt(args[7]));
//                                        p.sendMessage("arena: " + args[2] + " team: " + args[3] + args[4] +" set to " + Integer.parseInt(args[5]) + " " + Integer.parseInt(args[6]) + " " + Integer.parseInt(args[7]));
//                                    } else {
//                                        ArenaCommands.setSpawnPosition(args[2], args[3], args[4], Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]));
//                                        Bukkit.getLogger().info("got + " + args[2] + args[3] + args[4] + Integer.parseInt(args[5]) + "" + Integer.parseInt(args[6]) + "" + Integer.parseInt(args[7]));
//                                        p.sendMessage("arena: " + args[2] + " team: " + args[3] + args[4] + " set to " + Integer.parseInt(args[5]) + " " + Integer.parseInt(args[6]) + " " + Integer.parseInt(args[7]));
//                                    }
//                                }else p.sendMessage(ChatColor.RED + "invalid coords");
//
//                            } else  p.sendMessage(ChatColor.RED + "Invalid Team name");
//                        } else p.sendMessage(ChatColor.RED + "Invalid Arena name, current arenas:  " + StorageUtil.getArenas());
//                    }
//                }
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
