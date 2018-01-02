/*
 * Copyright (c) 2016 Skrypt - All Rights Reserved.
 *
 * Web: www.skrypt.net
 * E-Mail: contact@skrypt.net
 *
 * Illegal modification and redistribution is not allowed.
 */

package net.skrypt.spigot.skryptcore.api.actionbar;

import net.skrypt.spigot.skryptcore.API;
//import net.skrypt.spigot.skryptcore.api.actionbar.versions.ActionBar_1_12_R1;
import net.skrypt.spigot.skryptcore.api.enums.ServerVersion;
import net.skrypt.spigot.skryptcore.api.exceptions.UnsupportedVersionException;
import org.bukkit.entity.Player;

public interface ActionBar {

	static ActionBar create() throws UnsupportedVersionException {
		return create(null);
	}

	static ActionBar create(String message) throws UnsupportedVersionException {
		return create(message, (Player[])null);
	}

	static ActionBar create(String message, Player... players) throws UnsupportedVersionException {
		ServerVersion version = API.getVersion();
		if (version == ServerVersion.V_1_12_R1) {
			//return new ActionBar_1_12_R1(message, players);
			System.out.println("THIS IS A BAD VERSION OF SKRYPTCORE PLEASE LOOK INTO ACTIONBAR.JAVA LINE 32");
		}
		throw new UnsupportedVersionException();
	}

	void send();

	void setMessage(String message);
	void setPlayers(Player... players);
	void setDuration(float duration);

	void execute(Player player);

}
