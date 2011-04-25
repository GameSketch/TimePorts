package net.gamesketch.bukkit.timeports;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class TimePortsPListener extends PlayerListener {

	public void onPlayerInteract(PlayerInteractEvent event) {
		//TODO: Remove this if it isn't needed for other stuff
	}
	public void onPlayerJoin(PlayerJoinEvent event) {
		TimePortsCore.pdata.add(new PlayerData(event.getPlayer()));
	}
	public void onPlayerQuit(PlayerQuitEvent event) {
		TimePortsCore.pdata.remove(TimePortsCore.getPlayerData(event.getPlayer().getName()));
	}
	
}
