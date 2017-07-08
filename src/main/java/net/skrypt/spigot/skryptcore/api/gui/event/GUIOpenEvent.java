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

public class GUIOpenEvent extends Event {

	// Static Variables
	private static final HandlerList handlers = new HandlerList();

	// Private Variables
	private GUI gui;
	private Player player;

	public GUIOpenEvent(GUI gui, Player player) {
		this.gui = gui;
		this.player = player;
	}

	public GUI getGUI() {
		return this.gui;
	}

	public Player getHumanPlayer() {
		return this.player;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
