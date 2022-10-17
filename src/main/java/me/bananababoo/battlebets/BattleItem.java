package me.bananababoo.battlebets;

<<<<<<< HEAD
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class BattleItem {
    private final double x;
    private final double y;
    private final double z;
    private final String mmName;

    private final int timer;

    public BattleItem(Location location, String mmName, int timer) {
        this.x = location.getBlockX();
        this.y= location.getBlockY();
        this.z = location.getZ();
        this.timer = timer;
        this.mmName = mmName;
    }

    public Location getLocation() {
        return new Location(Bukkit.getServer().getWorld("battlebets"), x, y, z);
    }

    public String getMmName() {
=======
import org.bukkit.Location;

public class BattleItem {
    private final String mmName;
    private final int timer;
    private final int x;
    private final  int y;
    private final int z;

    public BattleItem(Location location, String mmName, int timer) {
        this.mmName = mmName;
        this.timer = timer;
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    public Location getLocation() {
        return new Location(BattleBets.getPlugin().getServer().getWorld("battlebets"), x, y, z);
    }

    public String getItemName() {
>>>>>>> ecb2c657d445063d3cd5fbff565aab8e47e562ef
        return mmName;
    }

    public int getTimer() {
        return timer;
    }

}
