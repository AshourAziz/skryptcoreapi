package net.skrypt.spigot.skryptcore.api.itemstack;

import net.skrypt.spigot.skryptcore.api.exceptions.OutdatedVersionException;
import net.skrypt.spigot.skryptcore.api.exceptions.UnsupportedVersionException;
import net.skrypt.spigot.skryptcore.api.exceptions.VersionException;
import net.skrypt.spigot.skryptcore.api.nbt.NBTItemStack;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.util.CraftMagicNumbers;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Skrypt
 */
public abstract class CustomItemStack implements Serializable {
	
	// Private Variables
	private Plugin plugin;
	
	private String name;
	private Material material;
	private short durability;
	
	private ArrayList<String> lore;
	
	/**
	 * Creates an instance of CustomItemStack.
	 * @param name Name of the item.
	 * @param material Material of the item.
	 */
	public CustomItemStack (String name, Material material) {
		this.name = name;
		this.material = material;
	}
	
	public void onSelectDefault(PlayerItemHeldEvent event) {
		onSelect(event);
	}
	
	protected abstract void onSelect(PlayerItemHeldEvent event);
	
	public void onInteractDefault(PlayerInteractEvent event) {
		onInteract(event);
	}
	
	protected abstract void onInteract(PlayerInteractEvent event);
	
	/**
	 * Called each time the getItemStack() method is called.
	 *
	 * Only use this method to apply NBT to the ItemStack!
	 *
	 * @param nbt The instance of the NBT ItemStack.
	 */
	protected abstract void setNBT(NBTItemStack nbt);
	
	/**
	 * Changes the custom ItemStack's name.
	 * The changes only take effect on newly created Bukkit ItemStacks using the getItemStack() method.
	 * @param name The new name.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Changes the custom ItemStack's durability.
	 * The changes only take effect on newly created Bukkit ItemStacks using the getItemStack() method.
	 *
	 * This is very useful to change the appearance of the item via resource packs.
	 * @param durability The new durability.
	 */
	public void setDurability(short durability) {
		this.durability = durability;
	}
	
	/**
	 * Changes the custom ItemStack's lore.
	 * The changes only take effect on newly created Bukkit ItemStacks using the getItemStack() method.
	 * @param lore The new lore.
	 */
	public void setLore(ArrayList<String> lore) {
		this.lore = lore;
	}
	
	/**
	 * Returns a Bukkit ItemStack instance of the custom item.
	 * @return Bukkit ItemStack
	 */
	public ItemStack getItemStack() {
		ItemStack itemStack = new ItemStack(this.material, 1, this.durability);
		
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.name));
		
		if (this.lore != null && !this.lore.isEmpty())
			itemMeta.setLore(this.lore);
		
		itemStack.setItemMeta(itemMeta);
		
		try {
			NBTItemStack nbt = NBTItemStack.fromBukkit(itemStack);
			
			nbt.setBoolean("custom", true);
			nbt.setString("plugin", this.plugin.getName());
			nbt.setString("class", this.getClass().getName());
			
			setNBT(nbt);
			
			itemStack = nbt.toBukkit();
			
		} catch (UnsupportedVersionException e) {
			e.printUserFriendlyMessage();
		} catch (OutdatedVersionException e) {
			e.printUserFriendlyMessage();
		}
		
		
		return itemStack;
	}
	
	/**
	 * Returns an instance of the plugin this item was created in.
	 * @return Plugin
	 */
	public Plugin getPlugin() {
		return this.plugin;
	}
	
	/**
	 * Sets the plugin this item was created in.
	 * @param plugin The instance of the plugin.
	 */
	protected void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}
}
