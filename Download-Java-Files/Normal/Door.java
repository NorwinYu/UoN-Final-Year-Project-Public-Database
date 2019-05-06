/*
 * **************************************************************************************************************
 * Door.java | Author: brandonlewis | Date: 2019.02.15 | Rev: 2019.03.01
 * This file is a class of the navigation package for the "Dungeons of UNG" text-based game.
 * **************************************************************************************************************
 */
package navigation;

public class Door extends RoomFeature {

  private boolean isLocked;
  private String material;

  /*
   * ------------------------------------------------------------------------------------------------------------
   * Constructor - Instantiates a door object with the specified isLocked state and default wood material.
   * ------------------------------------------------------------------------------------------------------------
   */
  public Door(boolean isLocked) {
	this.isLocked = isLocked;
	this.material = "wood";
  }
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Constructor - Instantiates a door object with the specified isLocked state and material.
   * ------------------------------------------------------------------------------------------------------------
   */
  public Door(boolean isLocked, String material) {
	this.isLocked = isLocked;
	if (material == "wood" || material == "steel") {
	  this.material = material;
	} else {
	  System.out.println("Door constructor error: material must either be \"wood\" or \"steel\".");
	}
	
  }

  /*
   * ------------------------------------------------------------------------------------------------------------
   * Returns the state of the door's lock.
   * ------------------------------------------------------------------------------------------------------------
   */
  public boolean isLocked() {
	return this.isLocked;
  }
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Sets the state of the door's lock.
   * ------------------------------------------------------------------------------------------------------------
   */
  public void setLock(boolean isLocked) {
	this.isLocked = isLocked;
  }
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Returns the material of the door.
   * ------------------------------------------------------------------------------------------------------------
   */
  public String getMaterial() {
	return this.material;
  }
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Sets this door to the specified material.
   * ------------------------------------------------------------------------------------------------------------
   */
  public void setMaterial(String material) {
	if (material == "wood" || material == "steel") {
	  this.material = material;
	} else {
	  System.out.println("setMaterial() error: material must either be \"wood\" or \"steel\".");
	}
  }
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Returns a string representation of this object.
   * ------------------------------------------------------------------------------------------------------------
   */
  public String toString() {
	if (isLocked) {
	  return "A locked door made of " + material + ".";
	} else {
	  return "An unlocked door made of " + material + ".";
	}
  }
}
