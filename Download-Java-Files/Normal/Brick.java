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
public class Brick extends Item {

    private int width;
    private int height;
    private Game game;
    private int index;
    private boolean dead;
    
     /**
     * to create brick with every attribute it have
     *
     * @param x to set the x position
     * @param y to set the y position
     * @param width to set the width of the brick
     * @param height to set the height of the brick
     * @param Game to set the game where the brick is created
     */
    public Brick(int x, int y, int width, int height, Game game, int index) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.game = game;
        this.index = index;
        dead= false;
    }
     /**
     * Set the the status of dead
     * @param dead <b>dead</b> value with the dead status
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }
     /**
     * To get the value of dead
     * @return an <code>boolean</code> value with the dead status
     */ 
    public boolean isDead() {
        return dead;
    }
   /**
     * To get the width of the bullet
     * @return an <code>int</code> value with the width
     */ 
    public int getWidth() {
        return width;
    }
     /**
     * To get the height of the brick
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }
    /**
     * Set the width of the brick
     * @param width <b>width</b> value with the width
     */
    public void setWidth(int width) {
        this.width = width;
    }
    /**
     * Set the height of the brick
     * @param height <b>height</b> value with the height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
    
    


    @Override
    public void tick() {
    if(index <0){
        dead=true;
    }else
        dead=false;
    }

    public Rectangle getPerimetro() {

        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void render(Graphics g) {
        if(index>=0)
        g.drawImage(Assets.brick[index], getX(), getY(), getWidth(), getHeight(), null);
    }
}
