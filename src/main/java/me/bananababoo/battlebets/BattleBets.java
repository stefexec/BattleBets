package me.bananababoo.battlebets;

import com.Zrips.CMI.CMI;
import me.bananababoo.battlebets.events.OnDamage;
import me.bananababoo.battlebets.events.OnDeath;
import me.bananababoo.battlebets.events.OnJoin;
import me.bananababoo.battlebets.events.OnMove;
import me.bananababoo.battlebets.SubCommands.StartStop;
import me.bananababoo.battlebets.utils.StorageUtil;
import me.bananababoo.battlebets.tabComplete.BattleTabComplete;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class BattleBets extends JavaPlugin {

    public static BattleBets getPlugin() { return plugin; }

    static BattleBets plugin;
    static CMI cmi;
    static LuckPerms lPapi;

    @Override
    public void onEnable() {
        plugin = this;

        // new stuff

        // TODO add support for item spawning in arenas ask roach for more info
        // TODO Fix spawning in wrong direction by saving of type Location instead of ints: x y z

        getLogger().info("#############################");
        getLogger().info("BattleBets 2.0.1 started!");
        getLogger().info("#############################");


        // ##################################################################
        StorageUtil.LoadFiles();

        RegisteredServiceProvider<LuckPerms> lpprovider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);

        if (lpprovider != null) {
            lPapi = lpprovider.getProvider();
        } else {
            Bukkit.getLogger().warning("LuckPerms not detecded");
        }

        StartStop.resetDeathLists();
        updateCMI();
        //register
        Objects.requireNonNull(this.getCommand("battle")).setExecutor(new Battle());
        Objects.requireNonNull(this.getCommand("battle")).setTabCompleter(new BattleTabComplete());
        Objects.requireNonNull(this.getCommand("battleitem")).setExecutor(new ItemCommand());
        Objects.requireNonNull(this.getCommand("battleitem")).setTabCompleter(new ItemCommandTabCompletion());

        getServer().getPluginManager().registerEvents(new OnJoin(), this);
        getServer().getPluginManager().registerEvents(new OnDeath(), this);
        getServer().getPluginManager().registerEvents(new OnMove(), this);
        getServer().getPluginManager().registerEvents(new OnDamage(), this);

    }

    public static void updateCMI(){
        RegisteredServiceProvider<CMI> cmiprovider = Bukkit.getServicesManager().getRegistration(CMI.class);
        if (cmiprovider != null) {
            cmi = cmiprovider.getProvider();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
