package org.jungles.mazagaoauth.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jungles.mazagaoauth.manager.AuthManager;
import org.jungles.mazagaoauth.serivices.Mazagao;
import org.jungles.mazagaoauth.serivices.PlayerLocation;

import static org.bukkit.Bukkit.getServer;

public class Login implements CommandExecutor {

    AuthManager authManager = AuthManager.getInstance();
    Plugin plugin;
    public Login(Plugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if(!(commandSender instanceof Player)){
            return false;
        }

        if(args.length != 1){
            return false;
        }

        Player player = (Player)commandSender;

        if(authManager.isAuthenticated(player.getName())){
            return true;
        }

        String username = player.getName();
        String password = args[0];

        new BukkitRunnable() {
            @Override
            public void run() {
                if (Mazagao.login(username, password))  {
                    getServer().getScheduler().runTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            PlayerLocation.teleportToLastLocation(player);
                            authManager.setAuthenticated(username);
                        }
                    });

                }
                else{
                    player.sendMessage(ChatColor.RED + "Senha inválida");
                }
            }
        }.runTaskAsynchronously(plugin);

        return true;
    }
}
