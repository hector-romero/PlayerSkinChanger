package fr.skyost.playerskinchanger.listeners;

import fr.skyost.playerskinchanger.PlayerSkinChanger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSkinCommand implements CommandExecutor {
    private final PlayerSkinChanger plugin;

    public SetSkinCommand(PlayerSkinChanger plugin) {
    		this.plugin = plugin; // Store the plugin in situations where you need it.
    }
    
	@Override
	public final boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if(args.length < 2) {
			return false;
		}
		try {
			final String player = args[0];
            final String skin = args[1];
            final String name;
            if( args.length > 2 && args[2] != null){
                name =  args[2];
            }else{
                name = player;
            }
			PlayerSkinChanger.getModifier().changeDisplay(player, skin, name);
			if(skin.equalsIgnoreCase(player) && name.equalsIgnoreCase(player)) {
                plugin.resetSkin(player);
                sender.sendMessage(ChatColor.RED + "Reset with success.");
			}
			else {
                plugin.setSkin(player, skin, name);
				sender.sendMessage(ChatColor.GREEN + "Skin changed with success.");
			}
		}
		catch(final Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}

}
