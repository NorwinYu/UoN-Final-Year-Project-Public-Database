/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Patricio y Diego
 */
public class PowerUp extends Item {

    private int width;
    private int height;
    private Game game;
    private int playerWidth;
    private int timer;
      /**
     * to create the powerup with every attribute it have
     *
     * @param x to set the x position
     * @param y to set the y position
     * @param Game to set the game where the bullet is created
     */

    public PowerUp(int x, int y, Game game) {
        super(x, y);
        width = 30;
        height = 30;
        this.game = game;
        playerWidth= game.getPlayer().getWidth()* 4 / 3;
        timer=0;
    }
    /**
     * To get the width of the powerup
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }
    /**
     * To get the height of the powerup
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }
    /**
     * Set the width of the powerup
     * @param width <b>width</b> value with the width
     */
    public void setWidth(int width) {
        this.width = width;
    }
    /**
     * Set the height of the powerup
     * @param height <b>height</b> value with the height
     */
    public void setHeight(int height) {
        this.height = height;
    }
     /**
     * To get the playerWidth of the powerup
     * @return an <code>int</code> value with the playerWidth
     */
    public int getPlayerWidth() {
        return playerWidth;
    }
    

    @Override
    public void tick() {   
        y=y+1; 
    }

    public Rectangle getPerimetro() {

        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public boolean intersecta(Object obj) {
        return obj instanceof Player && getPerimetro().intersects(((Player) obj).getPerimetro());
    }

    @Override
    //Draw powerup image
    public void render(Graphics g) {
    g.drawImage(Assets.powerUp, getX(), getY(), getWidth(), getHeight(), null);
    }

}
