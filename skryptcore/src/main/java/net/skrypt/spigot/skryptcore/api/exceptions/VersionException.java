package net.skrypt.spigot.skryptcore.api.exceptions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;

/**
 * Created by lukas on 16/06/2017.
 */
public abstract class VersionException extends Exception {
	
	public static final String message = "This minecraft version is not yet supported by the API. Please contact the developer of the plugin.";
	
	/**
	 * Returns a Human-Readable message (for the server owner) with instructions what to do with this kind of exceptions.
	 * @return String - a human-readable message describing the error.
	 */
	public abstract String getMessage();
	
	public abstract void printUserFriendlyMessage();
	
	public abstract void disablePlugin(Plugin plugin);
	
	@Override
	public void printStackTrace() {
		printUserFriendlyMessage();
		super.printStackTrace();
	}
}
