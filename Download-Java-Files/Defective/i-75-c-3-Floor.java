/*
 * **************************************************************************************************************
 * Floor.java | Author: brandonlewis | Date: 2019.02.15 | Rev: 2019.03.01
 * This file is a class of the navigation package for the "Dungeons of UNG" text-based game.
 * **************************************************************************************************************
 */
package navigation;

public class Floor {
  
  private Room[][] rooms;
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Constructor - Instantiates a floor object with an empty 2D array of rooms of the specified size.
   * ------------------------------------------------------------------------------------------------------------
   */
  public Floor(int gridSizeX, int gridSizeY) {
	this.rooms = new Room[gridSizeX][gridSizeY];
  }
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Returns the room at the specified location in the 2D room array.
   * ------------------------------------------------------------------------------------------------------------
   */
  public Room getRoom(int xIndex, int yIndex) {
	return this.rooms[xIndex][yIndex];
  }
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Assigns the specified room to the specified location in the 2D room array.
   * ------------------------------------------------------------------------------------------------------------
   */
  public void setRoom(Room room, int xIndex, int yIndex) {
	this.rooms[xIndex][yIndex] = room;
  }
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Clears the specified location in the 2D room array.
   * ------------------------------------------------------------------------------------------------------------
   */
  public void clearRoom(int xIndex, int yIndex) {
	this.rooms[xIndex][yIndex] = null;
  }
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Returns a string description of this floor object's rooms.
   * ------------------------------------------------------------------------------------------------------------
   */
  public String toString() {
	String result = "Rooms:\n";
	for (Room[] rooms1d : this.rooms) {
		for (Room room : rooms1d) {
			  result += room.toString() + "\n";
			}
	}
	
	result += "\n";
	return result;
  }

}
