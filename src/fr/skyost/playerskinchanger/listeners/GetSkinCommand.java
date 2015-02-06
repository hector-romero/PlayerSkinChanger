package fr.skyost.playerskinchanger.listeners;

import fr.skyost.playerskinchanger.PlayerSkinChanger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GetSkinCommand implements CommandExecutor {
    private final PlayerSkinChanger plugin;

    public GetSkinCommand(PlayerSkinChanger plugin) {
    		this.plugin = plugin; // Store the plugin in situations where you need it.
    }
    
	@Override
	public final boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if(args.length < 1) {
			return false;
		}
		try {
			final String player = args[0];

            String skin = plugin.getSkin(player);
            String name = plugin.getName(player);
            if(skin != null) {
				sender.sendMessage(ChatColor.GREEN + "Player has skin: " + skin + " (" + name + ")");
			}else{
                sender.sendMessage(ChatColor.RED + "Player doesn't have any skin.");
            }


		}
		catch(final Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}

}
