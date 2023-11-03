package org.jungles.mazagaoauth.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;
import org.jungles.mazagaoauth.manager.AuthManager;
import org.jungles.mazagaoauth.serivices.PlayerLocation;

import static org.bukkit.Bukkit.getServer;

public class AuthPlayerListeners implements Listener {

    public AuthPlayerListeners(Plugin plugin){
        this.plugin = plugin;
    }

    Plugin plugin;
    AuthManager authManager = AuthManager.getInstance();

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent e) {

        Player player = e.getPlayer();

        player.sendMessage("");
        player.sendMessage(ChatColor.YELLOW + "Para fazer login:");
        player.sendMessage(ChatColor.YELLOW + "/login <Senha>");
        player.sendMessage("");
        player.sendMessage(ChatColor.YELLOW + "Para se registrar, vá no site oficial do mazagão");
        player.sendMessage("");

        getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                if(!authManager.isAuthenticated(e.getPlayer().getName())){
                    player.kickPlayer("Tempo de login esgotado");
                }
            }
        }, 600L);

    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();

        authManager.clear(player.getName());

        if(player.isDead()){
            PlayerLocation.setLastLocation(player ,player.getBedSpawnLocation());
        }
    }

    @EventHandler()
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        String name = e.getPlayer().getName();

        if (!authManager.isAuthenticated(name)) {
            getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    PlayerLocation.teleportToLoginLocation(e.getPlayer());
                }
            }, 1L);

        }
    }

}
