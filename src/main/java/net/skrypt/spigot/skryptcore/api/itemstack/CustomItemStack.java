package net.skrypt.spigot.skryptcore.api.itemstack;

import net.skrypt.spigot.skryptcore.api.exceptions.VersionException;
import net.skrypt.spigot.skryptcore.api.nbt.NBTItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Skrypt
 */
public abstract class CustomItemStack implements Serializable {
	
	// Private Variables
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
		
		configureDefault();
	}
	
	/**
	 * Use this to configure the ItemStack in it's default state.
	 */
	public abstract void configureDefault();
	
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
