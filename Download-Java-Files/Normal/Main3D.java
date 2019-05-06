package main;

import javax.swing.JFrame;

import util.Point3D;
import util.Vector3D;
import window.Panel3D;
import world.World3D;

public class Main3D {

	public static void main(String[] args) {
		Point3D point = new Point3D(30,30,60);
		Vector3D vector = new Vector3D(90,90, 1);
		 
		System.out.println(point);
		System.out.println(vector);
		
		
		point.move(vector);
		System.out.println("After Moving:");
		System.out.println(point);
		System.out.println(vector);
		
		World3D world = new World3D(100,100,100);
		world.generate();
		
		JFrame frame = new JFrame("3D Raycaster");
		Panel3D panel = new Panel3D(640, 480, 1, point, vector, world);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		
	}
}
