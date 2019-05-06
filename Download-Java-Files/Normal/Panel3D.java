package window;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import render.Render3D;
import util.Point3D;
import util.Vector3D;
import world.World3D;

public class Panel3D extends JPanel{
	
	private Point3D point;
	private Vector3D vector;
	
	private Render3D renderer;
	
	private World3D world;
	
	public Panel3D( int w, int h, int s,Point3D pnt, Vector3D vec, World3D wld) {
		this.point = pnt;
		this.vector = vec;
		this.world = wld;
		this.setPreferredSize(new Dimension(w*s,h*s));
		this.renderer = new Render3D(w, h, s, pnt, vec, wld);
		 
	}

	public void paintComponent(Graphics g) {
		this.renderer.render(g);
		System.out.println("Frame");
		vector.setAngle1((vector.getAngle1()+1));
		repaint();
	}
}
