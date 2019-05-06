package util;

public class Vector3D {
	
	private double angle1;
	private double angle2;
	private double magnitude;
	
	public Vector3D(double ang1, double ang2, double mag) {
		this.angle1 = ang1;
		this.angle2 = ang2;
		this.magnitude = mag;
	}
	
	public void setAngle1(double ang) { 
		this.angle1 = ang;
	}
	public void setAngle2(double ang) {
		this.angle2 = ang;
	}
	public void setMagnitude(double mag) {
		this.magnitude = mag;
	}
	
	public double getAngle1() {
		return this.angle1;
	}
	public double getAngle2() {
		return this.angle2;
	}
	public double getMagnitude() {
		return this.magnitude;
	}
	
	public double getXOffset() {
		return this.magnitude * Math.cos(Math.toRadians(angle1)) * Math.sin(Math.toRadians(angle2));
	}
	public double getYOffset() {
		return this.magnitude * Math.sin(Math.toRadians(angle1)) * Math.sin(Math.toRadians(angle2));
	}
	public double getZOffset() {
		return this.magnitude * Math.cos(Math.toRadians(angle2));
	}
	
	public Vector3D copy() {
		return new Vector3D(this.angle1, this.angle2, this.magnitude);
	}
	
	public String toString() {
		return "This is a Vector3D object with Angles: " + this.getAngle1() + " and " + this.getAngle2() + " and a magnitude of " + this.getMagnitude() + ".";
	}

}
