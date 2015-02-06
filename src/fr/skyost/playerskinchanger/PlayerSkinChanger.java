package fr.skyost.playerskinchanger;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;

import fr.skyost.playerskinchanger.listeners.ReloadCommand;
import fr.skyost.playerskinchanger.listeners.SetSkinCommand;
import fr.skyost.playerskinchanger.listeners.GetSkinCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.skyost.playerskinchanger.listeners.CommandsExecutor;
import fr.skyost.playerskinchanger.listeners.EventsListener;
import fr.skyost.playerskinchanger.utils.Utils;

import javax.security.auth.login.Configuration;

public class PlayerSkinChanger extends JavaPlugin {
	
	private static FileConfiguration cache;
	private static PluginConfig config;
	private static PlayerDisplayModifier playerDisplayModifier;
	private static PlayerSkinChanger plugin;


    private static void loadCache () {
        plugin.reloadConfig();
        cache = plugin.getConfig();

    }

    public void updatePlayerSkin() {
        loadCache();
        for(final Player player : Bukkit.getOnlinePlayers()) {
            Utils.loadPlayerData(player.getName());
        }
    }

	@Override
	public final void onEnable() {
		try {
            plugin = this;
			final File dataFolder = this.getDataFolder();
            loadCache();
            cache.options().copyDefaults(true);
            saveConfig();

			config = new PluginConfig(dataFolder);
			config.load();
			final Logger logger = this.getLogger();
			final PluginManager manager = Bukkit.getPluginManager();
			final Plugin protocolLib = manager.getPlugin("ProtocolLib");
			if(protocolLib != null && !Utils.compareVersions("3.4.0", protocolLib.getDescription().getVersion().split("\\-")[0])) {
				playerDisplayModifier = new PlayerDisplayModifier(this);
				logger.log(Level.INFO, "ProtocolLib hooked !");
			}
			else {
				logger.log(Level.SEVERE, "Cannot hook into ProtocolLib because it is not running or you have installed a version older than 3.4.0 !");
				manager.disablePlugin(this);
				return;
			}
			manager.registerEvents(new EventsListener(), this);
            getLogger().info("Loading command executor");
            this.getCommand("psc").setExecutor(new CommandsExecutor(this));
            getLogger().info("Loading command set skin");
            this.getCommand("setskin").setExecutor(new SetSkinCommand(this));
            getLogger().info("Loading command get skin");
            this.getCommand("getskin").setExecutor(new GetSkinCommand(this));
            getLogger().info("Loading command reload");
            this.getCommand("pscreload").setExecutor(new ReloadCommand(this));
            getLogger().info("Update skin");
            updatePlayerSkin();
		}
		catch(final Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public final FileConfiguration getCacheConfig() {
        loadCache();
		return cache;
	}

    public final String getSkin(String _player) {
        getCacheConfig();
        String player = _player.toLowerCase();
        if(cache.get("cache." + player) != null){
            return cache.getString("cache." + player + ".skin");
        }else{
            return null;
        }
    }
    public final String getName(String _player) {
        String player = _player.toLowerCase();
        getCacheConfig();
        if(cache.get("cache." + player) != null && cache.getString("cache." + player + ".name") != null){
            return cache.getString("cache." + player + ".name");

        }else{
            return _player;
        }
    }

    public final void setSkin(String _player,String skin, String name) {
        String player = _player.toLowerCase();
        getCacheConfig();
        cache.set("cache." + player + ".skin", skin);
        cache.set("cache." + player + ".name", name);
        saveConfig();

    }

    public final void resetSkin(String _player) {
        String player = _player.toLowerCase();
        getCacheConfig();
        cache.set("cache." + player, null);
        saveConfig();

    }

    public String getProfileCache (String profileOwner){
        //getCacheConfig();
        return cache.getString("profiles." + profileOwner.toLowerCase());
    }

    public void setProfileCache (String profileOwner, String jsonProfile){
        getCacheConfig();
        cache.set("profiles." + profileOwner.toLowerCase(),jsonProfile);
        saveConfig();
    }

    public static final PlayerSkinChanger getPlugin() {
        return plugin;
    }

	public static final PluginConfig getPluginConfig() {
		return config;
	}
	
	public static final PlayerDisplayModifier getModifier() {
		return playerDisplayModifier;
	}
	
}