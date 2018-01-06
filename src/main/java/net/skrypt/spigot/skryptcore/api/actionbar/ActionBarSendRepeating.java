/*
 * Copyright (c) 2016 Skrypt - All Rights Reserved.
 *
 * Web: www.skrypt.net
 * E-Mail: contact@skrypt.net
 *
 * Illegal modification and redistribution is not allowed.
 */

package net.skrypt.spigot.skryptcore.api.actionbar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ActionBarSendRepeating implements Runnable {

	public static ActionBar activeActionBar = null;
	public static HashMap<Integer, Integer> tasks = new HashMap<>();

	private ActionBar actionBar;
	private int id;
	private int counter;
	private Player player;

	public ActionBarSendRepeating(ActionBar ab, int id, Player player, int duration) {
		actionBar = ab;

		if (activeActionBar == null) activeActionBar = actionBar;
		if (activeActionBar != actionBar) {
			for (Map.Entry<Integer, Integer> task : tasks.entrySet()) {
				Bukkit.getScheduler().cancelTask(task.getValue());
			}
			activeActionBar = actionBar;
		}

		this.id = id;
		counter = duration;
		this.player = player;
	}

	@Override
	public void run() {
		if (counter <= 0) Bukkit.getScheduler().cancelTask(tasks.get(id));
		actionBar.execute(player);
		counter -= 40;
	}
}
