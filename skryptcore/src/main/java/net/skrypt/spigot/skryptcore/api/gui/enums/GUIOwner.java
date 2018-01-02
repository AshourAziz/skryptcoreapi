package net.skrypt.spigot.skryptcore.api.gui.enums;


/**
 * @author Skrypt
 */
public enum GUIOwner {
	PLAYER(0),
	BLOCK(1),
	ENTITY(2);
	
	private int id;
	
	GUIOwner(int id) {
		this.id = id;
	}
}
