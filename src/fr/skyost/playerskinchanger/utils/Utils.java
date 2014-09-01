package fr.skyost.playerskinchanger.utils;

import java.util.regex.Pattern;

import org.bukkit.Bukkit;

import fr.skyost.playerskinchanger.PlayerData;
import fr.skyost.playerskinchanger.PlayerSkinChanger;

public class Utils {
	
	public static final Boolean loadPlayerData(final String player) {
		try {
			final String rawData = PlayerSkinChanger.getRAWCache().get(player);
			if(rawData != null) {
				final PlayerData data = PlayerData.fromJson(rawData);
				PlayerSkinChanger.getModifier().changeDisplay(player, data.skin, data.name);
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
