/*
 * Copyright (c) 2016 Skrypt - All Rights Reserved.
 *
 * Web: www.skrypt.net
 * E-Mail: contact@skrypt.net
 *
 * Illegal modification and redistribution is not allowed.
 */

package net.skrypt.spigot.skryptcore.api.exceptions;

import net.skrypt.spigot.skryptcore.SkryptCoreAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;

public class OutdatedVersionException extends VersionException {

	private String message = "The plugin requires access to parts of the API that require a newer minecraft version. Please update your server.";

	/**
	 * Returns a Human-Readable message (for the server owner) with instructions what to do with this kind of exceptions.
	 * @return String - a human-readable message describing the error.
	 */
	public String getMessage() {
		return message;
	}
	
	@Override
	public void printUserFriendlyMessage() {
		ConsoleCommandSender console = Bukkit.getConsoleSender();
		console.sendMessage(ChatColor.RED + message);
	}
	
	@Override
	public void disablePlugin(Plugin plugin) {
		Bukkit.getServer().getPluginManager().disablePlugin(plugin);
		ConsoleCommandSender console = Bukkit.getConsoleSender();
		console.sendMessage(ChatColor.RED + "The plugin was disabled automatically to avoid further critical errors.");
	}
	
}
