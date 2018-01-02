package net.skrypt.spigot.skryptcore.api.gui.owners;

import net.skrypt.spigot.skryptcore.api.gui.GUI;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

/**
 * @author Skrypt
 */
public class GUIOwnerBlock {
	
	// Static Variables
	private static HashMap<Plugin, HashMap<Location, HashMap<Class<? extends GUI>, GUI>>> blocks = new HashMap<>();
	
	// Private Variables
	private Plugin plugin;
	private Block block;
	
	private GUIOwnerBlock(Plugin plugin, Block block) {
		this.plugin = plugin;
		this.block = block;
	}
	
	public void open(Class<? extends GUI> gui, Player... players) {
		for (Player player : players) {
			blocks.get(this.plugin).get(this.block.getLocation()).get(gui).open(player);
		}
	}
	
	public GUI get(Class<? extends GUI> gui) {
		if (blocks.get(this.plugin).containsKey(this.block.getLocation())) {
			if (blocks.get(this.plugin).get(this.block.getLocation()).containsKey(gui))
				return blocks.get(this.plugin).get(this.block.getLocation()).get(gui);
		}
		return null;
	}
	
	public static void add(Plugin plugin, Block block, GUI gui) {
		// Contains Plugin
		if (blocks.containsKey(plugin)) {
			
			// Contains Block
			if (blocks.get(plugin).containsKey(block.getLocation())) {
				// Only add it if it's not already in the GUI - otherwise it would overwrite the content of it.
				if (!blocks.get(plugin).get(block.getLocation()).containsKey(gui.getClass()))
					blocks.get(plugin).get(block.getLocation()).put(gui.getClass(), gui);
			// Doesn't contain Block - create block HashMap from scratch
			} else {
				HashMap<Class<? extends GUI>, GUI> hashMap = new HashMap<>();
				hashMap.put(gui.getClass(), gui);
				blocks.get(plugin).put(block.getLocation(), hashMap);
			}
		// Doesn't contain plugin - create whole HashMap from scratch
		} else {
			HashMap<Location, HashMap<Class<? extends GUI>, GUI>> hashMap = new HashMap<>();
			hashMap.put(block.getLocation(), new HashMap<>());
			hashMap.get(block.getLocation()).put(gui.getClass(), gui);
			blocks.put(plugin, hashMap);
		}
	}
	
	public static GUIOwnerBlock get(Plugin plugin, Block block) {
		return new GUIOwnerBlock(plugin, block);
	}
	
}
