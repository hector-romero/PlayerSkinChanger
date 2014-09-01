package fr.skyost.playerskinchanger;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

import fr.skyost.playerskinchanger.utils.Skyoconfig;

public class Cache extends Skyoconfig {
	
	public HashMap<String, String> cache = new HashMap<String, String>();
	
	public Cache(final File dataFolder) {
		super(new File(dataFolder, "cache.yml"), Arrays.asList("The plugin's cache."));
	}
	
}
