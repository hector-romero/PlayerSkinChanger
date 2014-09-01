package fr.skyost.playerskinchanger;

import java.io.File;
import java.util.Arrays;

import org.bukkit.ChatColor;

import fr.skyost.playerskinchanger.utils.Skyoconfig;

public class PluginConfig extends Skyoconfig {
	
	@ConfigOptions(name = "waiting-time")
	public int waitingTime = 3;
	@ConfigOptions(name = "retrying-time")
	public int retryingTime = 60;
	
	@ConfigOptions(name = "messages.1")
	public String message1 = ChatColor.GOLD + "Loading your skin...";
	@ConfigOptions(name = "messages.2")
	public String message2 = ChatColor.GREEN + "Skin loaded !";
	@ConfigOptions(name = "messages.3")
	public String message3 = ChatColor.RED + "Failed to load your skin, re-trying in /n/ seconds.";

	protected PluginConfig(final File dataFolder) {
		super(new File(dataFolder, "config.yml"), Arrays.asList("The plugin's config"));
	}

}
