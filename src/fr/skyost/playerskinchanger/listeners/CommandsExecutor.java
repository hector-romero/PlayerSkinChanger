package fr.skyost.playerskinchanger.listeners;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.skyost.playerskinchanger.Cache;
import fr.skyost.playerskinchanger.PlayerData;
import fr.skyost.playerskinchanger.PlayerSkinChanger;

public class CommandsExecutor implements CommandExecutor {
	
	@Override
	public final boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "No console here !");
			return true;
		}
		if(args.length < 2) {
			return false;
		}
		try {
			final String player = ((Player)sender).getName();
			PlayerSkinChanger.getModifier().changeDisplay(player, args[0], args[1]);
			final Cache cache = PlayerSkinChanger.getCacheConfig();
			if(args[0].equals(player) && args[1].equals(player)) {
				if(cache.cache.containsKey(player)) {
					cache.cache.remove(player);
					cache.save();
				}
				sender.sendMessage(ChatColor.RED + "Reset with success.");
			}
			else {
				cache.cache.put(player, new PlayerData(args[0], args[1]).toString());
				cache.save();
				sender.sendMessage(ChatColor.GREEN + "Skin changed with success.");
			}
		}
		catch(final Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}

}
