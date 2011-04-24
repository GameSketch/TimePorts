package net.gamesketch.bukkit.timeports;

import org.bukkit.Location;
import org.bukkit.World;

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
	
}
