package net.gamesketch.bukkit.timeports;

import java.util.LinkedList;
import java.util.List;

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
//---selecting tools
//---using add/remove
//
//-make a data saver/loader with proper worlds (multiworlds support)
//
//-more required stuff

public class TimePortsCore extends JavaPlugin {
	PlayerListener TimePortsPListener = new TimePortsPListener();
	public static List<PlayerData> pdata;
	public static List<Teleporter> teleports;
	public static Server server;
	
	
    public void onDisable() {
        //PluginManager pm = getServer().getPluginManager();
    }

    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        PluginDescriptionFile pdfFile = this.getDescription();
        
        server = getServer();
        
        pdata = new LinkedList<PlayerData>();
        teleports = new LinkedList<Teleporter>();
        
        
        pm.registerEvent(Event.Type.PLAYER_INTERACT, TimePortsPListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_MOVE, TimePortsPListener, Event.Priority.Normal, this);
        
        
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
        		if (pos1 == null || pos2 == null) { player.sendMessage("Define the area using Iron_Chestplate first."); }
        		Teleporter teleporter = new Teleporter(pos1.getLocation(),pos2.getLocation(),name);
        		teleporter.setSpeed(speed);
        		teleports.add(teleporter);
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

}