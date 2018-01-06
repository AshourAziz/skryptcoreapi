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

public class LocalizedMessage {

	private Locale locale;
	private String key;

	private LocalizationAPI api;

	protected LocalizedMessage(Locale _locale, String _key, LocalizationAPI api) {
		this.locale = _locale;
		this.key = _key;
		this.api = api;
	}

	public String toString() {
		return this.api.getConfigurations().get(this.locale).getFileConfiguration().getString(this.key);
	}

}
