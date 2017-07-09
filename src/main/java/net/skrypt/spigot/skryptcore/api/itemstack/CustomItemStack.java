package net.skrypt.spigot.skryptcore.api.itemstack;

import net.skrypt.spigot.skryptcore.api.exceptions.OutdatedVersionException;
import net.skrypt.spigot.skryptcore.api.exceptions.UnsupportedVersionException;
import net.skrypt.spigot.skryptcore.api.exceptions.VersionException;
import net.skrypt.spigot.skryptcore.api.nbt.NBTItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
	
	// Public Static Variables
	public static HashMap<ItemStack, CustomItemStack> items = new HashMap<>();
	
	// Private Variables
	private Plugin plugin;
	private String name;
	private Material material;
	private int amount;
	private short durability;
	private ItemMeta meta;
	
	private ItemStack itemStack;
	/**
	 * Creates a new instance of CustomItemStack.
	 * You will need this to create own items.
	 */
	public CustomItemStack (String name, Material material) {
		this(name, material, 1);
	}
	
	public CustomItemStack (String name, Material material, int amount) {
		this(name, material, amount, (short)0);
	}
	
	public CustomItemStack(String name, Material material, int amount, short durability) {
		this(name, material, amount, durability, null);
	}
	public CustomItemStack(String name, Material material, int amount, short durability, ArrayList<String> lore) {
		this.name = name;
		this.material = material;
		this.amount = amount;
		this.durability = durability;
		
		this.itemStack = new ItemStack(material, amount, durability);
		this.meta = this.itemStack.getItemMeta();
		this.meta.setDisplayName(ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', name));
		this.itemStack.setItemMeta(this.meta);
		try {
			NBTItemStack nbt = NBTItemStack.fromBukkit(this.itemStack);
			nbt.setBoolean("customItemStack", true);
			this.itemStack = nbt.toBukkit();
		} catch (VersionException e) {
			e.printUserFriendlyMessage();
		}
		
		configureDefaults();
	}
	
	public void onSelectDefault(PlayerItemHeldEvent event) {
		onSelect(event);
	}
	
	public abstract void onSelect(PlayerItemHeldEvent event);
	
	public void onInteractDefault(PlayerInteractEvent event) {
		onInteract(event);
	}
	
	public abstract void onInteract(PlayerInteractEvent event);
	
	/**
	 * Use this to configure the ItemStack in it's default state.
	 */
	public abstract void configureDefaults();
	
	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
		try {
			NBTItemStack nbt = NBTItemStack.fromBukkit(this.itemStack);
			nbt.setString("plugin", this.plugin.getName());
			nbt.setString("class", this.getClass().getSimpleName().toString());
			this.itemStack = nbt.toBukkit();
		} catch (UnsupportedVersionException e) {
			e.printUserFriendlyMessage();
		} catch (OutdatedVersionException e) {
			e.printUserFriendlyMessage();
		}
	}
	
	public void setName(String name) {
		this.name = name;
		updateItemStack();
	}
	
	public void setMaterial(Material material) {
		this.material = material;
		updateItemStack();
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
		updateItemStack();
	}
	
	public void setDurability(short durability) {
		this.durability = durability;
		updateItemStack();
	}
	
	public void setLore(ArrayList<String> lore) {
		getItemMeta().setLore(lore);
		updateItemStack();
	}
	
	public void setItemMeta(ItemMeta meta) {
		this.meta = meta;
		updateItemStack();
	}
	
	public Plugin getPlugin() {
		return this.plugin;
	}
	
	public ItemMeta getItemMeta() {
		return this.meta;
	}
	
	public ItemStack getItemStack() {
		return this.itemStack;
	}
	
	public void setItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
		updateItemStack();
	}
	
	private void updateItemStack() {
		this.getItemMeta().setDisplayName(ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', this.name));
		this.itemStack.setType(this.material);
		this.itemStack.setAmount(this.amount);
		this.itemStack.setDurability(this.durability);
		this.itemStack.setItemMeta(this.meta);
	}
	
}
