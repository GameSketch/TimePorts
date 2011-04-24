package net.gamesketch.bukkit.timeports;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

public class TimePortsPListener extends PlayerListener {

	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getClickedBlock() == null) { System.out.println("Error in bukkit"); }
		//halt non-ops / non-right-clicks / non-tools
		if (!player.isOp() || !player.getItemInHand().getType().equals(Material.IRON_CHESTPLATE)) { return; }
		if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) { 
			PlayerData p = TimePortsCore.getPlayerData(event.getPlayer().getName());
			p.setPos1(event.getClickedBlock());
			p.getPlayer().sendMessage(ChatColor.AQUA + "Selected pos1 (" + p.getPos1().getX() + "," + p.getPos1().getY() + "," + p.getPos1().getZ() + ") for TimePorts");
		}
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) { 
			PlayerData p = TimePortsCore.getPlayerData(event.getPlayer().getName());
			p.setPos2(event.getClickedBlock());
			p.getPlayer().sendMessage(ChatColor.AQUA + "Selected pos2 (" + p.getPos1().getX() + "," + p.getPos1().getY() + "," + p.getPos1().getZ() + ") for TimePorts");
		}
	}
	
}
