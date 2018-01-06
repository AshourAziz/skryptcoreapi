/*
 * Copyright (c) 2016 Skrypt - All Rights Reserved.
 *
 * Web: www.skrypt.net
 * E-Mail: contact@skrypt.net
 *
 * Illegal modification and redistribution is not allowed.
 */

package net.skrypt.spigot.skryptcore.api.localization;

import net.skrypt.spigot.skryptcore.api.localization.enums.Locale;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.HashMap;

public class LocalizationAPI {

	private Plugin plugin;
	private String filePath;
	private File dir;

	private Locale locale;

	private HashMap<Locale, LocalizationFile> configurations;

	public LocalizationAPI(Plugin plugin, String filePath) {
		this.plugin = plugin;
		this.filePath = filePath;
		this.configurations = new HashMap<>();
		this.locale = Locale.en_US;

		dir = new File(plugin.getDataFolder() + File.separator + filePath);
		dir.mkdirs();

		add();
	}

	public void setDefaultLocale(Locale locale) {
		this.locale = locale;
	}

	public void add(Locale locale, String resourcePath, String resourceName) {
		this.configurations.put(locale, new LocalizationFile(plugin, "locale.", resourcePath, resourceName, filePath, locale.name() + ".lang"));
	}

	private void add() {
		for (File file : dir.listFiles()) {
			if (file.isFile()) {
				String[] fileParts = file.getName().split("[.]");
				if (fileParts[1].equalsIgnoreCase("lang")) {
					Locale locale = Locale.fromString(file.getName().split("[.]")[0]);
					if (locale != null)
						if (!configurations.containsKey(locale))
							this.configurations.put(locale, new LocalizationFile(plugin, "locale." + locale.name(), null, "blank.yml", filePath, locale + ".lang"));
				}
			}
		}
	}

	public LocalizedMessage getLocalizedMessage(String key) {
		return getLocalizedMessage(locale, key);
	}

	public LocalizedMessage getLocalizedMessage(Locale locale, String key) {
		return new LocalizedMessage(locale, key, this);
	}

	protected HashMap<Locale, LocalizationFile> getConfigurations() {
		return this.configurations;
	}
}
