package net.skrypt.spigot.skryptcore.api.title;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Skrypt on 18.05.2016.
 */
public class Title {

	private int id;

	private String title    = "Text not configured";
	private String subtitle = "";

	private float duration  = 5f;
	private float fadeIn    = 1f;
	private float fadeOut   = 1f;

	protected Title(int _id, String _title, String _subtitle, float _duration, float _fadeIn, float _fadeOut) {
		id = _id;
		if (_title != null) title = _title;
		if (_subtitle != null) subtitle = _subtitle;
		if (_duration != -1f) duration = _duration;
		if (_fadeIn != -1f) fadeIn =_fadeIn;
		if (_fadeOut != -1f) fadeOut = _fadeOut;
	}

	public void sendToPlayer() {
		Collection<? extends Player> players = Bukkit.getOnlinePlayers();
		players.forEach(this::sendToPlayer);
	}

	public void sendToPlayer(Player... players) {
		List<World> worlds = new ArrayList<>();
		for (Player player : players) {
			if (!worlds.contains(player.getWorld())) {
				if (player.getWorld().getGameRuleValue("sendCommandFeedback") == "true") {
					player.getWorld().setGameRuleValue("sendCommandFeedback", "false");
					worlds.add(player.getWorld());
				}
			}
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() + " times " +
					fadeIn + " " + duration + " " + fadeOut);
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() + " title " +
					"{\"text\":\"" + title + "\"}");
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() + " subtitle " +
					"{\"text\":\"" + subtitle + "\"}");
		}
		for (World world : worlds) {
			world.setGameRuleValue("sendCommandFeedback", "true");
		}
	}

	public void clear(Player... players) {
		for (Player player : players) {
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() + " clear");
		}
	}

	public int getId() { return id;}
	public String getTitle() { return title;}
	public String getSubtitle() { return subtitle;}
	public float getDuration() { return duration;}
	public float getFadeInDuration() { return fadeIn;}
	public float getFadeOutDuration() { return fadeOut;}
}
