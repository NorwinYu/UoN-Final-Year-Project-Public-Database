/*
 * **************************************************************************************************************
 * Decor.java | Author: brandonlewis | Date: 2019.02.15 | Rev: 2019.02.15
 * This file is a class of the navigation package for the "Dungeons of UNG" text-based game.
 * **************************************************************************************************************
 */
package navigation;

public class Decor extends RoomFeature {
  
  private String name, description;
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Constructor - Instantiates a decor object with the specified name and string.
   * ------------------------------------------------------------------------------------------------------------
   */
  public Decor(String name, String description) {
	this.name = name;
	this.description = description;
  }
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Returns the String name of the decor object.
   * ------------------------------------------------------------------------------------------------------------
   */
  public String getName() {
	return this.name;
  }
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Sets the name of the decor object.
   * ------------------------------------------------------------------------------------------------------------
   */
  public void setName(String name) {
	this.name = name;
  }
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Returns the String name of the decor object.
   * ------------------------------------------------------------------------------------------------------------
   */
  public String getDescription() {
	return this.description;
  }
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Sets the description of the decor object.
   * ------------------------------------------------------------------------------------------------------------
   */
  public void setDescription(String description) {
	this.description = description;
  }
  
  /*
   * ------------------------------------------------------------------------------------------------------------
   * Returns a string representation of this object.
   * ------------------------------------------------------------------------------------------------------------
   */
  public String toString() {
	return "A(n) " + name + ": " + description;
  }
}
