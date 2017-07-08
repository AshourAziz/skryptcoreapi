package net.skrypt.spigot.skryptcore.api.block.listener;

import net.skrypt.spigot.skryptcore.api.block.CustomBlock;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author Skrypt
 */
public class BlockListener implements Listener {
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		ItemStack itemStack = event.getItemInHand();
		
		if (!CustomBlock.blocks.containsKey(itemStack))
			return;
		
		CustomBlock customBlock = CustomBlock.blocks.get(itemStack);
		customBlock.onPlaceDefault(event);
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
