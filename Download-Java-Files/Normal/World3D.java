package world;

import util.Point3D;

public class World3D {
	
	private Voxel3D[][][] world;
	
	private int width;
	private int length;
	private int height;
	
	public World3D(int w, int l, int h) {
		this.world = new Voxel3D[w][l][h];
		this.width = w;
		this.length = l;
		this.height = h;
	}
	
	public void generate() {
		for(int x = 0; x < this.width; x++) {
			for(int y = 0; y < this.length; y++) {
				for(int z = 0; z < this.height/2; z++) {
					if ((x%2)==0) {
						this.world[x][y][z] = Voxel3D.DIRT;
					} else {
						this.world[x][y][z] = Voxel3D.GRASS;
					}
				}
				for(int z = this.height/2; z < this.height; z++) {
					this.world[x][y][z] = Voxel3D.AIR;
				}
			}
		}
	}
	
	public Voxel3D getVoxel(Point3D pnt) {

		try {	
		return this.world[(int)pnt.getX()][(int)pnt.getY()][(int)pnt.getZ()];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
		}


}
