package org.kelsi;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;


public final class fishingplus extends JavaPlugin implements Listener{

    EntityType fish; // Переменная с рыбами
    int fish_random; // Цифра означающая рыбу
    boolean fish_succes; // Будет ли поймана рыба

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

    public void fish_randomfunc() {
        fish_random = (int) (Math.random() * 32);
        if (fish_random < 5) {
            fish = EntityType.TROPICAL_FISH;
        }
        if (fish_random > 5 && fish_random < 10) {
            fish = EntityType.COD;
        }
        if (fish_random > 10 && fish_random < 15) {
            fish = EntityType.SALMON;
        }
        if (fish_random > 17 && fish_random < 20) {
            fish = EntityType.PUFFERFISH;
        }
        if (fish_random >= 20 && fish_random <= 25) {
            fish_succes = false;
        }
        if (fish_random > 25 && fish_random <= 32) {
            fish_succes = true;
        }
        System.out.println(fish);
    }
    public void fish_succesfunc() {
        fish_random = (int) (Math.random() * 13);
        if (fish_random >= 1 && fish_random <= 5) {
            fish_succes = false;
        }
        if (fish_random >= 6 && fish_random <= 13) {
            fish_succes = true;
        }
    }

    @EventHandler
    public void onPlayerFishEvent(PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            fish_succesfunc();
            if (fish_succes) {
                fish_randomfunc();
                Player player = event.getPlayer();
                player.getWorld().spawnEntity(player.getLocation(), EntityType.valueOf(String.valueOf(fish)));
                event.setCancelled(true);
                Player p = event.getPlayer();
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(ChatColor.GREEN + getConfig().getString("messages.fish_succes")));
            }
            if (!fish_succes) {
                event.setCancelled(true);
                Player p = event.getPlayer();
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(ChatColor.RED + getConfig().getString("messages.fish_fail")));
            }
        }
    }
}