package fr.skyost.playerskinchanger.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.json.simple.parser.ParseException;

import fr.skyost.playerskinchanger.PlayerSkinChanger;
import fr.skyost.playerskinchanger.PluginConfig;
import fr.skyost.playerskinchanger.utils.Utils;

public class EventsListener implements Listener {
	
	@EventHandler
	private final void onPlayerJoin(final PlayerJoinEvent event) throws ParseException {
		final Player player = event.getPlayer();
        PlayerSkinChanger.getPlugin().getLogger().info("Player joined: cheking for skin " + player.getName());
		if(PlayerSkinChanger.getPlugin().getSkin(player.getName()) == null) {
            PlayerSkinChanger.getPlugin().getLogger().info("Player joined: doesn't have skin");
			return;
		}
        PlayerSkinChanger.getPlugin().getLogger().info("Player joined: have skin");
		final BukkitScheduler scheduler = Bukkit.getScheduler();
		final PluginConfig config = PlayerSkinChanger.getPluginConfig();
        final PlayerSkinChanger plugin = PlayerSkinChanger.getPlugin();
		scheduler.scheduleSyncDelayedTask(plugin, new Runnable() { // http://forums.bukkit.org/threads/class-change-the-skin-of-a-player-without-changing-the-name-yes-its-possible-no-spout.273251/page-5#post-2754114
			
			@Override
			public void run() {
				player.sendMessage(config.message1);
				if(Utils.loadPlayerData(player.getName())) {
					player.sendMessage(config.message2);
				}
				else {
					player.sendMessage(config.message3.replace("/n/", String.valueOf(config.retryingTime)));
					scheduler.scheduleSyncDelayedTask(plugin, this, config.retryingTime * 20L);
				}
			}
			
		}, config.waitingTime * 20L);
	}

}
