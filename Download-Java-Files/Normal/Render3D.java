package render;

import java.awt.Color;
import java.awt.Graphics;

import world.Voxel3D;
import world.World3D;
import util.Point3D;
import util.Vector3D;

public class Render3D {
	 
	private World3D world;
	private int width;
	private int height;
	
	private Point3D point;
	private Vector3D vector;
	
	private int FOV = 90;
	
	private int scale;

	public Render3D(int w, int h, int s, Point3D pnt, Vector3D vec, World3D wld) {
		this.width = w;
		this.height = h;
		this.scale = s;
		this.point = pnt;
		this.vector = vec;
		this.world = wld;
	}
	
	public void render(Graphics g) {
		Ray3D ray = new Ray3D(new Point3D(0,0,0), new Vector3D(0,0,0));
		Voxel3D voxel = null;
		
		for(int x = 0; x < this.width; x++) {
			for(int y = 0; y < this.height; y++) {
				
				ray = new Ray3D(point.copy(), new Vector3D(this.vector.getAngle1() + (FOV * ((((double)x)/this.width) - 0.5)), this.vector.getAngle2() + (FOV * ((((double)y)/this.height) - 0.5)), 1));
				voxel = ray.render(world);
				//System.out.println(x);
				if (voxel != null) {
					g.setColor(voxel.getColor());
				} else
					g.setColor(Color.CYAN);
				g.fillRect(x*this.scale, y*this.scale, this.scale, this.scale);

			}
		}
	}
}
