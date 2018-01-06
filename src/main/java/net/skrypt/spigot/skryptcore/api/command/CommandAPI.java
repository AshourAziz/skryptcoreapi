package net.skrypt.spigot.skryptcore.api.command;

import net.skrypt.spigot.skryptcore.API;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

/**
 * <p>Handles the plugin's commands.</p>
 * <p>Advantages of this API are that each command has it's own class and that it doesn't need
 * to be inside your plugin.yml file. The API handles the registration itself.</p>
 * <p>Please visit pub.skrypt.net to learn how to create commands with this API.</p>
 *
 * <p>Created by Skrypt on 25.02.2016.</p>
 */
public class CommandAPI {

	/**
	 * <p>Registers the command/s for the plugin.</p>
	 * <p>Please visit pub.skrypt.net to learn how to create commands with this API.</p>
	 * @param commands Instance of the command/s class
	 */
	public static void register(Command... commands) {
		try{
			Class<?> craftServer = API.getCraftbukkitClass("CraftServer");
			final Field f = craftServer.getDeclaredField("commandMap");
			f.setAccessible(true);
			CommandMap cm = (CommandMap)f.get(Bukkit.getServer());
			for (Command command : commands) {
				cm.register("", command);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
