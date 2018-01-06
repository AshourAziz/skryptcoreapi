/*
 * Copyright (c) 2016 Skrypt - All Rights Reserved.
 *
 * Web: www.skrypt.net
 * E-Mail: contact@skrypt.net
 *
 * Illegal modification and redistribution is not allowed.
 */

package net.skrypt.spigot.skryptcore.api.gui.event;

import net.skrypt.spigot.skryptcore.api.gui.GUI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIClickEvent extends Event {

	// Static Variables
	private static final HandlerList handlers = new HandlerList();

	// Private Variables
	private GUI gui;
	private Player humanPlayer;
	private InventoryClickEvent event;

	public GUIClickEvent(GUI _gui, Player _hp, InventoryClickEvent _event) {
		this.gui = _gui;
		this.humanPlayer = _hp;
		this.event = _event;
	}

	public GUI getGUI() {
		return this.gui;
	}

	public Player getHumanPlayer() {
		return this.humanPlayer;
	}

	public InventoryClickEvent getClickEvent() {
		return this.event;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}