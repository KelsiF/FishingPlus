package org.kelsi;

import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;

import java.util.Objects;

public final class fishingplus extends JavaPlugin implements Listener{

    EntityType fish; // Переменная с рыбами
    int fish_random; // Цифра означающая рыбу

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
        getConfig().options().copyDefaults(true);
        saveConfig();
        reloadConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void func1() {
        fish_random = (int) (Math.random() * 10);
        if (fish_random < 5) {
            fish = EntityType.TROPICAL_FISH;
        }
        if (fish_random > 5) {
            fish = EntityType.COD;
        }
        System.out.println(fish);
    }


    @EventHandler
    public void onPlayerFishEvent(PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            func1();
            Player player = event.getPlayer();
            player.getWorld().spawnEntity(player.getLocation(), EntityType.valueOf(String.valueOf(fish)));
            event.setCancelled(true);
        }
    }
}
