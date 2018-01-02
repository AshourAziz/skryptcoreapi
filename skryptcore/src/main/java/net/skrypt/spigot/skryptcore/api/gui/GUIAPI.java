package net.skrypt.spigot.skryptcore.api.gui;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Skrypt on 01.07.2016.
 */
public class GUIAPI {
	
	private static HashMap<Plugin, HashMap<Class<? extends GUI>, GUI>> guis = new HashMap<>();

	public static void addGUI(Plugin plugin, Class<? extends GUI> guiClass, GUI gui) {
		HashMap<Class<? extends GUI>, GUI> hashMap = new HashMap<>();
		
		if (guis.containsKey(plugin))
			hashMap = guis.get(plugin);
		
		hashMap.put(guiClass, gui);
		
		guis.put(plugin, hashMap);
	}
	
	public static HashMap<Class<? extends GUI>, GUI> getGUIsFromPlugin(Plugin plugin) {
		return guis.get(plugin);
	}
	
	public static HashMap<Plugin, HashMap<Class<? extends GUI>, GUI>> getGUIs() {
		return guis;
	}
}
