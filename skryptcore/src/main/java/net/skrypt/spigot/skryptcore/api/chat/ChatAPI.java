package net.skrypt.spigot.skryptcore.api.chat;

import net.skrypt.spigot.skryptcore.api.chat.event.ChatSendMessageEvent;
import net.skrypt.spigot.skryptcore.api.enums.MessageType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>Handles messages sent accross the server with full color- and formatting-support.<br />
 * This API can be used in static reference.</p>
 *
 * <p>Created by Skrypt on 25.02.2016.</p>
 */
public class ChatAPI {

	/**
	 * Sends a message to the console or the whole server based on the type.
	 * @param type
	 * @param message
	 */
	public static void sendMessage(MessageType type, String message) {
		if (type == MessageType.SERVER) {
			Collection<? extends Player> players = Bukkit.getOnlinePlayers();
			ChatSendMessageEvent event = new ChatSendMessageEvent(type, message, players);
			Bukkit.getPluginManager().callEvent(event);
		}

		if (type == MessageType.CONSOLE) {
			ChatSendMessageEvent event = new ChatSendMessageEvent(type, message);
			Bukkit.getPluginManager().callEvent(event);
		}

		if (type == MessageType.DEBUG) {
			ChatSendMessageEvent event = new ChatSendMessageEvent(type, ChatColor.RED + "" + ChatColor.BOLD + "[DEBUG]: " +
					ChatColor.RESET + "" + ChatColor.WHITE + message);
			Bukkit.getPluginManager().callEvent(event);
		}
	}

	/**
	 * Sends a message to the specified player/s.
	 * @param type
	 * @param message
	 * @param players
	 */
	public static void sendMessage(MessageType type, String message, Player... players) {
		if (type == MessageType.PLAYER) {
			Collection<Player> p = new ArrayList<>();
			for (Player player : players) {
				p.add(player);
			}
			ChatSendMessageEvent event = new ChatSendMessageEvent(type, message, p);
			Bukkit.getPluginManager().callEvent(event);
		}

		if (type == MessageType.DEBUG) {
			sendMessage(MessageType.PLAYER, ChatColor.RED + "" + ChatColor.BOLD + "[DEBUG]: " +
					ChatColor.RESET + "" + ChatColor.WHITE + message, players);


			sendMessage(MessageType.CONSOLE, message);
		}
	}

}
