package org.jungles.mazagaoauth.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jungles.mazagaoauth.manager.AuthManager;

public class AuthPlayerListeners implements Listener {

    AuthManager authManager = AuthManager.getInstance();

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.getPlayer().sendMessage("");
        e.getPlayer().sendMessage(ChatColor.YELLOW + "Para fazer login:");
        e.getPlayer().sendMessage(ChatColor.YELLOW + "/login <Senha>");
        e.getPlayer().sendMessage("");
        e.getPlayer().sendMessage(ChatColor.YELLOW + "Para se registrar, vá no site oficial do mazagão");
        e.getPlayer().sendMessage("");

    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerQuit(PlayerQuitEvent e){
        authManager.clear(e.getPlayer().getName());
    }
}
