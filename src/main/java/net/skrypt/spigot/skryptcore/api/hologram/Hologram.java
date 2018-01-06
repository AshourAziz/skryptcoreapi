/*
 * Copyright (c) 2016 Skrypt - All Rights Reserved.
 *
 * Web: www.skrypt.net
 * E-Mail: contact@skrypt.net
 *
 * Illegal modification and redistribution is not allowed.
 */

package net.skrypt.spigot.skryptcore.api.hologram;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public interface Hologram {

	//HashMap<Integer, Hologram> holograms = new HashMap<Integer, Hologram>();

	void setText(String... text);
	void setText(ArrayList<String> text);
	void addText(String... text);
	void addText(ArrayList<String> text);
	void removeText(int... lines);

	void show();
	
	/**
	 * Shows the hologram for the specified amount of seconds.
	 * @param duration
	 */
	void show(float duration);
	void show(Player... players);
	void show(float duration, Player... players);

	void hide();
	void hide(Player... players);

	void delete();

	int getID();
}
