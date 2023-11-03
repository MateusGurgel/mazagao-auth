package org.jungles.mazagaoauth.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.Plugin;
import org.jungles.mazagaoauth.manager.AuthManager;
import org.jungles.mazagaoauth.serivices.PlayerLocation;

import static org.bukkit.Bukkit.getServer;

public class PlayerBlockerListeners implements Listener {

    public PlayerBlockerListeners(Plugin plugin) {
        this.plugin = plugin;
    }

    Plugin plugin;
    AuthManager authManager = AuthManager.getInstance();

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        String name = player.getName();
        String message = e.getMessage().toLowerCase();
        String command = message.split(" ")[0];

        if (!authManager.isAuthenticated(name) && !command.equals("/login")) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerMove(PlayerMoveEvent e) {
        if (e.isCancelled()) return;

        Player player = e.getPlayer();
        String name = player.getName();
        if (authManager.isAuthenticated(name)) return;

        Location to = e.getTo();
        if (to != null && e.getFrom().getY() > to.getY()) return;

        player.teleport(e.getFrom());
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent e) {
        String name = e.getPlayer().getName();
        if (!authManager.isAuthenticated(name)) e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent e) {
        String name = e.getPlayer().getName();
        if (!authManager.isAuthenticated(name)) e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamageEvent(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.SUICIDE) return;
        if (e.isCancelled()) return;
        if (!(e.getEntity() instanceof Player)) return;

        Player player = ((Player) e.getEntity());
        if (!authManager.isAuthenticated(player.getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
        String name = e.getPlayer().getName();
        if (!authManager.isAuthenticated(name)) e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        String name = e.getPlayer().getName();
        if (!authManager.isAuthenticated(name)) e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        String name = e.getWhoClicked().getName();
        if (!authManager.isAuthenticated(name)) e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        if (e.isCancelled()) return;

        String name = e.getPlayer().getName();
        if (!authManager.isAuthenticated(name)) e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent e) {
        String name = e.getPlayer().getName();
        if (!authManager.isAuthenticated(name)) e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        String name = e.getPlayer().getName();
        if (!authManager.isAuthenticated(name)) e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.SUICIDE) return;
        if (e.isCancelled()) return;

        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (!authManager.isAuthenticated(player.getName())) {
                e.setCancelled(true);
                return;
            }
        }

        if (e.getDamager() instanceof Player) {
            Player player = (Player) e.getDamager();
            if (!authManager.isAuthenticated(player.getName())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerShearEntity(PlayerShearEntityEvent e) {
        String name = e.getPlayer().getName();
        if (!authManager.isAuthenticated(name)) e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerFish(PlayerFishEvent e) {
        String name = e.getPlayer().getName();
        if (!authManager.isAuthenticated(name)) e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerBedEnter(PlayerBedEnterEvent e) {
        String name = e.getPlayer().getName();
        if (!authManager.isAuthenticated(name)) e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerEditBook(PlayerEditBookEvent e) {
        String name = e.getPlayer().getName();
        if (!authManager.isAuthenticated(name)) e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSignChange(SignChangeEvent e) {
        String name = e.getPlayer().getName();
        if (!authManager.isAuthenticated(name)) e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerItemHeld(PlayerItemHeldEvent e) {
        String name = e.getPlayer().getName();
        if (!authManager.isAuthenticated(name)) e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerItemConsume(PlayerItemConsumeEvent e) {
        String name = e.getPlayer().getName();
        if (!authManager.isAuthenticated(name)) e.setCancelled(true);
    }
}
