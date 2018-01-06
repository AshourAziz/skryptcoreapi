package net.skrypt.spigot.skryptcore.api.gui.owners;

import net.skrypt.spigot.skryptcore.api.gui.GUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author Skrypt
 */
public class GUIOwnerPlayer {
	
	// Static Variables
	private static HashMap<Plugin, HashMap<UUID, HashMap<Class<? extends GUI>, GUI>>> players = new HashMap<>();
	
	// Private Variables
	private Plugin plugin;
	private UUID uuid;
	
	private GUIOwnerPlayer(Plugin plugin, UUID uuid) {
		this.plugin = plugin;
		this.uuid = uuid;
	}
	
	public void open(Class<? extends GUI> gui) {
		players.get(this.plugin).get(this.uuid).get(gui).open();
	}
	
	public static void add(Plugin plugin, Player player, GUI gui) {
		// Contains Plugin
		if (players.containsKey(plugin)) {
			
			// Contains Block
			if (players.get(plugin).containsKey(player.getUniqueId())) {
				// Only add it if it's not already in the GUI - otherwise it would overwrite the content of it.
				if (!players.get(plugin).get(player.getUniqueId()).containsKey(gui.getClass()))
					players.get(plugin).get(player.getUniqueId()).put(gui.getClass(), gui);
			// Doesn't contain Block - create block HashMap from scratch
			} else {
				HashMap<Class<? extends GUI>, GUI> hashMap = new HashMap<>();
				hashMap.put(gui.getClass(), gui);
				players.get(plugin).put(player.getUniqueId(), hashMap);
			}
		// Doesn't contain plugin - create whole HashMap from scratch
		} else {
			HashMap<UUID, HashMap<Class<? extends GUI>, GUI>> hashMap = new HashMap<>();
			hashMap.put(player.getUniqueId(), new HashMap<>());
			hashMap.get(player.getUniqueId()).put(gui.getClass(), gui);
			players.put(plugin, hashMap);
		}
	}
	
	public static GUIOwnerPlayer get(Plugin plugin,Player player) {
		return new GUIOwnerPlayer(plugin, player.getUniqueId());
	}
	
}
