package org.jungles.mazagaoauth;

import org.bukkit.plugin.java.JavaPlugin;
import org.jungles.mazagaoauth.commands.Login;
import org.jungles.mazagaoauth.listeners.AuthPlayerListeners;
import org.jungles.mazagaoauth.listeners.LocationRestorerListeners;
import org.jungles.mazagaoauth.listeners.PlayerBlockerListeners;

public final class MazagaoAuth extends JavaPlugin {

    @Override
    public void onEnable() {

        ConfigManager.getInstance().setup(this);

        getServer().getPluginManager().registerEvents( new PlayerBlockerListeners(this), this);
        getServer().getPluginManager().registerEvents( new AuthPlayerListeners(this), this);
        getServer().getPluginManager().registerEvents( new LocationRestorerListeners(), this);

        getCommand ("login").setExecutor(new Login(this));
        System.out.println("### MazagãoAuth initialized ###");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
