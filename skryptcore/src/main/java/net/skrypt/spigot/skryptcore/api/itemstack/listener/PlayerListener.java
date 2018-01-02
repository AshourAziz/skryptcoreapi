/*
 * Copyright (c) 2017 Lukas F. - All Rights Reserved.
 * Redistribution, reselling, decompiling and modifying is forbidden.
 */

package net.skrypt.spigot.skryptcore.api.itemstack.listener;

import net.skrypt.spigot.skryptcore.api.exceptions.VersionException;
import net.skrypt.spigot.skryptcore.api.itemstack.CustomItemStack;
import net.skrypt.spigot.skryptcore.api.itemstack.CustomItemStackAPI;
import net.skrypt.spigot.skryptcore.api.nbt.NBTItemStack;
import net.skrypt.spigot.skryptcore.api.nbt.exceptions.NBTException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * <h1>[Insert Class Name]</h1>
 * [Insert a short, clean and concise description here.]
 * <p>
 * <p>
 * [Insert detailed description here, if needed.]
 * </p>
 *
 * @author Lukas
 * @version [Insert version of class (change after each edit)]
 * @see [Insert relevant /Class/ here.]
 * @since [Insert version of introduction.]
 */
public class PlayerListener implements Listener {
	
	@EventHandler
	public void onPlayerSelectItem(PlayerItemHeldEvent event) {
		int slot = event.getNewSlot();
		
		ItemStack itemStack = event.getPlayer().getInventory().getItem(slot);
		
		if (itemStack == null)
			return;
		
		if(itemStack.getType() == Material.AIR)
			return;
		
		try {
			NBTItemStack nbt = NBTItemStack.fromBukkit(itemStack);
			if (nbt.hasNBT() && nbt.getBoolean("customItemStack")) {
				Plugin plugin = Bukkit.getPluginManager().getPlugin(nbt.getString("plugin"));
				try {
					CustomItemStack customItemStack = CustomItemStackAPI.items.get(plugin).get(Class.forName(nbt.getString("class")));
					customItemStack.onSelectDefault(event);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (VersionException e) {
			e.printUserFriendlyMessage();
		} catch (NBTException e) {
			e.printUserFriendlyMessage();
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		ItemStack itemStack = event.getItem();
		try {
			NBTItemStack nbt = NBTItemStack.fromBukkit(itemStack);
			if (nbt.hasNBT() && nbt.getBoolean("customItemStack")) {
				Plugin plugin = Bukkit.getPluginManager().getPlugin(nbt.getString("plugin"));
				try {
					CustomItemStack customItemStack = CustomItemStackAPI.items.get(plugin).get(Class.forName(nbt.getString("class")));
					customItemStack.onInteractDefault(event);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (VersionException e) {
			e.printUserFriendlyMessage();
		} catch (NBTException e) {
			e.printUserFriendlyMessage();
		}
	}
}
