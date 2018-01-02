/*
 * Copyright (c) 2016 Skrypt - All Rights Reserved.
 *
 * Web: www.skrypt.net
 * E-Mail: contact@skrypt.net
 *
 * Illegal modification and redistribution is not allowed.
 */

package net.skrypt.spigot.skryptcore.api.hologram.task;


import net.skrypt.spigot.skryptcore.api.hologram.Hologram;
import net.skrypt.spigot.skryptcore.api.hologram.HologramAPI;

public class HologramShowDuration implements Runnable {

	private Hologram hologram;

	public HologramShowDuration(Hologram hologram) {
		this.hologram = hologram;
	}

	@Override
	public void run() {
		//hologram.delete();
		HologramAPI.delete(hologram);
	}
}
