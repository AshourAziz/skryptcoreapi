package net.skrypt.spigot.skryptcore.api.chat.event;

import net.skrypt.spigot.skryptcore.api.enums.MessageType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Skrypt on 11.06.2016.
 */
public class ChatSendMessageEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private MessageType type;
	private String message;
	private Collection<? extends Player> players;

	public ChatSendMessageEvent(MessageType _type, String _message) {
		this(_type, _message, (Player)null);
	}

	public ChatSendMessageEvent(MessageType _type, String _message, Player _player) {
		this(_type, _message, ((_player == null) ? null : new ArrayList<Player>(){{add(_player);}}));


	}

	public ChatSendMessageEvent(MessageType _type, String _message, Collection<? extends Player> _players) {
		type = _type;
		message = _message;
		players = _players;
		if (players != null) {
			for (Player player : players) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
			}
		} else {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
		}
	}


	public MessageType getMessageType() {
		return type;
	}

	public String getMessage() {
		return message;
	}

	public Collection<? extends Player> getPlayers() {
		return players;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}