/*
 * Copyright (c) 2016 Skrypt - All Rights Reserved.
 *
 * Web: www.skrypt.net
 * E-Mail: contact@skrypt.net
 *
 * Illegal modification and redistribution is not allowed.
 */

package net.skrypt.spigot.skryptcore.api.nbt;

import net.skrypt.spigot.skryptcore.API;
import net.skrypt.spigot.skryptcore.api.enums.ServerVersion;
import net.skrypt.spigot.skryptcore.api.exceptions.OutdatedVersionException;
import net.skrypt.spigot.skryptcore.api.exceptions.UnsupportedVersionException;
import net.skrypt.spigot.skryptcore.api.nbt.exceptions.NBTException;
import net.skrypt.spigot.skryptcore.api.nbt.versions.NBTItemStack_1_12_R1;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface NBTItemStack {

	static NBTItemStack fromBukkit(Material material) throws UnsupportedVersionException, OutdatedVersionException {
		return fromBukkit(material, 1);
	}
	static NBTItemStack fromBukkit(Material material, int amount) throws UnsupportedVersionException, OutdatedVersionException {
		return fromBukkit(material, amount, (short)0);
	}
	static NBTItemStack fromBukkit(Material material, int amount, short durability) throws UnsupportedVersionException, OutdatedVersionException {
		return fromBukkit(new ItemStack(material, amount, durability));
	}

	static NBTItemStack fromBukkit(ItemStack item) throws UnsupportedVersionException, OutdatedVersionException {
		ServerVersion version = API.getVersion();
		
		// Return the class based on the server version
		if (version == ServerVersion.V_1_12_R1)
			return new NBTItemStack_1_12_R1(item);

		// If server version enum was loaded - so the plugin know's the version, but it didn't
		// find any class above (that's why this part of code was reached)
		// it means that that feature is not available for the server's minecraft version and required an update
		if (version != null)
			throw new OutdatedVersionException();
		// Otherwise the server version is not supported at all.
		else
			throw new UnsupportedVersionException();
	}

	ItemStack toBukkit();

	void setByte(String key, byte value);
	void setByteArray(String key, byte[] value);
	void setInt(String key, int value);
	void setIntArray(String key, int[] value);
	void setFloat(String key, float value);
	void setDouble(String key, double value);
	void setShort(String key, short value);
	void setLong(String key, long value);
	void setString(String key, String value);
	void setBoolean(String key, boolean value);

	byte getByte(String key) throws NBTException;
	byte[] getByteArray(String key) throws NBTException;
	int getInt(String key) throws NBTException;
	int[] getIntArray(String key) throws NBTException;
	float getFloat(String key) throws NBTException;
	double getDouble(String key) throws NBTException;
	short getShort(String key) throws NBTException;
	long getLong(String key) throws NBTException;
	String getString(String key) throws NBTException;
	boolean getBoolean(String key) throws NBTException;
	
	boolean contains(String key) throws NBTException;
	boolean hasNBT();
}
