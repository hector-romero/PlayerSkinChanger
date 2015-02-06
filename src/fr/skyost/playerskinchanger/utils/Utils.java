package fr.skyost.playerskinchanger.utils;

import java.util.regex.Pattern;

import org.bukkit.Bukkit;

import fr.skyost.playerskinchanger.PlayerSkinChanger;

public class Utils {
	
	public static final Boolean loadPlayerData(final String player) {
		try {
            String skin = PlayerSkinChanger.getPlugin().getSkin(player);
            String name = PlayerSkinChanger.getPlugin().getName(player);
            PlayerSkinChanger.getPlugin().getLogger().info("Cheking skin for " + player + " : " + skin + "; " + name);
			if(skin != null) {
                PlayerSkinChanger.getPlugin().getLogger().info("Going to change skin for " + player + " : " + skin + "; " + name);
				PlayerSkinChanger.getModifier().changeDisplay(player, skin, name);
				return true;
			}
			return null;
		}
		catch(final Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public static final String getMinecraftServerVersion() {
		final String version = Bukkit.getVersion().split("\\(MC\\: ")[1];
		return version.substring(0, version.length() - 1);
	}
	
	public static final boolean compareVersions(final String versionTo, final String versionWith) {
		return normalisedVersion(versionTo, ".", 4).compareTo(normalisedVersion(versionWith, ".", 4)) > 0;
	}

	private static final String normalisedVersion(final String version, final String separator, final int maxWidth) {
		final StringBuilder stringBuilder = new StringBuilder();
		for(final String normalised : Pattern.compile(separator, Pattern.LITERAL).split(version)) {
			stringBuilder.append(String.format("%" + maxWidth + 's', normalised));
		}
		return stringBuilder.toString();
	}

}
