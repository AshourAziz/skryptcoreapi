/*
 * Copyright (c) 2016 Skrypt - All Rights Reserved.
 *
 * Web: www.skrypt.net
 * E-Mail: contact@skrypt.net
 *
 * Illegal modification and redistribution is not allowed.
 */

package net.skrypt.spigot.skryptcore.api.gui;

import net.skrypt.spigot.skryptcore.api.exceptions.OutdatedVersionException;
import net.skrypt.spigot.skryptcore.api.exceptions.UnsupportedVersionException;
import net.skrypt.spigot.skryptcore.api.exceptions.VersionException;
import net.skrypt.spigot.skryptcore.api.gui.enums.GUIOwner;
import net.skrypt.spigot.skryptcore.api.gui.event.GUIOpenEvent;
import net.skrypt.spigot.skryptcore.api.gui.owners.GUIOwnerBlock;
import net.skrypt.spigot.skryptcore.api.gui.owners.GUIOwnerEntity;
import net.skrypt.spigot.skryptcore.api.gui.owners.GUIOwnerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.UUID;

public abstract class GUI {

	// Static Variables
	public static HashMap<Plugin, HashMap<Class<? extends GUI>, GUI>> guis = new HashMap<>();
	public static HashMap<UUID, GUI> players = new HashMap<>();
	
	// Private Variables
	private Plugin plugin;
	private String title;
	private int size;
	private Inventory inventory;
	private InventoryType inventoryType;
	private GUIOwner ownerType;
	private Object owner;
	private int clickedSlot;
	private boolean persistent;
	
	public GUI(Plugin plugin, GUIOwner ownerType, InventoryType inventoryType, String title, int size, Object owner, boolean persistent) {
		this.plugin = plugin;
		this.title = title;
		this.size = size;
		this.inventoryType = inventoryType;
		this.ownerType = ownerType;
		this.owner = owner;
		this.persistent = persistent;
		
		if (this.inventoryType == InventoryType.CHEST || this.inventoryType == InventoryType.PLAYER)
			this.inventory = Bukkit.createInventory(null, this.size, title);
		else
			this.inventory = Bukkit.createInventory(null, this.inventoryType, this.title);
		
		
		
		if (ownerType == GUIOwner.BLOCK)
			GUIOwnerBlock.add(plugin, (Block)owner, this);
		
		if (ownerType == GUIOwner.PLAYER)
			GUIOwnerPlayer.add(plugin, (Player)owner, this);
		
		if (ownerType == GUIOwner.ENTITY)
			GUIOwnerEntity.add(plugin, (Entity)owner, this);
		
		/*if (this.persistent) {
			/if (guis.containsKey(plugin)) {
				HashMap<Class<? extends GUI>, GUI> hashMap = guis.get(plugin);
				hashMap.put(this.getClass(), this);
				guis.put(plugin, hashMap);
			} else {
				HashMap<Class<? extends GUI>, GUI> hashMap = new HashMap<>();
				hashMap.put(this.getClass(), this);
				guis.put(plugin, hashMap);
			}
		}*/
	}
	
	public void setOwner(Object object) {
		this.owner = object;
	}
	
	/**
	 * Returns the plugin this GUI belongs to.
	 * @return
	 */
	public Plugin getPlugin() {
		return this.plugin;
	}
	
	/**
	 * Returns the title of the GUI.
	 * @return
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Returns the size of the GUI.
	 * @return
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * Returns the owner of the GUI.
	 * @return
	 */
	public Object getOwner() {
			return this.owner;
	}

	/**
	 * Returns the Inventory instance of the GUI.
	 * @return
	 */
	public Inventory getInventory() {
		return this.inventory;
	}

	/**
	 * Returns the last clicked slot in this GUI.
	 * @return
	 */
	public int getClickedSlot() {
		return this.clickedSlot;
	}

	/**
	 * Opens the GUI to the GUI's owner.
	 * Works only if the GUIOwner is a PLAYER.
	 */
	public void open() {
		if (ownerType == GUIOwner.PLAYER)
			open((Player)this.owner);
	}
	
	/**
	 * Opens the GUI to the specified player.
	 * This method was added to add support for GUIs not owned by non-players and the environment.
	 * @param player
	 */
	public void open(Player player) {
		player.openInventory(this.inventory);
		
		players.put(player.getUniqueId(), this);
		
		GUIOpenEvent event = new GUIOpenEvent(this, player);
		Bukkit.getPluginManager().callEvent(event);
	}

	/**
	 * Called once when the GUI is opened. Useful to initialize variables.
	 */
	public abstract void start();

	/**
	 * Builds the GUI. Use this to recalculate the content of the GUI.
	 */
	public abstract void build() throws OutdatedVersionException, UnsupportedVersionException;

	/**
	 * Called each time the user clicks, either inside or outside the GUI.
	 */
	public void handleClick(InventoryClickEvent event) {
		this.clickedSlot = event.getSlot();
		try {
			click(event);
		} catch (VersionException e) {
			e.printUserFriendlyMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Defines what happens when the user clicks while the GUI is open.
	 * @param event
	 */
	public abstract void click(InventoryClickEvent event) throws OutdatedVersionException, UnsupportedVersionException;

	/**
	 * Called once when the GUI is closed. Useful for saving and similar.
	 */
	public abstract void close();
	
	/**
	 * Fills the whole GUI with the given item.
	 */
	public void setBackground(ItemStack item) {
		for (int row = 1; row <= getNumRows(); row++) {
			setRow(row, item);
		}
	}
	
	/**
	 * Fills the row's slot position/s with the specified item.
	 * @param row
	 * @param item
	 * @param positions
	 */
	public void setSlot(int row, ItemStack item, SlotPosition... positions) {
		for (SlotPosition position : positions) {
			setRow(row, position, position, item);
		}
	}
	
	/**
	 * Fills the row with the specified item.
	 * @param row
	 */
	public void setRow(int row, ItemStack item) {
		setRow(row, SlotPosition.RIGHT, item);
	}
	
	/**
	 * Fills the row, starting from the first slot to the end slot (inclusive), with the specified item.
	 * @param row
	 * @param end
	 * @param item
	 */
	public void setRow(int row, SlotPosition end, ItemStack item) {
		setRow(row, SlotPosition.LEFT, end, item);
	}
	
	/**
	 * Fills the row, starting from the begin slot (inclusive) to the end slot (inclusive), with the specified item.
	 * @param row
	 * @param begin
	 * @param end
	 * @param item
	 */
	public void setRow(int row, SlotPosition begin, SlotPosition end, ItemStack item) {
		for (int i = begin.getPos(); i <= end.getPos(); i++) {
			getInventory().setItem(((row-1)*9)+i, item);
		}
	}
	
	/**
	 * Returns the amount of rows the GUI has.
	 * @return
	 */
	public int getNumRows() {
		return this.inventory.getSize()/9;
	}
	
	/**
	 * Returns the row the specified slot is in.
	 * @param slot
	 * @return
	 */
	public int getRowOfSlot(int slot) {
		int curRow = 1;
		for (int i = 0; i < inventory.getSize(); i+=9) {
			if (slot >= i && slot < i+9) return curRow;
			curRow++;
		}
		return 0;
	}
	
	/**
	 * Returns the slot based on the provided row and position.
	 * @param row
	 * @param pos
	 * @return
	 */
	public int getSlotFromPosition(int row, SlotPosition pos) {
		int start = (row-1) * 9;
		return (start + pos.getPos());
	}
	
	/**
	 * Checks if the slot is in the specified position.
	 * @param slot
	 * @param pos
	 * @return
	 */
	public boolean checkPosition(int slot, SlotPosition pos) {
		int row = getRowOfSlot(slot);
		int start = (row-1) * 9;

		return (slot == start + pos.getPos()) ? true : false;
	}
	
	/**
	 * Saves all GUIs which are set to be persistent. (ie keep their contents after restarts, etc.)
	 * The data is saved in SkryptCoreAPI's data folder.
	 */
	public static void save() {
		/*
		// Loop through all plugins
		for (Map.Entry<Plugin, HashMap<Class<? extends GUI>, GUI>> plugin : guis.entrySet()) {
			
			// Loop through all GUIs
			int id = 0;
			for (Map.Entry<Class<? extends GUI>, GUI> entry : plugin.getValue().entrySet()) {
				GUI gui = entry.getValue();
				
				if (!gui.persistent)
					continue;
				
				// Creating the data file for this GUI
				String label = plugin.getKey().getName().toLowerCase() + ".data.gui." + gui.ownerType.name().toLowerCase() + "." + id;
				Config config = new Config(label,
						"",
						"blank.yml",
						"data/" + plugin.getKey().getName().toLowerCase(),
						id + ".dat");
				SkryptCoreAPI.getInstance().getFlatfileAPI().add(config);
				
				// Saving Basic Information about the GUI
				// - Title
				// - Size
				// - OwnerType
				// - InventoryType
				// - ...
				FileConfiguration fc = SkryptCoreAPI.getInstance().getFlatfileAPI().get(label);
				fc.set("title", gui.getTitle());
				fc.set("size", gui.getSize());
				if (gui.ownerType == GUIOwner.PLAYER)
					fc.set("owner", ((Player)gui.getOwner()).getUniqueId().toString());
				
				if (gui.ownerType == GUIOwner.BLOCK)
					fc.set("owner", ((Block)gui.getOwner()).getLocation().getBlockX() + "|" + ((Block)gui.getOwner()).getLocation().getBlockY() + "|" + ((Block)gui.getOwner()).getLocation().getBlockZ());
				
				if (gui.ownerType == GUIOwner.ENTITY)
					fc.set("owner", ((Entity)gui.getOwner()).getEntityId());
				fc.set("owner-type", gui.ownerType.name().toUpperCase());
				fc.set("inventory-type", gui.inventoryType.name().toUpperCase());
				
				for (int i = 0; i < gui.getInventory().getSize(); i++) {
					ItemStack item = gui.getInventory().getItem(i);
					if (item == null || item.getType() == Material.AIR)
						continue;
					
					fc.set("content." + i + ".material", item.getType().toString());
				}
				SkryptCoreAPI.getInstance().getFlatfileAPI().save();
			}
		}*/
	}
	
	/**
	 * Enum for slot positions.
	 */
	public enum SlotPosition {
		LEFT(0),
		SECOND(1),
		THIRD(2),
		FOURTH(3),
		MIDDLE(4),
		SIXTH(5),
		SEVENTH(6),
		EIGHTH(7),
		RIGHT(8);

		int pos = 0;

		SlotPosition(int _pos) {
			pos = _pos;
		}

		public int getPos() {
			return pos;
		}
		
		public SlotPosition add(int amount) {
			if (amount < 0)
				return this;
			
			int p = this.getPos() + amount;
			if (p >= RIGHT.getPos())
				return RIGHT;
			
			return this.of(p);
		}
		
		public SlotPosition subtract(int amount) {
			if (amount < 0)
				return this;
			
			int p = this.getPos() - amount;
			if (p <= LEFT.getPos())
				return LEFT;
			
			return this.of(p);
		}
		
		public static SlotPosition of(int _pos) {
			if (_pos >= 0 &&_pos <= 8) {
				for (SlotPosition sp : SlotPosition.values()) {
					if (sp.getPos() == _pos)
						return sp;
				}
			}
			return null;
		}
	}

}
