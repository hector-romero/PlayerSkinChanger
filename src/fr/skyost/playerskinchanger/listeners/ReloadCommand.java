package fr.skyost.playerskinchanger.listeners;
import fr.skyost.playerskinchanger.PlayerSkinChanger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    private final PlayerSkinChanger plugin;

    public ReloadCommand(PlayerSkinChanger plugin) {
    		this.plugin = plugin; // Store the plugin in situations where you need it.
    }
    
	@Override
	public final boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args)      {
        plugin.updatePlayerSkin();
        plugin.getLogger().info("Reloaded players skins");
        sender.sendMessage("Reloaded player skins");
		return true;
    }
}
