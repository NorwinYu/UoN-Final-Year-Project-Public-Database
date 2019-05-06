package world;

import java.awt.Color;

public class Voxel3D {
	
	private Color color;

	public static final Voxel3D 
	DIRT = new Voxel3D(new Color(160, 90, 20)),
	AIR = new Voxel3D(new Color(0,0,0,0)),
	GRASS = new Voxel3D(new Color(50,140,70));
	
	public Voxel3D(Color clr) {
		this.color = clr;
	}
	
	public Color getColor() {
		return this.color; 
	}
			
}
