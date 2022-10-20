package me.bananababoo.battlebets.SubCommands;

import io.lumine.mythic.api.adapters.AbstractItemStack;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import me.bananababoo.battlebets.BattleBets;
import me.bananababoo.battlebets.utils.StorageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class Items {

    public static void start(){
        Objects.requireNonNull(StorageUtil.getBattleItemArena(StartStop.getArena().getName())).getItems().forEach(itemBase -> {
            BukkitRunnable runnable = new BukkitRunnable() {
                Entity entity = null;
                @Override
                public void run() {

                    Bukkit.getLogger().info("tried to spawn item" + itemBase.getItemName());
                    if(MythicBukkit.inst().getItemManager().getItem(itemBase.getItemName()).isPresent()){
                        if(entity == null || !(entity.isValid())) {
                            AbstractItemStack mythicItem = MythicBukkit.inst().getItemManager().getItem(itemBase.getItemName()).get().generateItemStack(1);
                            Location location = itemBase.getLocation().clone();
                            location.setY(location.getY() + .2);
                            entity = StartStop.getArena().getLocation().getWorld().dropItem(location, BukkitAdapter.adapt(mythicItem));
                            entity.setGlowing(true);
                        } else {
                            Bukkit.getLogger().info("tried to spawn item but it already existed");
                        }
                    }
                }
            };
            runnable.runTaskTimer(BattleBets.getPlugin(), 0, itemBase.getTimer()* 20L);
            });
        }


    }

