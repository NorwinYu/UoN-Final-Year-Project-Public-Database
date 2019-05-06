package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import characters.AttackSpell;
import characters.HealingSpell;
import characters.MagicSpell;
import characters.Player;
import display.Display;

import static characters.AttackSpell.fireBall;
import static characters.AttackSpell.testAttack;
import static characters.HealingSpell.lightHealing;
import static characters.HealingSpell.testHealing;
import static characters.Player.*;
import static item.Armor.ruggedArmor;
import static item.Armor.ruggedHelmet;
import static item.Armor.ruggedLeggings;
import static item.Armor.ruggedShield;
import static item.Armor.testArmor;
import static item.Armor.testHelmet;
import static item.Armor.testLeggings;
import static item.Armor.testShield;
import static item.Potion.healthPotion;
import static item.Weapon.ruggedSword;
import static item.Weapon.testSword;

import item.Armor;
import item.Potion;
import item.Weapon;
import navigation.Door;
import navigation.Dungeon;
import navigation.Floor;
import navigation.Room;
import navigation.Wall;

import instance.*;
import characters.*;
import item.*;
import java.util.ArrayList;

import static display.Display.*;

public class MainGame {

	//Dungeon dungeon;
	//Entity player;
	Scanner scan;

	int ENEMY_WAIT = 1;
	int QUIT_MOVE = 0;

	//This is my random comment somewhere
	public Player player1;
	public Dungeon dungeon;

	public static void main(String[] args) {
		System.setOut(System.out);
		// Print Logo
		try {
			Scanner input;
			input = new Scanner(new File("src/data/DUNG ASCII Logo.txt"));
			while (input.hasNextLine()) {
				println(input.nextLine());
			}

		} catch (FileNotFoundException e) {
			println("Error Loading Logo");
		}
		new MainGame();
	}

	public MainGame() {
		player1 = buildCharacter();


		dungeon = new Dungeon("Scary Dungeon", 1);   //Quick and dirty dungeon build, all wall and door objects are the same which would mess up locking/unlocking, in the doors case
		dungeon.setFloor(0, new Floor(2,2));
		Door door = new Door(false, "wood");
		Wall wall = new Wall();

		dungeon.getFloor(0).setRoom(new Room(wall, door, door, wall),  0, 0);
		dungeon.getFloor(0).setRoom(new Room(wall, wall, door, door),  1, 0);
		dungeon.getFloor(0).setRoom(new Room(door, door, wall, wall),  0, 1);
		dungeon.getFloor(0).setRoom(new Room(door, wall, wall, door),  1, 1);

		player1.setXPosition(0);
		player1.setYPosition(0);


		Entity giantRoach = new Entity("Giant Roach", 40, 0, 3, 6, 1, 2, 1, true); // Make Enemy
		Weapon bite = new Weapon("bite", 0, 0, 0, 3, 4, 0, false); // Make Weapon for Enemy
		giantRoach.setEquippedItems(WEAPON, bite); // Give Weapon to Enemy

		Entity giantMouse = new Entity("Giant Mouse", 40, 0, 3, 6, 1, 2, 1, true); // Make Enemy
		giantMouse.setEquippedItems(WEAPON, bite); // Give Weapon to Enemy
		


		//dungeon.getFloor(0).getRoom(1, 1).addInstances(testInstance); // Place Instance Somewhere.
		dungeon.getFloor(0).getRoom(1, 1).addEntities(giantRoach, giantMouse);


		//For Testing
		//player1 = new Player("", 50,50,50,50,50,50,50,2);


		//dungeon = new Dungeon();


		//String name = getName();
		//player = new Entity(name, map);



		//FOR TESTING PLAYER SPELL INVENTORY
		/*player1.setEquippedSpells(0, fireBall);
		player1.setEquippedSpells(1, lightHealing);
		player1.setPlayerSpells(fireBall);
		player1.setPlayerSpells(lightHealing);
		player1.setPlayerSpells(testAttack);
		player1.setPlayerSpells(testHealing);
		player1.changeEquippedSpells();*/

		/*player1.setEquippedSpells(0, fireBall);
		player1.setEquippedSpells(1, lightHealing);
		player1.displayEquippedSpells();*/


		//FOR TESTING PLAYER ITEM INVENTORY
		/*player1.setEquippedItems(ruggedHelmet.getEquippedItemSlot(), ruggedHelmet);
		player1.setEquippedItems(ruggedArmor.getEquippedItemSlot(), ruggedArmor);
		player1.setEquippedItems(ruggedLeggings.getEquippedItemSlot(), ruggedLeggings);
		player1.setEquippedItems(ruggedShield.getEquippedItemSlot(), ruggedShield);
		player1.setEquippedItems(ruggedSword.getEquippedItemSlot(), ruggedSword);
		player1.setPlayerBlocking(player1.getEquippedItems());
		player1.setPlayerDamage(player1.getEquippedItems());		
		player1.setPlayerInventory(healthPotion);
		healthPotion.setQuantity(3);*/
		player1.setInventory(testHelmet);
		player1.setInventory(testArmor);
		player1.setInventory(testLeggings);
		player1.setInventory(testShield);
		player1.setInventory(testSword);
		//player1.changeEquippedItems();

		boolean play = true;


		//Entity enemy;
		//int move;
		Room currentRoom;
		//boolean isEnemyAlive;

		while (play) {
			//print(player.getPosition());

			currentRoom = dungeon.getFloor(0).getRoom(player1.getXPosition(), player1.getYPosition());

			// Activate Instances.
			//This seems like best location for now, just after you have entered the room.
//			for (Instance i: currentRoom.getInstances()) {
//
//				i.addEntity(player1, 0);
//				if (i.checkActive()) {
//					i.launch();
//					if (player1.isAlive() == false) {
//						print(player1.getName() + " is dead. \nGame Over\n");
//						play = false;
//						break;
//					}
//				}
//			}
			
			Instance combat = new Combat();
			combat.addEntity(player1, 0);
			
			for (Entity i: currentRoom.getEnties()) {
				combat.addEntity(i, 1);	
			}
			
			if (combat.checkActive()) {
				combat.launch();
				if (player1.isAlive() == false) {
					print(player1.getName() + " is dead. \nGame Over\n");
					play = false;
					break;
				}
			}
			
			if (!player1.isAlive()) { 
				play = false;
				break;
			}

			println("Your options are:");

			if (currentRoom.getNorth() instanceof Door) {
				println("You can go north");
			}
			if (currentRoom.getEast() instanceof Door) {
				println("You can go east");
			}
			if (currentRoom.getSouth() instanceof Door) {
				println("You can go south");
			}
			if (currentRoom.getWest() instanceof Door) {
				println("You can go west");
			}
			println("You can quit");
			println("Change equipped items.");


			String input = input("Where would you like to go?");

			if (input.toLowerCase().charAt(0)=='n') {
				player1.setYPosition(player1.getYPosition() - 1);
			}
			if (input.toLowerCase().charAt(0)=='e') {
				player1.setXPosition(player1.getXPosition() + 1);
			}
			if (input.toLowerCase().charAt(0)=='s') {
				player1.setYPosition(player1.getYPosition() + 1);
			}
			if (input.toLowerCase().charAt(0)=='w') {
				player1.setXPosition(player1.getXPosition() - 1);
			}
			if (input.toLowerCase().charAt(0)=='q') {
				play = false;
			}
			
			if (input.toLowerCase().charAt(0)=='c') {
				player1.changeEquippedItems();
			}
			//enemy = getEnemy();

			//if (enemy == null) { //Move mode

			//getClass().int move = player.getMove();
			//player.Move(move);

			/*} else { //Fight Mode

				Thread.sleep(ENEMY_WAIT);

				isEnemyAlive = true;

				while(isEnemyAlive) {

					enemy.display();
					move =  player.getAttack();
					enemy.attack(move);

					move = enemy.getAttack();
					player.attack(move);
				}
			} */

		}
		Display.println("Game Over");
	}


}//End Class MainGame
