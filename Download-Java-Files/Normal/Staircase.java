/*
 * *****************************************************************************************
 * Staircase.java | Author: brandonlewis | Date: 2019.02.15 | Rev: 2019.02.22
 * This file is a class of the navigation package for the "Dungeons of UNG" text-based game.
 * *****************************************************************************************
 */
package navigation;

public class Staircase extends RoomFeature {
  
  private boolean isAscending;
  
  /*
   * ---------------------------------------------------------------------------------------
   * Constructor - Instantiates a staircase object with the specified leadsUp boolean state.
   * ---------------------------------------------------------------------------------------
   */
  public Staircase(boolean isAscending) {
	this.isAscending = isAscending;
  }
  
  /*
   * ---------------------------------------------
   * Returns the state of the isAscending boolean.
   * ---------------------------------------------
   */
  public boolean isAscending() {
	return this.isAscending;
  }
  
  /*
   * ----------------------------------------------------
   * Sets the state of the staircase, leading up or down.
   * ----------------------------------------------------
   */
  public void setIsAscending(boolean isAscending) {
	this.isAscending = isAscending;
  }
  
  /*
   * -----------------------------------------------
   * Returns a string representation of this object.
   * -----------------------------------------------
   */
  public String toString() {
	if (isAscending) {
	  return "A steep spiral staircase rises to the floor above.";
	} else {
	  return "A steep spiral staircase descends to the floor below.";
	}
	
  }
  
}
