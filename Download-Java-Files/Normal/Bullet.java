/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;
import java.awt.Rectangle;
import static java.lang.Math.abs;

/**
 *
 * @author Patricio y Diego
 */
public class Bullet extends Item {
    
    private int velX;                       //to store the direction in X
    private int velY;                       //to store the direction in Y
    private int aux;                        // to store the velX in case of getting on the center
    private int width;                      //to store the width of the bullet
    private int height;                     //to store the height of the bullet
    private Game game;                      //to store the Game
    private Animation animationBullet;      //to store the animation of the bullet
    private boolean endGame;                //to store the game status depending in bullet position
    
     /**
     * to create bullet with every attribute it have
     *
     * @param x to set the x position
     * @param y to set the y position
     * @param width to set the width of the bullet
     * @param height to set the height of the bullet
     * @param Game to set the game where the bullet is created
     */
    public Bullet(int x, int y, int width, int height, Game game) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.game = game;
        velX = 3;
        velY = -3;
        endGame = false;
        this.animationBullet = new Animation(Assets.bullet,100);
    }
    
    /**
     * To get the direction in X of the bullet
     * @return an <code>int</code> value with the direction
     */
    public int getVelX() {
        return velX;
    }
    
    /**
     * To get the direction in Y of the bullet
     * @return an <code>int</code> value with the direction
     */
    public int getVelY() {
        return velY;
    }
    
    /**
     * To get the width of the bullet
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }
    
     /**
     * To get the height of the bullet
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set the direction in X of the bullet
     * @param velX <b>velX</b> value with the direction
     */
    public void setVelX(int velX) {
        this.velX = velX;
    }
    
     /**
     * Set the direction in Y of the bullet
     * @param velY <b>velY</b> value with the direction
     */
    public void setVelY(int velY) {
        this.velY = velY;
    }
     /**
     * Set the width of the bullet
     * @param width <b>width</b> value with the width
     */
    public void setWidth(int width) {
        this.width = width;
    }

     /**
     * Set the height of the bullet
     * @param height <b>height</b> value with the height
     */
    public void setHeight(int height) {
        this.height = height;
    }
    
     /**
     * To get the status of the game
     * @return an <code>int</code> value with the width
     */
    public boolean isEndGame() {
        return endGame;
    }
    
    /**
     * Set the status of the game
     * @param endGame <b>endGame</b> value with boolean
     */
    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }
    
    
    
    @Override
    public void tick() {
        //change animation of the bullet
        this.animationBullet.tick();
        
            // change direction of x position and y position if colision
            if (getX() + getWidth() > game.getWidth()) {
                setVelX(-getVelX());
            } else if (getX() < 0) {
                setVelX(-getVelX());
            }
                   
            if (getY()+getHeight() > game.getHeight()) {
                endGame = true;
                
            } else if (getY() < 0) {
                setVelY(-getVelY());
            }
            
            //set direction of movement of the bullet
            setX(getX() + velX);
            setY(getY() + velY);
    }
    
     public Rectangle getPerimetro() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
     
     
      public boolean intersecta(Object obj) {
        return obj instanceof Brick && getPerimetro().intersects(((Brick) obj).getPerimetro());
    }
    
     @Override
    public void render(Graphics g) {
        g.drawImage(animationBullet.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
    }
    
}
