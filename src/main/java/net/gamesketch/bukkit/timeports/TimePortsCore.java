package net.gamesketch.bukkit.timeports;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

//TODO
//-fix all the nullpointers
//
//-make a data saver/loader with proper worlds (multiworlds support)
// Probably this format: (it needs world/yaw support:
//
// name:speed:world:x;y;z:x;y;z:x1;y1;z1;world1;yaw1:x2;y2;z2;world2;yaw2
//
//-more required stuff

public class TimePortsCore extends JavaPlugin {
	PlayerListener TimePortsPListener = new TimePortsPListener();
	public static List<PlayerData> pdata;
	public static List<Teleporter> teleports;
	public static Server server;
	
	
    public void onDisable() {
        //PluginManager pm = getServer().getPluginManager();
    	//Data.Save();
    }

    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        PluginDescriptionFile pdfFile = this.getDescription();
        
        server = getServer();
        
        pdata = new LinkedList<PlayerData>();
        teleports = new LinkedList<Teleporter>();
        
        for (Player p : getServer().getOnlinePlayers()) {
        	pdata.add(new PlayerData(p));
        }
        
        //Data.Load();
        
        pm.registerEvent(Event.Type.PLAYER_INTERACT, TimePortsPListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_MOVE, TimePortsPListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_JOIN, TimePortsPListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_QUIT, TimePortsPListener, Event.Priority.Normal, this);
        
        
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        String commandName = command.getName().toLowerCase();
        Player player = (Player)sender;

        if (commandName.equals("timeport")) {
        	if (args.length <= 0) { return false; }
        	if (args[0] == null) { return false; }
        	
        	if (args[0].equals("add")) { //add <name> <speed> 
        		if (args.length <= 2) { return false; }
        		if (args[1] == null || args[2] == null) { return false; }
        		String name = "";
        		int speed = 1;
        		try {
        			name = args[1];
        			speed = Integer.parseInt(args[2]);
        		} catch (NumberFormatException e) { return false; }
        		PlayerData pos = getPlayerData(player.getName());
        		if (pos == null) { return false; }
        		Block pos1 = pos.getPos1();
        		Block pos2 = pos.getPos2();
        		if (pos1 == null || pos2 == null) { player.sendMessage("Define the area using para's 'add <1/2>' first."); return true; }
        		Teleporter teleporter = new Teleporter(pos1.getLocation(),pos2.getLocation(),name);
        		teleporter.setSpeed(speed);
        		teleports.add(teleporter);
        		player.sendMessage(ChatColor.AQUA + "Teleporter created, define midpoints now");
        		return true;
        		
        		
        	}
        	if (args[0].equals("edit")) { //edit <name> <midpos/endpos/bounds/name/speed>
        		if (args.length <= 2) { return false; }
        		if (args[1] == null) { return false; }
        		if (args[2] == null) { return false; }
    			Teleporter tp = getTeleporter(args[1]);
    			if (tp == null) { player.sendMessage("No Timeport found with that name."); return false; }
        		
        		if (args[2] == "midpos") { //edit <name> midpos
        			tp.setDestinationMiddle(player.getLocation());
        			player.sendMessage("Done.");
        			return true;
        		}
        		if (args[2] == "endpos") { //edit <name> endpos
        			tp.setDestinationEnd(player.getLocation());
        			player.sendMessage("Done.");
        			return true;
        		}
        		if (args[2] == "bounds") { //edit <name> bounds
            		Block pos1 = getPlayerData(player.getName()).getPos1();
            		Block pos2 = getPlayerData(player.getName()).getPos2();
            		if (pos1 == null || pos2 == null) { player.sendMessage("Define the area using Iron_Chestplate first."); }
            		
            		tp.setPoint1(pos1.getLocation());
            		tp.setPoint2(pos2.getLocation());
            		player.sendMessage("Done.");
            		return true;
        		}
        		
        		if (args[2] == "name") { //edit <name> name <newname>
        			tp.setName(args[3]);
        			player.sendMessage("Done.");
        			return true;
        		}
        		if (args[2] == "speed") { //edit <name> speed <newspeed>
        			try { tp.setSpeed(Integer.parseInt(args[3])); }
        			catch (NumberFormatException e) { return false; }
        			player.sendMessage("Done.");
        			return true;
        		}
        		
        	}
        	if (args[0].equals("remove")) {
        		if (args.length <= 1) { return false; }
        		if (args[1] == null) { return false; }
    			Teleporter tp = getTeleporter(args[1]);
    			if (tp == null) { player.sendMessage("No Timeport found with that name."); return false; }
        		
    			teleports.remove(tp);
    			player.sendMessage("Teleporter " + args[1] + "succesfully removed");
    			return true;
        	}
        	if (args[0].equals("sel")) {
        		if (args.length <= 1) { return false; }
        		if (args[1] == null) { return false; }
        		if (args[1].equals("1")) { getPlayerData(player.getName()).setPos1(player.getLocation().getBlock()); 
        		player.sendMessage(ChatColor.AQUA + "Selected pos 1 at your position"); return true; }
        		if (args[1].equals("2")) { getPlayerData(player.getName()).setPos2(player.getLocation().getBlock()); 
        		player.sendMessage(ChatColor.AQUA + "Selected pos 2 at your position"); return true; }
        		return false;
        	}
        	if (args[0].equals("list")) {
        		if (teleports.isEmpty()) { return true; }
        		StringBuilder build = new StringBuilder();
        		for (Teleporter t : teleports) {
        			build.append(t.getName()).append(" ");
        		}
        		player.sendMessage(build.substring(0,build.length() - 1));
        	}
        }
        return true;
    }
    public static PlayerData getPlayerData(String s) {
    	for (PlayerData p : pdata) {
    		
    		if (p.getPlayer().getName().equals(server.getPlayer(s).getName())) {
    			return p;
    		}
    	}
    	return null;
    }
    public static Teleporter getTeleporter(String s) {
    	for (Teleporter t : teleports) {
    		if (t.getName().equals(s)) { return t; }
    	}
    	return null;
    }
    public static Teleporter isInTeleporter(Player p) {
    	for (Teleporter t : teleports) {
    		Location ploc = p.getLocation();
    		boolean isX = false, isY = false, isZ = false;
    		int px = ploc.getBlockX(), tx1 = t.getX1(), tx2 = t.getX2();
    		int py = ploc.getBlockY(), ty1 = t.getY1(), ty2 = t.getY2();
    		int pz = ploc.getBlockZ(), tz1 = t.getZ1(), tz2 = t.getZ2();
    		
    		if (tx1 <= tx2) { if (px >= tx1 && px <= tx2) { isX = true; } }
    		if (tx1 >= tx2) { if (px >= tx2 && px <= tx1) { isX = true; } }
    		
    		if (ty1 <= ty2) { if (py >= ty1 && py <= ty2) { isY = true; } }
    		if (ty1 >= ty2) { if (py >= ty2 && py <= ty1) { isY = true; } }
    		
    		if (tz1 <= tz2) { if (pz >= tz1 && pz <= tz2) { isZ = true; } }
    		if (tz1 >= tz2) { if (pz >= tz2 && pz <= tz1) { isZ = true; } }
    		
    		if (isX && isY && isZ) { return t; }
    		else { continue; }
    		
    		
    	}
    	return null;
    }

}