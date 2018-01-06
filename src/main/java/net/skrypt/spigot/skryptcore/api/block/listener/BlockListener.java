package net.skrypt.spigot.skryptcore.api.block.listener;

import net.skrypt.spigot.skryptcore.api.block.CustomBlock;
import net.skrypt.spigot.skryptcore.api.block.CustomBlockAPI;
import net.skrypt.spigot.skryptcore.api.exceptions.VersionException;
import net.skrypt.spigot.skryptcore.api.itemstack.CustomItemStack;
import net.skrypt.spigot.skryptcore.api.itemstack.CustomItemStackAPI;
import net.skrypt.spigot.skryptcore.api.nbt.NBTItemStack;
import net.skrypt.spigot.skryptcore.api.nbt.exceptions.NBTException;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * @author Skrypt
 */
public class BlockListener implements Listener {
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		ItemStack itemStack = event.getItemInHand();
		
		try {
			NBTItemStack nbt = NBTItemStack.fromBukkit(itemStack);
			if (nbt.hasNBT() && nbt.getBoolean("customItemStack")) {
				Plugin plugin = Bukkit.getPluginManager().getPlugin(nbt.getString("plugin"));
				try {
					if (!CustomBlock.blocks.containsKey(Class.forName(nbt.getString("class"))))
						return;
					
					CustomBlock customBlock = CustomBlock.blocks.get(Class.forName(nbt.getString("class")));
					customBlock.onPlaceDefault(event);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (VersionException e) {
			e.printUserFriendlyMessage();
		} catch (NBTException e) {
			e.printUserFriendlyMessage();
		}
		
		/*if (!CustomBlock.blocks.containsKey(itemStack))
			return;
		
		CustomBlock customBlock = CustomBlock.blocks.get(itemStack);
		customBlock.onPlaceDefault(event);*/
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		
		if (!CustomBlock.placedBlocks.containsKey(block.getLocation()))
			return;
		
		CustomBlock customBlock = CustomBlock.placedBlocks.get(block.getLocation());
		customBlock.onBreakDefault(event);
	}
}
