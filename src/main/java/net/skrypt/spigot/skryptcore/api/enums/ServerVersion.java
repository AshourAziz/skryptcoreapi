/*
 * Copyright (c) 2016 Skrypt - All Rights Reserved.
 *
 * Web: www.skrypt.net
 * E-Mail: contact@skrypt.net
 *
 * Illegal modification and redistribution is not allowed.
 */

package net.skrypt.spigot.skryptcore.api.enums;

import net.skrypt.spigot.skryptcore.api.chat.ChatAPI;
import org.bukkit.ChatColor;

public enum ServerVersion {
	
	V_1_12_R1("v1_12_R1"),
	V_1_11_R2("v1_11_R2"),
	V_1_11_R1("v1_11_R1"),
	V_1_10_R2("v1_10_R2"),
	V_1_10_R1("v1_10_R1"),
	V_1_8_R3("v1_8_R3"),
	V_1_8_R2("v1_8_R2"),
	V_1_8_R1("v1_8_R1");

	private String version;

	ServerVersion(String version) {
		this.version = version;
	}

	public String getVersionString() {
		return this.version;
	}

	public static ServerVersion fromString(String version) {
		for (ServerVersion sv : ServerVersion.values()) {
			if (sv.getVersionString().equalsIgnoreCase(version)) {
				return sv;
			}
		}
		ChatAPI.sendMessage(MessageType.CONSOLE, ChatColor.DARK_RED + "Version: " + version);
		return ServerVersion.values()[0];
	}

}
