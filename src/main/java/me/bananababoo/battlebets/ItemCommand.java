package me.bananababoo.battlebets;

import me.bananababoo.battlebets.utils.StorageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player) sender;
        if(args[0].equals("list") && StorageUtil.getArenas().contains(args[1])){
            p.sendMessage("List of items:" + Objects.requireNonNull(StorageUtil.getBattleItemArena(args[1])));
        } else if (args[0].equals("add") && args.length == 4 && StorageUtil.getArenas().contains(args[1]) && Extra.isNumber(args[3])){
            StorageUtil.storeBattleItem(new BattleItem(p.getLocation().toCenterLocation(), args[2], Integer.parseInt(args[3])),args[1]);
            p.sendMessage("Added item " + args[2] + " to arena " + args[1]);
        } else if (args[0].equals("remove") && args.length == 3 && StorageUtil.getArenas().contains(args[1])){
            StorageUtil.removeBattleItem(args[1], args[2]);
            p.sendMessage("Removed item " + args[2] + " from arena " + args[1]);
        }
        return true;
    }
}
class ItemCommandTabCompletion implements TabCompleter {
    protected static List<String> completions = new ArrayList<>();
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (args.length) {
            case 1 -> arg1(args);
            case 2 -> arg2(args);
            case 3 -> arg3(args);
            default ->blank(args);
        }
        return completions;
    }
    void arg1(String[] args){
        if(List.of("add", "remove", "list").contains(args[0])){
            cPm(args[0], StorageUtil.getArenas());
        }
    }
    void arg2(String[] args){
        if(args[0].equals("add") || args[0].equals("remove")){
            cPm(args[1], StorageUtil.getArenas());
        }
    }
    void arg3(String[] args){
        if(args[0].equals("add")){
            cPm(args[2], StorageUtil.getBattleItemArena(args[1]).getItems().stream().map(BattleItem::getItemName).toList());
        }
    }
    void blank(String[] args){
        cPm(args[0], List.of(""));
    }
    public static void cPm(String in, List<String> list) {
        StringUtil.copyPartialMatches(in, list, completions);
    }
    // /battleitem <list|add|remove> <arena> <item> <amount>
}