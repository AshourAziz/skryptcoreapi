package net.skrypt.spigot.skryptcore.api.gui.owners;

import net.skrypt.spigot.skryptcore.api.gui.GUI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author Skrypt
 */
public class GUIOwnerEntity {
	
	// Static Variables
	private static HashMap<Plugin, HashMap<Integer, HashMap<Class<? extends GUI>, GUI>>> entities = new HashMap<>();
	
	// Private Variables
	private Plugin plugin;
	private Entity entity;
	
	private GUIOwnerEntity(Plugin plugin, Entity entity) {
		this.plugin = plugin;
		this.entity = entity;
	}
	
	public void open(Class<? extends GUI> gui, Player... players) {
		for (Player player : players) {
			entities.get(this.plugin).get(this.entity.getEntityId()).get(gui).open(player);
		}
	}
	
	public static void add(Plugin plugin, Entity entity, GUI gui) {
		// Contains Plugin
		if (entities.containsKey(plugin)) {
			
			// Contains Block
			if (entities.get(plugin).containsKey(entity.getEntityId())) {
				// Only add it if it's not already in the GUI - otherwise it would overwrite the content of it.
				if (!entities.get(plugin).get(entity.getEntityId()).containsKey(gui.getClass()))
					entities.get(plugin).get(entity.getEntityId()).put(gui.getClass(), gui);
			// Doesn't contain Block - create block HashMap from scratch
			} else {
				HashMap<Class<? extends GUI>, GUI> hashMap = new HashMap<>();
				hashMap.put(gui.getClass(), gui);
				entities.get(plugin).put(entity.getEntityId(), hashMap);
			}
		// Doesn't contain plugin - create whole HashMap from scratch
		} else {
			HashMap<Integer, HashMap<Class<? extends GUI>, GUI>> hashMap = new HashMap<>();
			hashMap.put(entity.getEntityId(), new HashMap<>());
			hashMap.get(entity.getEntityId()).put(gui.getClass(), gui);
			entities.put(plugin, hashMap);
		}
	}
	
	public static GUIOwnerEntity get(Plugin plugin, Entity entity) {
		return new GUIOwnerEntity(plugin, entity);
	}
	
}
