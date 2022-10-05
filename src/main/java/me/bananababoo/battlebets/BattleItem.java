package me.bananababoo.battlebets;

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
        return mmName;
    }

    public int getTimer() {
        return timer;
    }

}
