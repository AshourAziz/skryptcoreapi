/*
 * Copyright (c) 2016 Skrypt - All Rights Reserved.
 *
 * Web: www.skrypt.net
 * E-Mail: contact@skrypt.net
 *
 * Illegal modification and redistribution is not allowed.
 */

package net.skrypt.spigot.skryptcore.api.actionbar.versions;

import net.minecraft.server.v1_12_R1.ChatMessage;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.skrypt.spigot.skryptcore.SkryptCoreAPI;
import net.skrypt.spigot.skryptcore.api.actionbar.ActionBar;
import net.skrypt.spigot.skryptcore.api.actionbar.ActionBarSendRepeating;
import net.skrypt.spigot.skryptcore.api.chat.ChatAPI;
import net.skrypt.spigot.skryptcore.api.enums.MessageType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ActionBar_1_12_R1 implements ActionBar {

	private int id;
	private String message;
	private ArrayList<Player> players;
	private int duration = 60;

	public ActionBar_1_12_R1(String message, Player... players) {
		this.id = 0;
		this.players = new ArrayList<>();

		if (message != null)
			this.message = message;
		if (players != null)
			for (Player player : players) this.players.add(player);
	}

	@Override
	public void send() {
		for (Player player : players) {
			ActionBarSendRepeating task = new ActionBarSendRepeating(this, id, player, this.duration);
			int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(SkryptCoreAPI.getInstance(), task, 0L, 40L);
			ActionBarSendRepeating.tasks.put(id, taskID);
			id++;
		}
	}

	@Override
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public void setPlayers(Player... players) {
		for (Player player : players) {
			this.players.add(player);
		}
	}

	@Override
	public void setDuration(float duration) {
		this.duration = (int)(duration*20f);
		ChatAPI.sendMessage(MessageType.CONSOLE, "Duration: " + this.duration);
	}

	@Override
	public void execute(Player player) {
		ChatMessage cm = new ChatMessage(ChatColor.translateAlternateColorCodes('&', message));
		PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.ACTIONBAR, cm);
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
	}
}
