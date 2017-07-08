/*
 * Copyright (c) 2016 Skrypt - All Rights Reserved.
 *
 * Web: www.skrypt.net
 * E-Mail: contact@skrypt.net
 *
 * Illegal modification and redistribution is not allowed.
 */

package net.skrypt.spigot.skryptcore.api.gui.listener;

import net.skrypt.spigot.skryptcore.api.gui.GUI;
import net.skrypt.spigot.skryptcore.api.gui.event.GUIClickEvent;
import net.skrypt.spigot.skryptcore.api.gui.event.GUICloseEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryListener implements Listener {

	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {
		Player player = (Player)event.getWhoClicked();
		
		if (!GUI.players.containsKey(player.getUniqueId()))
			return;
		
		GUI gui = GUI.players.get(player.getUniqueId());
		
		if (gui != null) {
			
			// Fix whole server crashing when Shift-Clicking in Hoppers
			// TODO: Find other GUIs causing this problem - so far only hopper is known
			if (event.getInventory().getType() == InventoryType.HOPPER) {
				if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY)
					event.setCancelled(true);
			}
			
			GUIClickEvent clickEvent = new GUIClickEvent(gui, player, event);
			Bukkit.getPluginManager().callEvent(clickEvent);
		}
	}

	@EventHandler
	public void onInventoryCloseEvent(InventoryCloseEvent event) {
		Player player = (Player)event.getPlayer();
		
		if (!GUI.players.containsKey(player.getUniqueId()))
			return;
		
		GUI gui = GUI.players.get(player.getUniqueId());
		
		if (gui != null) {
			GUICloseEvent closeEvent = new GUICloseEvent(gui, player);
			Bukkit.getPluginManager().callEvent(closeEvent);
		}
	}

}