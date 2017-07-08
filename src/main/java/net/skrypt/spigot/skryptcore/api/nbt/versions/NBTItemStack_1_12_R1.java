/*
 * Copyright (c) 2016 Skrypt - All Rights Reserved.
 *
 * Web: www.skrypt.net
 * E-Mail: contact@skrypt.net
 *
 * Illegal modification and redistribution is not allowed.
 */

package net.skrypt.spigot.skryptcore.api.nbt.versions;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.skrypt.spigot.skryptcore.api.nbt.NBTItemStack;
import net.skrypt.spigot.skryptcore.api.nbt.exceptions.NBTException;
import net.skrypt.spigot.skryptcore.api.nbt.exceptions.NoNBTKeyException;
import net.skrypt.spigot.skryptcore.api.nbt.exceptions.NoNBTTagsException;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NBTItemStack_1_12_R1 implements NBTItemStack {

	private ItemStack base;
	private net.minecraft.server.v1_12_R1.ItemStack item;
	private NBTTagCompound tag;

	public NBTItemStack_1_12_R1(ItemStack item) {
		this.base = item;
	}

	public ItemStack toBukkit() {
		return this.base;
	}

	@Override
	public void setByte(String key, byte value) {
		getTag();
		tag.setByte(key, value);
		setTag();
	}
	@Override
	public void setByteArray(String key, byte[] value) {
		getTag();
		tag.setByteArray(key, value);
		setTag();
	}

	@Override
	public void setInt(String key, int value) {
		getTag();
		tag.setInt(key, value);
		setTag();
	}

	@Override
	public void setIntArray(String key, int[] value) {
		getTag();
		tag.setIntArray(key, value);
		setTag();
	}

	@Override
	public void setFloat(String key, float value) {
		getTag();
		tag.setFloat(key, value);
		setTag();
	}

	@Override
	public void setDouble(String key, double value) {
		getTag();
		tag.setDouble(key, value);
		setTag();
	}

	@Override
	public void setShort(String key, short value) {
		getTag();
		tag.setShort(key, value);
		setTag();
	}

	@Override
	public void setLong(String key, long value) {
		getTag();
		tag.setLong(key, value);
		setTag();
	}

	@Override
	public void setString(String key, String value) {
		getTag();
		tag.setString(key, value);
		setTag();
	}

	@Override
	public void setBoolean(String key, boolean value) {
		getTag();
		tag.setBoolean(key, value);
		setTag();
	}

	@Override
	public byte getByte(String key) throws NoNBTTagsException, NoNBTKeyException {
		getTag();
		if (item.getTag() == null)
			throw new NoNBTTagsException();
		
		if(item.getTag().hasKey(key)) return item.getTag().getByte(key);
		throw new NoNBTKeyException();
	}


	@Override
	public byte[] getByteArray(String key) throws NBTException {
		getTag();
		if (item.getTag() == null)
			throw new NoNBTTagsException();
		
		if(item.getTag().hasKey(key)) return item.getTag().getByteArray(key);
		throw new NoNBTKeyException();
	}

	@Override
	public int getInt(String key) throws NBTException {
		getTag();
		if (item.getTag() == null)
			throw new NoNBTTagsException();
		
		if(item.getTag().hasKey(key)) return item.getTag().getInt(key);
		throw new NoNBTKeyException();
	}

	@Override
	public int[] getIntArray(String key) throws NBTException {
		getTag();
		if (item.getTag() == null)
			throw new NoNBTTagsException();
		
		if(item.getTag().hasKey(key)) return item.getTag().getIntArray(key);
		throw new NoNBTKeyException();
	}

	@Override
	public float getFloat(String key) throws NBTException {
		getTag();
		if (item.getTag() == null)
			throw new NoNBTTagsException();
		
		if(item.getTag().hasKey(key)) return item.getTag().getFloat(key);
		throw new NoNBTKeyException();
	}

	@Override
	public double getDouble(String key) throws NBTException {
		getTag();
		if (item.getTag() == null)
			throw new NoNBTTagsException();
		
		if(item.getTag().hasKey(key)) return item.getTag().getDouble(key);
		throw new NoNBTKeyException();
	}

	@Override
	public short getShort(String key) throws NBTException {
		getTag();
		if (item.getTag() == null)
			throw new NoNBTTagsException();
		
		if(item.getTag().hasKey(key)) return item.getTag().getShort(key);
		throw new NoNBTKeyException();
	}

	@Override
	public long getLong(String key) throws NBTException {
		getTag();
		if (item.getTag() == null)
			throw new NoNBTTagsException();
		
		if(item.getTag().hasKey(key)) return item.getTag().getLong(key);
		throw new NoNBTKeyException();
	}

	@Override
	public String getString(String key) throws NBTException {
		getTag();
		if (item.getTag() == null)
			throw new NoNBTTagsException();
		
		if(item.getTag().hasKey(key)) return item.getTag().getString(key);
		throw new NoNBTKeyException();
	}

	@Override
	public boolean getBoolean(String key) throws NBTException {
		getTag();
		if (item.getTag() == null)
			throw new NoNBTTagsException();
			
		if(item.getTag().hasKey(key)) return item.getTag().getBoolean(key);
		throw new NoNBTKeyException();
	}
	
	@Override
	public boolean hasNBT() {
		getTag();
		if (item.getTag() == null)
			return false;
		
		return true;
	}

	private void getTag() {
		item = CraftItemStack.asNMSCopy(base);
		tag = item.getTag();
		if (tag == null) tag = new NBTTagCompound();
	}

	private void setTag() {
		item.setTag(tag);
		base = CraftItemStack.asBukkitCopy(item);
	}
	
}
