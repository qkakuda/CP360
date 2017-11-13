import java.util.ArrayList;
////////TODO : Get Center Method - shoot from center of the ship
////////TODO : Shading 
public class Square {
	ArrayList<Face> faces = new ArrayList<Face>();
	Point3D center;
	
	public Square(ArrayList<Face> _faces) {
		faces = _faces;
		center = new Point3D(0,0,0);
	}
	
	public Point3D return_center() {
		return center;
	}
	
	//rotate around user input line through (0,0,0) and (x,y,z)
	public double[][] calculateRotationMatrix(double x, double y, double z, double theta) {
		//make xyz a unit vector
		double magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
		x = x/magnitude;
		y=y/magnitude;
		z=z/magnitude;
		
		//initialize matrix for rotating about any vector by theta
		int A=4;
		int B=3;
		
		double centerX = center.getX();
		double centerY = center.getY();
		
		double[][] rotationMatrix = new double[A][B];
		rotationMatrix[0][0] = Math.cos(theta) + Math.pow(x,2)*(1-Math.cos(theta));
		rotationMatrix[1][0] = x*y*(1-Math.cos(theta))-z*Math.sin(theta);
		rotationMatrix[2][0] = x*z*(1-Math.cos(theta))+y*Math.sin(theta);
		rotationMatrix[3][0] = (-centerX)*(Math.cos(theta) + Math.pow(x,2)*(1-Math.cos(theta))) - (centerY)*(x*y*(1-Math.cos(theta))-z*Math.sin(theta)) + centerX;
		
		rotationMatrix[0][1] = y*x*(1-Math.cos(theta))+z*Math.sin(theta);
		rotationMatrix[1][1] = Math.cos(theta)+Math.pow(y,2)*(1-Math.cos(theta));
		rotationMatrix[2][1] = y*z*(1-Math.cos(theta))-x*Math.sin(theta);
		rotationMatrix[3][1] = (-centerX)*(y*x*(1-Math.cos(theta))+z*Math.sin(theta)) - (centerY)*(Math.cos(theta)+Math.pow(y,2)*(1-Math.cos(theta))) + centerY;
		
		rotationMatrix[0][2] = z*x*(1-Math.cos(theta))-y*Math.sin(theta);
		rotationMatrix[1][2] = z*y*(1-Math.cos(theta))+x*Math.sin(theta);
		rotationMatrix[2][2] = Math.cos(theta) + Math.pow(z,2)*(1-Math.cos(theta));
		rotationMatrix[3][2] = (-centerX)*(z*x*(1-Math.cos(theta))-y*Math.sin(theta)) - (centerY)*(z*y*(1-Math.cos(theta))+x*Math.sin(theta));
		
		return rotationMatrix;
	}
	
	public void rotate_square(double x, double y, double z, double theta) {
		double[][] rotationMatrix = calculateRotationMatrix(x, y, z, theta);
		for(int i=0; i<faces.size(); i++) {
			Face currFace = faces.get(i);
			currFace.rotate_face(rotationMatrix);
		}
	}
	
	public void translate(double x, double y) {
		for(int i=0; i<faces.size(); i++) {
			Face currFace = faces.get(i);
			currFace.translate(x,y);
		}
		//adds/subtracts x/y from current center to keep track of center. hopefully
		//if something is fucky with rotate, check this.
		center = new Point3D(center.getX() + x, center.getY() +y, center.getZ());
	}
	
	public ArrayList<Face> get_faces() {
		return faces;
	}
			
	
}
