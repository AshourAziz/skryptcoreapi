package net.skrypt.spigot.skryptcore.api.itemstack;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

/**
 * @author Skrypt
 */
public class CustomItemStackAPI {
	
	// Private Static Variables
	public static HashMap<Plugin, HashMap<Class<? extends CustomItemStack>, CustomItemStack>> items = new HashMap<>();
	
	// Private Variables
	private Plugin plugin;
	
	public CustomItemStackAPI(Plugin plugin) {
		
		this.plugin = plugin;
		HashMap<Class<? extends CustomItemStack>, CustomItemStack> hashMap = new HashMap<>();
		items.put(this.plugin, hashMap);
	}
	
	public void add(CustomItemStack cis) {
		cis.setPlugin(this.plugin);
		items.get(this.plugin).put(cis.getClass(), cis);
	}
	
	public CustomItemStack get(Class<? extends CustomItemStack> cis) {
		return items.get(this.plugin).get(cis);
	}
}
