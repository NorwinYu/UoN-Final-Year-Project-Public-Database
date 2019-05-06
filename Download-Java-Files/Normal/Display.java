package display;

import java.util.Scanner;

import characters.Entity;
import characters.Player;
import item.Item;
import navigation.Decor;
import navigation.Door;
import navigation.Room;
import navigation.RoomFeature;
import navigation.Staircase;
import navigation.Wall;

public class Display {

	static Scanner scan = new Scanner(System.in);

	public static void print(String str) {
		System.out.print(str);
	}

	public static void println(String str) {
		System.out.println(str);
	}

	public static String input(String str) {
		println(str);
		return scan.nextLine();
	}

	public static int inputInt(String str) {
		println(str);
		String in;
		int out = 0;;
		
		in = scan.nextLine();
		try {
			out = Integer.parseInt(in);
		} catch (NumberFormatException e) {

			return inputInt(str);
		}
		return out;
	}

	public static void print(Entity ent) {
		String name = ent.getName();
		char firstletter = name.toLowerCase().charAt(0);
		if (firstletter == 'a' || firstletter == 'e' || firstletter == 'i' || firstletter == 'o' || firstletter == 'u') {
			println("There is an " + name);
		} else {
			println("There is a " + name);
		}
	}

	public static void print(Player player) {
		println(player.toString());
	}

	public static void print(Item item) {
		println(item.getName());
	}

	public static void print(Room room) {
		println("You are in a room");
		print("The North side is ");
		print(room.getNorth());

		print("The East side is ");
		print(room.getEast());

		print("The South side is ");
		print(room.getSouth());

		print("The West side is ");
		print(room.getWest());

	}

	public static void print(RoomFeature feature) {
		if (feature instanceof Door) {
			print((Door)feature);
		}
		if (feature instanceof Decor) {
			print((Decor)feature);
		}
		if (feature instanceof Wall) {
			print((Wall)feature);
		}
		if (feature instanceof Staircase) {
			print((Staircase)feature);
		}

	}

	public static void print(Door door) {
		println("Is a " + ((door.isLocked())? "locked" : "" ) + door.getMaterial() + " door");
	}
	public static void print(Decor decor) {
		println("Is a " + decor.getDescription());
	}
	public static void print(Wall wall) {
		println("wall");
	}
	public static void print(Staircase stair) {
		println("Staircase to another floor!");
	}
	
	public static void debug(String str) {
		println("Debug: " + str);
	}
}
