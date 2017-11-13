
public class Point3D {
	double x;
	double y;
	double z;
	
	public Point3D (double _x, double _y, double _z) {
		x=_x;
		y=_y;
		z=_z;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
	//return cos(theta) where theta is the angle between the called on Point3D representing the vector and the input Point3D representing a vector
	public double cosOfAngleOfProjection(Point3D p1) {
		//numerator and denominator are to find the solution "cos(theta) = " to the dot product formula
		double numerator = x*p1.getX() + y*p1.getY() + z*p1.getZ();
		double denominator = Math.pow(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2), .5) 
				* Math.pow(Math.pow(p1.getX(), 2) + Math.pow(p1.getY(), 2) + Math.pow(p1.getZ(), 2), .5);
		return numerator/denominator;
	}
	
	//get the vector from p to p1
	public Point3D p1Minusp (Point3D p1) {
		double vx = p1.getX() - x;
		double vy = p1.getY() - y;
		double vz = p1.getZ() - z;
		return new Point3D(vx, vy, vz);
	}

	//print a point conveniently
	public String printPoint() {
		return "(" + x + "," + y + "," + z + ")";
	}
	
}