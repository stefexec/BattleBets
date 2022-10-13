package me.bananababoo.battlebets;

import org.bukkit.Location;

public class ItemBase {
    private final String mmName;
    private final int timer;
    private final int x;
    private final  int y;
    private final int z;

    public ItemBase(Location location, String mmName, int timer) {
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
        return mmName;
    }

    public int getTimer() {
        return timer;
    }

}
