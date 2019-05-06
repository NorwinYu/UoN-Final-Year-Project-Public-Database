/*
 * **************************************************************************************************************
 * Wall.java | Author: brandonlewis | Date: 2019.02.15 | Rev: 2019.02.15
 * This file is a class of the navigation package for the "Dungeons of UNG" text-based game.
 * **************************************************************************************************************
 */
package navigation;

public class Wall extends RoomFeature {
  
  private boolean isBreakable, isBroken = false;
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Constructor - Instantiates a wall object with the default isBreakable and isBroken states.
   * ------------------------------------------------------------------------------------------------------------
   */
  public Wall() {
	this.isBreakable = false;
  }
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Constructor - Instantiates a wall object with the specified isBreakable and default isBroken states.
   * ------------------------------------------------------------------------------------------------------------
   */
  public Wall(boolean isBreakable) {
	this.isBreakable = isBreakable;
  }
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Returns the isBreakable state of the wall.
   * ------------------------------------------------------------------------------------------------------------
   */
  public boolean isBreakable() {
	return this.isBreakable;
  }
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Sets the isBreakable state of the wall.
   * ------------------------------------------------------------------------------------------------------------
   */
  public void setBreakable(boolean isBreakable) {
	this.isBreakable = isBreakable;
  }
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Returns the isBroken state of the wall.
   * ------------------------------------------------------------------------------------------------------------
   */
  public boolean isBroken() {
	return this.isBroken;
  }
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Sets the isBroken state of the wall.
   * ------------------------------------------------------------------------------------------------------------
   */
  public void setBroken(boolean isBroken) {
	this.isBroken = isBroken;
  }
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Returns a string representation of this object.
   * ------------------------------------------------------------------------------------------------------------
   */
  public String toString() {
	return "a wall";
  }
}
