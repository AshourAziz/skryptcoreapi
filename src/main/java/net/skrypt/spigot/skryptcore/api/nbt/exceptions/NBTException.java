package net.skrypt.spigot.skryptcore.api.nbt.exceptions;

import org.bukkit.plugin.Plugin;

/**
 * @author Skrypt
 */
public abstract class NBTException extends Exception {
	
	public static final String message = "An unknown error occured with the NBTItemStack regarding NBT. Please contact the developer.";
	
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
