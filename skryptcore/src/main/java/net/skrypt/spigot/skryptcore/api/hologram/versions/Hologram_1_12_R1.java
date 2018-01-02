///*
// * Copyright (c) 2016 Skrypt - All Rights Reserved.
// *
// * Web: www.skrypt.net
// * E-Mail: contact@skrypt.net
// *
// * Illegal modification and redistribution is not allowed.
// */
//
//package net.skrypt.spigot.skryptcore.api.hologram.versions;
//
//import net.minecraft.server.v1_12_R1.EntityArmorStand;
//import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;
//import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntityLiving;
//import net.skrypt.spigot.skryptcore.SkryptCoreAPI;
//import net.skrypt.spigot.skryptcore.api.hologram.Hologram;
//import net.skrypt.spigot.skryptcore.api.hologram.HologramAPI;
//import net.skrypt.spigot.skryptcore.api.hologram.task.HologramShowDuration;
//import org.bukkit.Bukkit;
//import org.bukkit.ChatColor;
//import org.bukkit.Location;
//import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
//import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
//import org.bukkit.entity.Player;
//
//import java.util.ArrayList;
//import java.util.ConcurrentModificationException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//public class Hologram_1_12_R1 implements Hologram {
//
//	// CONSTANTS
//	private final double LINE_HEIGHT = 0.25D;
//
//
//	// VARIABLES
//	private int id = 0;
//	private Location location;
//	private ArrayList<String> text;
//	private HashMap<Integer, EntityArmorStand> entities;
//	private ArrayList<Player> players;
//
//
//	// CONSTRUCTORS
//	public Hologram_1_12_R1(int id, Location location, ArrayList<String> text) {
//		this.id = id;
//		this.location = location;
//		this.text = text;
//		this.entities = new HashMap<>();
//		this.players = new ArrayList<>();
//		create();
//	}
//
//
//	// METHODS
//	@Override
//	public void setText(String... text) {
//		this.text.clear();
//		for(String line : text) {
//			this.text.add(ChatColor.translateAlternateColorCodes('&', line));
//		}
//	}
//
//	@Override
//	public void setText(ArrayList<String> text) {
//		this.text = text;
//	}
//
//	@Override
//	public void addText(String... text) {
//		for (String line : text) {
//			this.text.add(ChatColor.translateAlternateColorCodes('&', line));
//		}
//	}
//
//	@Override
//	public void addText(ArrayList<String> text) {
//		this.text.addAll(text.stream().map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList()));
//	}
//
//	@Override
//	public void removeText(int... lines) {
//		for (int line : lines) {
//			if (line < this.text.size()) {
//				this.text.remove(line);
//			}
//		}
//	}
//
//	@Override
//	public void show() {
//		Bukkit.getOnlinePlayers().forEach(this::show);
//	}
//
//	@Override
//	public void show(float duration) {
//		for (Player player : Bukkit.getOnlinePlayers()) {
//			show(duration, player);
//		}
//	}
//
//	@Override
//	public void show(Player... players) {
//		show(0f, players);
//	}
//
//	@Override
//	public void show(float duration, Player... players) {
//		for (Player player : players) {
//			this.players.add(player);
//			for (Map.Entry<Integer, EntityArmorStand> entity : entities.entrySet()) {
//				PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(entity.getValue());
//				((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
//			}
//		}
//		HologramShowDuration task = new HologramShowDuration(this);
//		if (duration == 0f) Bukkit.getScheduler().scheduleSyncDelayedTask(SkryptCoreAPI.getInstance(), task);
//		else Bukkit.getScheduler().scheduleSyncDelayedTask(SkryptCoreAPI.getInstance(), task, (long)(duration*20));
//	}
//
//	@Override
//	public void hide() {
//		for (Player player : players) {
//			hide(player);
//		}
//	}
//
//	@Override
//	public void hide(Player... players) {
//		for (Player player : players) {
//			if (this.players.contains(player)) {
//				for (Map.Entry<Integer, EntityArmorStand> entity : entities.entrySet()) {
//					PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(entity.getValue().getId());
//					((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
//				}
//				this.players.remove(player);
//			}
//		}
//	}
//
//	@Override
//	public int getID() {
//		return id;
//	}
//
//	@Override
//	public void delete() {
//		try {
//			hide();
//			HologramAPI.get().remove(id);
//		} catch (ConcurrentModificationException e) {
//
//		}
//	}
//
//	private void create() {
//		int lineNo = 0;
//		for (String line : text) {
//			EntityArmorStand entity = new EntityArmorStand(((CraftWorld)location.getWorld()).getHandle(), location.getX(), location.getY(), location.getZ());
//			entity.setCustomName(line);
//			entity.setCustomNameVisible(true);
//			entity.setInvisible(true);
//			entity.setNoGravity(true);
//			entities.put(lineNo, entity);
//			location.subtract(0d, LINE_HEIGHT, 0d);
//			lineNo++;
//		}
//		location.add(0d, lineNo*LINE_HEIGHT, 0d); // Resets the location to match the position of the first line
//	}
//
//}
