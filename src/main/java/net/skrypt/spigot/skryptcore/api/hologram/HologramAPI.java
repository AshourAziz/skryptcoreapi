/*
 * Copyright (c) 2016 Skrypt - All Rights Reserved.
 *
 * Web: www.skrypt.net
 * E-Mail: contact@skrypt.net
 *
 * Illegal modification and redistribution is not allowed.
 */

package net.skrypt.spigot.skryptcore.api.hologram;

import net.skrypt.spigot.skryptcore.API;
import net.skrypt.spigot.skryptcore.api.enums.ServerVersion;
import net.skrypt.spigot.skryptcore.api.exceptions.UnsupportedVersionException;
import net.skrypt.spigot.skryptcore.api.hologram.versions.Hologram_1_12_R1;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class HologramAPI {

	private static int id = 0;

	private static HashMap<Integer, Hologram> holograms = new HashMap<>();

	public static Hologram create(Player player) throws UnsupportedVersionException {
		return create(player.getLocation());
	}
	public static Hologram create(Location location) throws UnsupportedVersionException {
		return create(location, new ArrayList<>());
	}
	public static Hologram create(Player player, String... text) throws UnsupportedVersionException {
		return create(player.getLocation(), text);
	}
	public static Hologram create(Location location, String... text) throws UnsupportedVersionException {
		return create(location, new ArrayList<String>(){{
			for (String line : text) add(ChatColor.translateAlternateColorCodes('&', line));
		}});
	}
	public static Hologram create(Player player, ArrayList<String> text) throws UnsupportedVersionException {
		return create(player.getLocation(), text);

	}
	public static Hologram create(Location location, ArrayList<String> text) throws UnsupportedVersionException {
		ServerVersion version = API.getVersion();
		Hologram hologram = null;
		if (version == ServerVersion.V_1_12_R1) {
			hologram = new Hologram_1_12_R1(id, location, text);
		}
		if (hologram != null) {
			holograms.put(id, hologram);
			id++;
			return hologram;
		}
		throw new UnsupportedVersionException();
	}

	public static Hologram get(int id) {
		return holograms.get(id);
	}

	public static HashMap<Integer, Hologram> get() {
		return holograms;
	}

	public static void delete(Hologram hologram) {
		hologram.delete();
	}

	public static void delete(int id) {
		delete(holograms.get(id));
	}
}
