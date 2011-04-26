package net.gamesketch.bukkit.timeports;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.World;

public class Data {
	static File file = new File("plugins/TimePort/data");
	static File folder = new File("plugins/TimePort/");
	
	
	public static void Load() {
		TimePortsCore.teleports.clear();
		if (!file.exists()) {
			folder.mkdirs();
			try { file.createNewFile(); }
			catch (IOException e) { return; }
			return;
		}
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String s;

			while ((s = in.readLine()) != null) {
				if (s.split(":").length <= 6) { continue; }
				TimePortsCore.teleports.add(StringToData(s));
			}
			
		} catch (IOException e) { System.out.println("Unhandled exception IOException in Data.Load()"); e.printStackTrace(); }
		System.out.println("[TimePort] Loaded " + TimePortsCore.teleports.size() + "teleporters.");
	}
	public static void Save() {
		if (!file.exists()) {
			try {
				folder.mkdirs();
				file.createNewFile();
			} catch (IOException e) { System.out.println("Unable to create new file"); e.printStackTrace(); }
		}
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for (Teleporter t : TimePortsCore.teleports) {
				try {
					out.write(dataToString(t));
					out.newLine();
				} catch (IOException e) { }
			}
		} catch (IOException e) { System.out.println("Unhandled exception IOException in Data.Save()"); e.printStackTrace(); }
	}
	
	private static String dataToString(Teleporter t) {
		// name:speed:world:x;y;z:x;y;z:x1;y1;z1;world1;yaw1:x2;y2;z2;world2;yaw2
		StringBuilder s = new StringBuilder();
		s.append(t.getName()).append(":");              			//name:
		s.append((int)t.getSpeed()).append(":");        			//speed:
		s.append(t.getWorld().getName()).append(":");   			//world:
		
		s.append(t.getX1()).append(";");                			//x; \
		s.append(t.getY1()).append(";");                			//y; <> pos1
		s.append(t.getZ1()).append(":");                			//z: /
		
		s.append(t.getX2()).append(";");                			//x; \
		s.append(t.getY2()).append(";");                			//y; <> pos2
		s.append(t.getZ2()).append(":");                			//z: /
		
		s.append(t.getPoint1().getX()).append(";");     			//x1;
		s.append(t.getPoint1().getY()).append(";");     			//y1;
		s.append(t.getPoint1().getZ()).append(";");     			//z1;
		s.append(t.getPoint1().getWorld().getName()).append(";");   //world1;
		s.append((int)t.getPoint1().getYaw()).append(":");     			//yaw1;
		
		s.append(t.getPoint2().getX()).append(";");     			//x2;
		s.append(t.getPoint2().getY()).append(";");     			//y2;
		s.append(t.getPoint2().getZ()).append(";");     			//z2;
		s.append(t.getPoint2().getWorld().getName()).append(";");   //world2;
		s.append((int)t.getPoint2().getYaw());     						//yaw2;
		
		
		
		return s.toString();
	}
	public static Teleporter StringToData(String s) {
		String[] data = s.split(":");
		String name = data[0];										//name
		int speed = Integer.parseInt(data[1]);						//speed
		World world = TimePortsCore.server.getWorld(data[2]);   	//world
		//pos1
		String[] xyz1 = data[3].split(";");
		int x1 = Integer.parseInt(xyz1[0]);							//x1
		int y1 = Integer.parseInt(xyz1[1]);							//y1
		int z1 = Integer.parseInt(xyz1[2]);							//z1
		//pos2
		String[] xyz2 = data[4].split(";");
		int x2 = Integer.parseInt(xyz2[0]);							//x2
		int y2 = Integer.parseInt(xyz2[1]);							//y2
		int z2 = Integer.parseInt(xyz2[2]);							//z2
		//loc1
		String[] xyz3 = data[5].split(";");
		int x3 = Integer.parseInt(xyz3[0]);							//x3
		int y3 = Integer.parseInt(xyz3[1]);							//y3
		int z3 = Integer.parseInt(xyz3[2]);							//z3
		World world3 = TimePortsCore.server.getWorld(xyz3[3]);      //world3
		int yaw3 = Integer.parseInt(xyz3[4]);						//yaw3
		//loc1
		String[] xyz4 = data[6].split(";");
		int x4 = Integer.parseInt(xyz4[0]);							//x4
		int y4 = Integer.parseInt(xyz4[1]);							//y4
		int z4 = Integer.parseInt(xyz4[2]);							//z4
		World world4 = TimePortsCore.server.getWorld(xyz4[3]);      //world4
		int yaw4 = Integer.parseInt(xyz4[4]);						//yaw4
		
		Location loc1 = new Location(world3,x3,y3,z3,yaw3,0);       //loc1
		Location loc2 = new Location(world4,x4,y4,z4,yaw4,0);		//loc2
		return new Teleporter(name,speed,x1,y1,z1,x2,y2,z2,world,loc1,loc2);
		
		
		
		
	}
	
}
