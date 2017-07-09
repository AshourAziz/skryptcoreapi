package net.skrypt.spigot.skryptcore.api.block;

import net.skrypt.spigot.skryptcore.SkryptCoreAPI;
import net.skrypt.spigot.skryptcore.api.block.task.SetBlockToNull;
import net.skrypt.spigot.skryptcore.api.itemstack.CustomItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Skrypt
 */
public abstract class CustomBlock implements Serializable {
	
	// Private Static Variables
	public static HashMap<Class<? extends CustomItemStack>, CustomBlock> blocks = new HashMap<>();
	public static HashMap<Location, CustomBlock> placedBlocks = new HashMap<>();
	
	// Private Variables
	private CustomItemStack customItemStack;
	private Block block;
	
	public CustomBlock(CustomItemStack customItemStack) {
		this.customItemStack = customItemStack;
		
		blocks.put(customItemStack.getClass(), this);
	}
	
	/**
	 * Called when the block is being loaded.
	 * Contains default procedures for every block.
	 */
	public void onLoadDefault(Block block) {
		this.block = block;
		onLoad();
	}
	
	/**
	 * Called when the block is being saved.
	 * Contains default procedures for every block.
	 */
	public void onSaveDefault() {
		onSave();
	}
	
	/**
	 * Called when the block is placed.
	 * Contains default procedures for every block.
	 */
	public void onPlaceDefault(BlockPlaceEvent event) {
		this.block = event.getBlockPlaced();
		placedBlocks.put(this.block.getLocation(), this);
		
		onPlace(event);
	}
	
	/**
	 * Called when the block is broken.
	 * Contains default procedures for every block.
	 */
	public void onBreakDefault(BlockBreakEvent event) {
		placedBlocks.remove(event.getBlock().getLocation());
		
		SetBlockToNull task = new SetBlockToNull(this.block);
		Bukkit.getScheduler().scheduleSyncDelayedTask(SkryptCoreAPI.getInstance(), task);
		
		onBreak(event);
	}
	
	/**
	 * Called when the block is being interacted with by the player.
	 * Contains default procedures for every block.
	 * @param event
	 */
	public void onInteractDefault(PlayerInteractEvent event) {
		onInteract(event);
	}
	
	/**
	 * Called when the block is being loaded.
	 */
	public abstract void onLoad();
	
	/**
	 * Called when the block is being saved.
	 */
	public abstract void onSave();
	
	/**
	 * Called when the block is placed.
	 */
	public abstract void onPlace(BlockPlaceEvent event);
	
	/**
	 * Called when the block is broken.
	 */
	public abstract void onBreak(BlockBreakEvent event);
	
	/**
	 * Called when the block is being interacted with by the player.
	 * @param event
	 */
	public abstract void onInteract(PlayerInteractEvent event);
	
	public Block getBlock() {
		return this.block;
	}
}
