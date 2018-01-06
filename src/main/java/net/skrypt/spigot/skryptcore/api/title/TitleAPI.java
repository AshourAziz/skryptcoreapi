package net.skrypt.spigot.skryptcore.api.title;

import org.bukkit.plugin.Plugin;

import java.util.HashMap;

/**
 * Created by Skrypt on 18.05.2016.
 */
public class TitleAPI {

	private static HashMap<Integer, Title> titles = new HashMap<>();

	private Plugin plugin;

	public TitleAPI(Plugin _plugin) {
		plugin = _plugin;
	}

	public static Title create() {
		return create(null);
	}

	public static Title create(String title) {
		return create(title, null);
	}

	public static Title create(String title, String subtitle) {
		return create(title, subtitle, -1f, -1f, -1f);
	}

	public static Title create(String title, String subtitle, float duration, float fadeIn, float fadeOut) {
		return new Title(titles.size(), title, subtitle, duration, fadeIn, fadeOut);
	}


	public static void delete(Title title) {
		delete(title.getId());
	}

	public static void delete(int id) {
		titles.remove(id);
	}

}