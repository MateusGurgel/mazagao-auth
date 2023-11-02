package org.jungles.mazagaoauth.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jungles.mazagaoauth.manager.AuthManager;
import org.jungles.mazagaoauth.serivices.PlayerLocation;

public class LocationRestorerListeners implements Listener {

    AuthManager authManager = AuthManager.getInstance();

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent e) {
        PlayerLocation.teleportToLoginLocation(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        if(authManager.isAuthenticated(player.getName())){
            PlayerLocation.setLastLocation(player);
        }

    }

}
