package me.bananababoo.battlebets;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Arena {
    private final String arenaName;
    private final String team;
    private int lives;
    private int x;
    private int z;
    private int y;
    private float pitch;
    private float yaw;
    private String kitName = "";
    private int deathX = 0;
    private int deathY = 150;
    private int deathZ = 0;



    public Arena(String arenaName, String team, Location l, Integer lives) {
        this.arenaName = arenaName;
        this.team = team;
        this.x = l.getBlockX();
        this.y =  l.getBlockY();
        this.z = l.getBlockZ();
        this.pitch = l.getPitch();
        this.yaw = l.getYaw();
        this.lives = lives;
        if(this.getKit() == null){
            this.kitName = "Default";
        }
    }

    public Arena(String arenaName, String team,Integer lives, String kit, Location l ) {
        this.arenaName = arenaName;
        this.team = team;
        this.x = l.getBlockX();
        this.y =  l.getBlockY();
        this.z = l.getBlockZ();
        this.pitch = l.getPitch();
        this.yaw = l.getYaw();
        this.lives = lives;
        this.kitName = kit;
    }

    public void setDeathX(int deathX){
        this.deathX = deathX;
    }
    public void setDeathY(int deathY){
        this.deathY = deathY;
    }
    public void setDeathZ(int deathZ){
        this.deathZ = deathZ;
    }

    public Location getLocation(){ return new Location(Bukkit.getWorld("battlebets"),this.x,this.y,this.z, this.yaw, this.pitch); }

    public void setLocation(Location l){
        this.x = l.getBlockX();
        this.y =  l.getBlockY();
        this.z = l.getBlockZ();
        this.pitch = l.getPitch();
        this.yaw = l.getYaw();
    }

    public String getTeam(){ return this.team; }

    public String getName(){ return this.arenaName; }

    public int getDeathX() { return this.deathX; }
    public int getDeathY() { return this.deathY; }
    public int getDeathZ() { return this.deathZ; }

    public int getX(){ return this.x; }
    public int getY(){ return this.y; }
    public int getZ(){ return this.z; }
    public float getPitch(){ return this.pitch;}
    public float getYaw(){ return this.yaw;}
    public int getLives(){return this.lives; }
    public String getKit(){ return this.kitName;}

    public void setMaxLives(int lives){
        this.lives = lives;
    }



    public String toString(){
        return "arnena:" + arenaName + ",team:" + team +",x:" + x + ",y:" + y + ",z:" + z + ",lives:" + lives + " ,kit:" + kitName + " ,yaw:" + yaw + " pitch:" + pitch ;
    }

}
