package net.skrypt.spigot.skryptcore;

import net.skrypt.spigot.skryptcore.api.storage.flatfile.FlatfileAPI;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Skrypt on 25.02.2016.
 */
public class SkryptCoreAPI extends JavaPlugin {
	
	private static SkryptCoreAPI instance;

	private FlatfileAPI flatfile;
	
	@Override
	public void onEnable() {
		this.instance = this;
		API.enable();
		flatfile = new FlatfileAPI(this);
	}
	
	@Override
	public void onDisable() {
		this.instance = this;
		API.disable();
	}

	public static SkryptCoreAPI getInstance() {
		return instance;
	}
	
	public FlatfileAPI getFlatfileAPI() {
		return flatfile;
	}
}
