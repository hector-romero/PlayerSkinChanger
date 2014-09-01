package fr.skyost.playerskinchanger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PlayerData {
	
	public String skin;
	public String name;
	
	public PlayerData(final String skin, final String name) {
		this.skin = skin;
		this.name = name;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public final String toString() {
		final JSONObject object = new JSONObject();
		object.put("skin", skin);
		object.put("name", name);
		return object.toJSONString();
	}
	
	public static final PlayerData fromJson(final String json) throws ParseException {
		final JSONObject object = (JSONObject)new JSONParser().parse(json);
		return new PlayerData(object.get("skin").toString(), object.get("name").toString());
	}
	

}
