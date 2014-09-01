package fr.skyost.playerskinchanger;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.skyost.playerskinchanger.listeners.CommandsExecutor;
import fr.skyost.playerskinchanger.listeners.EventsListener;
import fr.skyost.playerskinchanger.utils.Utils;

public class PlayerSkinChanger extends JavaPlugin {
	
	private static Cache cache;
	private static PluginConfig config;
	private static PlayerDisplayModifier playerDisplayModifier;
	
	@Override
	public final void onEnable() {
		try {
			final File dataFolder = this.getDataFolder();
			cache = new Cache(dataFolder);
			cache.load();
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
			final PluginCommand command = this.getCommand("psc");
			command.setUsage(ChatColor.RED + command.getUsage());
			command.setExecutor(new CommandsExecutor());
			for(final Player player : Bukkit.getOnlinePlayers()) {
				Utils.loadPlayerData(player.getName());
			}
		}
		catch(final Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static final Cache getCacheConfig() {
		return cache;
	}
	
	public static final PluginConfig getPluginConfig() {
		return config;
	}
	
	public static final HashMap<String, String> getRAWCache() {
		return cache.cache;
	}
	
	public static final PlayerDisplayModifier getModifier() {
		return playerDisplayModifier;
	}
	
}