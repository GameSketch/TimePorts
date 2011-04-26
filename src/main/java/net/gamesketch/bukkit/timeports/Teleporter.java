package net.gamesketch.bukkit.timeports;

import java.util.Timer;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Teleporter {
	//zone markers
	int x1;
	int y1;
	int z1;
	int x2;
	int y2;
	int z2;
	World world;
	
	//teleport info
	String name;
	Location destinyend;
	Location destinymiddle;
	double speed;
	
	//timer
	Timer midtimer = new Timer();
	Timer endtimer = new Timer();
	
	
	
	
	
	/*
	 * Init's
	 */
	public Teleporter(Location loc1, Location loc2, String name) {
		if (loc1.getWorld().getId() != loc2.getWorld().getId()) { return; }
		this.x1 = loc1.getBlockX();
		this.x2 = loc2.getBlockX();
		this.y1 = loc1.getBlockY();
		this.y2 = loc2.getBlockY();
		this.z1 = loc1.getBlockZ();
		this.z2 = loc2.getBlockZ();
		this.world = loc1.getWorld();
		this.name = name;
	}
	public Teleporter(String name, int speed, int x1, int y1, int z1, int x2, int y2, int z2, World world, Location middle, Location end) {
		this.name = name;
		this.speed = speed;
		this.x1 = x1;
		this.y1 = y1;
		this.z1 = z1;
		this.x2 = x2;
		this.y2 = y2;
		this.z2 = z2;
		this.world = world;
		this.destinymiddle = middle;
		this.destinyend = end;
	}
	/*
	 * Value getters
	 */
	public int getX1() {
		return x1;
	}
	public int getX2() {
		return x2;
	}
	public int getY1() {
		return y1;
	}
	public int getY2() {
		return y2;
	}
	public int getZ1() {
		return z1;
	}
	public int getZ2() {
		return z2;
	}
	public Location getPoint1() {
		return new Location(world, x1, y1, z1);
	}
	public Location getPoint2() {
		return new Location(world, x2, y2, z2);
	}
	public World getWorld() {
		return world;
	}
	public Location getDestinationEnd() {
		return destinyend;
	}
	public Location getDestinationMiddle() {
		return destinymiddle;
	}
	public double getSpeed() {
		return speed;
	}
	public String getName() {
		return name;
	}
	/*
	 * Value getters
	 */
	public void setPoint1(Location l) {
		if (l.getWorld().getId() != world.getId()) { return; }
		this.x1 = l.getBlockX();
		this.y1 = l.getBlockY();
		this.z1 = l.getBlockZ();
	}
	public void setPoint2(Location l) {
		if (l.getWorld().getId() != world.getId()) { return; }
		this.x2 = l.getBlockX();
		this.y2 = l.getBlockY();
		this.z2 = l.getBlockZ();
	}
	public void setWorld(World w) {
		this.world = w;
	}
	public void setDestinationEnd(Location l) {
		this.destinyend = l;
	}
	public void setDestinationMiddle(Location l) {
		this.destinymiddle = l;
	}
	public void setSpeed(int speed) {
		this.speed = speed/10;
	}
	public void setName(String s) {
		this.name = s;
	}
	
	public long getTime() {
		if (destinymiddle != null && destinyend != null && speed != 0) {
			Vector v1 = destinymiddle.toVector();
			Vector v2 = destinyend.toVector();
			double distance = v1.distance(v2);
			long time = (int)distance / (int)speed * 1000;
			return time;
		}
		return -1;
	}
	public boolean teleport(Player p) {
		//TODO check if teleporter is valid (all locations)
		//TODO add timers for teleporting
		//TODO use getTime for milliseconds
		
		//note: start = 10000
		//note: mid-air = getTime + 10000
		return false;
	}
	
}
