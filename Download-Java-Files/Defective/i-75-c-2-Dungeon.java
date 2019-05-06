/*
 * **************************************************************************************************************
 * Dungeon.java | Author: brandonlewis | Date: 2019.02.15 | Rev: 2019.03.01
 * This file is a class of the navigation package for the "Dungeons of UNG" text-based game.
 * **************************************************************************************************************
 */
package navigation;

public class Dungeon {
  
  private String name;
  private Floor[] floors;
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Constructor - Instantiates a single floor dungeon object with the specified name, using the default floorCount and activeFloor.
   * ------------------------------------------------------------------------------------------------------------
   */
  public Dungeon(String name) {
	this.name = name;
	this.floors = new Floor[1];
  }

  /*
   * ------------------------------------------------------------------------------------------------------------
   * Constructor - Instantiates a dungeon object with the specified name and floorCount, using the default activeFloor.
   * ------------------------------------------------------------------------------------------------------------
   */
  public Dungeon(String name, int floorCount) {
	this.name = name;
	this.floors = new Floor[floorCount];
  }
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Returns the name of this dungeon.
   * ------------------------------------------------------------------------------------------------------------
   */
  public String getName() {
	return this.name;
  }
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Sets the name of this dungeon.
   * ------------------------------------------------------------------------------------------------------------
   */
  public void setName(String name) {
	this.name = name;
  }
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Returns the active floor of this dungeon.
   * ------------------------------------------------------------------------------------------------------------
   */
  public Floor getFloor(int index) {
	return this.floors[index];
  }
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Sets the active floor of this dungeon.
   * ------------------------------------------------------------------------------------------------------------
   */
  /*public void setFloor(int activeFloorIndex) {
	this.activeFloor = floors[activeFloorIndex];
  }*/
  
}
