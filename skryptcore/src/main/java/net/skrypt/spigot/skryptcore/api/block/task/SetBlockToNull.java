package net.skrypt.spigot.skryptcore.api.block.task;

import org.bukkit.block.Block;

/**
 * @author Skrypt
 */
public class SetBlockToNull implements Runnable {
	
	private Block block;
	
	public SetBlockToNull(Block block) {
		this.block = block;
	}
	
	@Override
	public void run() {
		block = null;
	}
}
