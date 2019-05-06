package a.b.project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.Random;
import android.graphics.Rect;

public class EnemyShip{
    private Bitmap bitmap;
    private int x, y;
    private int speed = 1;

    // A hit box for collision detection
    private Rect hitBox;

    
    // Detect enemies leaving the screen
    private int maxX;
    private int minX;

    // Spawn enemies within screen bounds
    private int maxY;
    private int minY;

    //Getters and Setters
    public Bitmap getBitmap(){
	return bitmap;
    }

    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }
      
    // This is used by the TDView update() method to
    // Make an enemy out of bounds and force a re-spawn
    public void setX(int x) {
	this.x = x;
    }


    // Constructor
    public EnemyShip(Context context, int screenX, int screenY){
	bitmap = BitmapFactory.decodeResource
	    (context.getResources(), R.drawable.enemy);

	// Initialize the hit box
	hitBox = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());


	maxX   =   screenX;
	maxY   =   screenY;
	minX   =   0;
	minY   =   0;

	Random generator = new Random();
	speed = generator.nextInt(6)+10;

	x = screenX;
	y = generator.nextInt(maxY) - bitmap.getHeight();
    }

    public void update(int playerSpeed){

        // Move to the left
        x -= playerSpeed;
        x -= speed;

        //respawn when off screen
        if(x < minX-bitmap.getWidth()){
	    Random generator = new Random();
	    speed = generator.nextInt(10)+10;
	    x = maxX;
	    y = generator.nextInt(maxY) - bitmap.getHeight();
        }

	// Refresh hit box location
	hitBox.left = x;
	hitBox.top = y;
	hitBox.right = x + bitmap.getWidth();
	hitBox.bottom = y + bitmap.getHeight();

    }

    public Rect getHitbox(){
	return hitBox;
    }


}

