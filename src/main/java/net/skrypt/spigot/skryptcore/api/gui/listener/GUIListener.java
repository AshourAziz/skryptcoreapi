/*
 * Copyright (c) 2016 Skrypt - All Rights Reserved.
 *
 * Web: www.skrypt.net
 * E-Mail: contact@skrypt.net
 *
 * Illegal modification and redistribution is not allowed.
 */

package net.skrypt.spigot.skryptcore.api.gui.listener;

import net.skrypt.spigot.skryptcore.api.exceptions.VersionException;
import net.skrypt.spigot.skryptcore.api.gui.GUI;
import net.skrypt.spigot.skryptcore.api.gui.event.GUIClickEvent;
import net.skrypt.spigot.skryptcore.api.gui.event.GUICloseEvent;
import net.skrypt.spigot.skryptcore.api.gui.event.GUIOpenEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GUIListener implements Listener {

	@EventHandler
	public void onGUIOpenEvent(GUIOpenEvent event) {
		GUI gui = event.getGUI();
		gui.start();
		try {
			gui.build();
		} catch (VersionException e) {
			e.printUserFriendlyMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@EventHandler
	public void onGUIClickEvent(GUIClickEvent event) {
		GUI gui = event.getGUI();
		gui.handleClick(event.getClickEvent());
	}

	@EventHandler
	public void onGUICloseEvent(GUICloseEvent event) {
		GUI gui = event.getGUI();
		gui.close();
	}

}