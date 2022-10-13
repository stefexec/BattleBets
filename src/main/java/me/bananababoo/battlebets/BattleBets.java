package me.bananababoo.battlebets;

import com.Zrips.CMI.CMI;
import me.bananababoo.battlebets.Events.OnDamage;
import me.bananababoo.battlebets.Events.OnDeath;
import me.bananababoo.battlebets.Events.OnJoin;
import me.bananababoo.battlebets.Events.OnMove;
import me.bananababoo.battlebets.SubCommands.StartStop;
import me.bananababoo.battlebets.Utils.StorageUtil;
import me.bananababoo.battlebets.tabComplete.BattleTabComplete;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class BattleBets extends JavaPlugin {

    public static BattleBets getPlugin() { return plugin; }

    static BattleBets plugin;
    static CMI cmi;
    public static LuckPerms LPapi;

    @Override
    public void onEnable() {
        plugin = this;

        // new stuff

        // TODO add support for item spawning in arenas ask roach for more info
        // TODO Fix spawning in wrong direction by saving of type Location instead of ints: x y z

        getLogger().info("#############################");
        getLogger().info("BattleBets 1.3.2 started!");
        getLogger().info("#############################");


        // ##################################################################
        try {
            StorageUtil.LoadFiles();
        } catch (IOException e) {
            Bukkit.getLogger().warning(Arrays.toString(e.getStackTrace()));
        }
        StorageUtil.storeBattleItem(new BattleItemArena("sonic",List.of(new ItemBase(Objects.requireNonNull(Bukkit.getServer().getWorld("battlebets")).getBlockAt(-7,86,-15).getLocation().toCenterLocation(), "test",30))));

        RegisteredServiceProvider<LuckPerms> lpprovider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);

        if (lpprovider != null) {
            LPapi = lpprovider.getProvider();
        }
        else{
            Bukkit.getLogger().warning("LuckPerms not detecded");
        }

        StartStop.resetDeathLists();

        RegisteredServiceProvider<CMI> cmiprovider = Bukkit.getServicesManager().getRegistration(CMI.class);
        if (cmiprovider != null) {
            cmi = cmiprovider.getProvider();
        }
        //register
        Objects.requireNonNull(this.getCommand("battle")).setExecutor(new Battle());
        Objects.requireNonNull(this.getCommand("battle")).setTabCompleter(new BattleTabComplete());

        getServer().getPluginManager().registerEvents(new OnJoin(), this);
        getServer().getPluginManager().registerEvents(new OnDeath(), this);
        getServer().getPluginManager().registerEvents(new OnMove(), this);
        getServer().getPluginManager().registerEvents(new OnDamage(), this);



    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
