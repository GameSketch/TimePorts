package net.gamesketch.bukkit.timeports;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class PlayerData {
	Block sel1 = null;
	Block sel2 = null;
	Player player;
	
	public PlayerData(Player player) { this.player = player; }
	
	public Block getPos1() {
		return sel1;
	}
	public Block getPos2() {
		return sel2;
	}
	public void setPos1(Block b) {
		this.sel1 = b;
	}
	public void setPos2(Block b) {
		this.sel2 = b;
	}
	public Player getPlayer() {
		return player;
	}
	
}
