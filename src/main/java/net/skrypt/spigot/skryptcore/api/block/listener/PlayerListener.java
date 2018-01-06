package net.skrypt.spigot.skryptcore.api.block.listener;

import net.skrypt.spigot.skryptcore.api.block.CustomBlock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * @author Skrypt
 */
public class PlayerListener implements Listener {
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Block block = event.getClickedBlock();
		
		if (block == null)
			return;
		
		if (block.getType() == Material.AIR)
			return;
		
		// If placedBlocks doesn't cointain this block, the block is not custom.
		if (!CustomBlock.placedBlocks.containsKey(block.getLocation()))
			return;
		
		CustomBlock customBlock = CustomBlock.placedBlocks.get(block.getLocation());
		customBlock.onInteractDefault(event);
	}
}
