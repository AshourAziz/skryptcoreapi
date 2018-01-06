package net.skrypt.spigot.skryptcore.api.block;

import net.skrypt.spigot.skryptcore.SkryptCoreAPI;
import net.skrypt.spigot.skryptcore.api.storage.flatfile.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Skrypt
 */
public class CustomBlockAPI {
	
	// Public Static Variables
	public static HashMap<Plugin, HashMap<Class<? extends CustomBlock>, CustomBlock>> blocks = new HashMap<>();
	
	// Private Variables
	private Plugin plugin;
	
	public CustomBlockAPI(Plugin plugin) {
		this.plugin = plugin;
		
		HashMap<Class<? extends CustomBlock>, CustomBlock> hashMap = new HashMap<>();
		blocks.put(this.plugin, hashMap);
		
		SkryptCoreAPI.getInstance().getFlatfileAPI().add(new Config("data.blocks", "", "blank.yml", "data", "blocks.dat"));
	}
	
	public void add(CustomBlock block) {
		blocks.get(this.plugin).put(block.getClass(), block);
	}
	
	public CustomBlock get(Class<? extends CustomBlock> block) {
		return blocks.get(this.plugin).get(block);
		
	}
	
	public void load() {
		FileConfiguration config = SkryptCoreAPI.getInstance().getFlatfileAPI().get("data.blocks");
		Set<String> locations = config.getConfigurationSection("").getKeys(false);
		
		for (String loc : locations) {
			String[] positions = loc.split("[|]");
			for (String str : positions) {
				Bukkit.broadcastMessage(str);
			}
			World world = Bukkit.getWorld(positions[0]);
			int x = Integer.parseInt(positions[1]);
			int y = Integer.parseInt(positions[2]);
			int z = Integer.parseInt(positions[3]);
			Location location = new Location(world, x, y, z);
			try {
				Class<? extends CustomBlock> customBlock = (Class<? extends CustomBlock>) Class.forName(config.getString(loc));
				CustomBlock block = blocks.get(plugin).get(customBlock);
				block.onLoadDefault(location.getBlock());
				CustomBlock.placedBlocks.put(location, block);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void save() {
		
		FileConfiguration config = SkryptCoreAPI.getInstance().getFlatfileAPI().get("data.blocks");
		for (Map.Entry<Location, CustomBlock> entry : CustomBlock.placedBlocks.entrySet()) {
			Location location = entry.getKey();
			CustomBlock block = entry.getValue();
			block.onSaveDefault();
			String path = location.getWorld().getName() + "|" + location.getBlockX() + "|" + location.getBlockY() + "|" + location.getBlockZ();
			config.set(path, block.getClass().getName());
			//config.set(path + ".plugin", block.getClass().get);
			//config.set(path + ".class", block.getClass().getName());
		}
		SkryptCoreAPI.getInstance().getFlatfileAPI().save("data.blocks");
	}
}
