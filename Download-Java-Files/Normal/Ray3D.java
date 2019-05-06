package render;

import util.Point3D;
import util.Vector3D;
import world.Voxel3D;
import world.World3D;

public class Ray3D { 
	
	private Point3D point;
	private Vector3D vector;
	
	public Ray3D(Point3D pnt, Vector3D vec) {
		this.point = pnt.copy();
		this.vector = vec.copy();
	}
	
	public void override(Point3D pnt, Vector3D vec) {
		this.point = pnt;
		this.vector = vec;
	}
	
	public Voxel3D render(World3D world) {
		while(world.getVoxel(this.point) == Voxel3D.AIR) {
			this.point.move(this.vector);
		}
		
		return world.getVoxel(this.point);
	}

}
