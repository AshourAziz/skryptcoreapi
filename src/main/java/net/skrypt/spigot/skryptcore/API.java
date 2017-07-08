package net.skrypt.spigot.skryptcore;

import net.skrypt.spigot.skryptcore.api.enums.ServerVersion;
import net.skrypt.spigot.skryptcore.api.gui.GUI;
import net.skrypt.spigot.skryptcore.api.gui.listener.GUIListener;
import net.skrypt.spigot.skryptcore.api.gui.listener.InventoryListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

/**
 * @author Skrypt
 */
public class API {
	
	public static void enable() {
		
		PluginManager pm = SkryptCoreAPI.getInstance().getServer().getPluginManager();
		pm.registerEvents(new GUIListener(), SkryptCoreAPI.getInstance());
		pm.registerEvents(new InventoryListener(), SkryptCoreAPI.getInstance());
		//pm.registerEvents(new BlockListener(), SkryptCoreAPI.getInstance());
		//pm.registerEvents(new PlayerListener(), SkryptCoreAPI.getInstance());
	}
	
	public static void disable() {
		GUI.save();
		
	}
	
	public static Class<?> getCraftbukkitClass(String classString) throws ClassNotFoundException {
		return getReflectedClass("org.bukkit.craftbukkit", classString);
	}
	
	public static Class<?> getMinecraftClass(String classString) throws ClassNotFoundException {
		return getReflectedClass("net.minecraft.server", classString);
	}
	
	private static Class<?> getReflectedClass(String packageString, String classString) throws ClassNotFoundException {
		//String version = Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
		String name = packageString + "." + getVersion().getVersionString() + "." + classString;
		Class<?> cbClass = Class.forName(name);
		return cbClass;
	}
	
	public static ServerVersion getVersion() {
		String version = Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
		return ServerVersion.fromString(version);
	}
}
