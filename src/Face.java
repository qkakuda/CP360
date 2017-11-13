
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Face {
	private ArrayList<Point3D> pts = new ArrayList<Point3D>();
	private Color myColor;
	
	public Face(ArrayList<Point3D> _pts) {
		pts = _pts;
	}
	
	//get the normal vector of the face
	public Point3D getNormal() {
		//get two vectors on the face p2p1 and p2p3
		Point3D v = pts.get(1).p1Minusp(pts.get(0));
		Point3D w = pts.get(1).p1Minusp(pts.get(2));
		//take the cross product of p2p1 and p2p3
		double normX = w.getY()*v.getZ() - w.getZ()*v.getY();
		double normY = w.getZ()*v.getX() - w.getX()*v.getZ();
		double normZ = w.getX()*v.getY() - w.getY()*v.getX();
		return new Point3D(normX, normY, normZ);
	}
	
	//get the average of the points in a face so the middle point on the face
	public Point3D getAvg() {
		double avgX = 0;
		double avgY = 0;
		double avgZ = 0;
		int i;
		for (i=0; i<pts.size(); i++) {
			avgX = avgX + pts.get(i).getX();
			avgY = avgY + pts.get(i).getY();
			avgZ = avgZ + pts.get(i).getY();
		}
		avgX = avgX/(double)i;
		avgY = avgY/(double)i; 
		avgZ = avgZ/(double)i;
		return new Point3D(avgX, avgY, avgZ);
	}
	
	//get the vector from the middle point on the face to the "viewers eye"
	public Point3D vectorToEye(double eyeZ) {
		Point3D midPoint = getAvg();
		return new Point3D(-midPoint.getX(), -midPoint.getY(), eyeZ - midPoint.getZ());
	}
	
	//get cos(theta) where theta is the angle between the midpoint of the face to the eye and the normal vector
	public double midPointToEyeMidpointToNormAngle(double eyeZ) {
		Point3D vToEye = vectorToEye(eyeZ);
		Point3D norm = getNormal();
		return norm.cosOfAngleOfProjection(vToEye);
	}
	
	//if the angle theta described in the above method is greater than 90 degrees then cos(theta) is less than zero and the
	//face should be visible - return true
	public boolean faceVisible(double eyeZ) {
		if(midPointToEyeMidpointToNormAngle(eyeZ) < 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public ArrayList<Point3D> getFacePoints() {
		return pts;
	}
	
	public void setFacePoints(ArrayList<Point3D> _pts) {
		pts = _pts;
	}
	
	public void rotate_face(double[][] rotationMatrix) {
		//multiply the rotation matrix by each point in the face
		for(int i=0; i<pts.size(); i++) {
			double newX = rotationMatrix[0][0]*pts.get(i).getX() + rotationMatrix[1][0]*pts.get(i).getY() + rotationMatrix[2][0]*pts.get(i).getZ() + rotationMatrix[3][0];
			double newY = rotationMatrix[0][1]*pts.get(i).getX() + rotationMatrix[1][1]*pts.get(i).getY() + rotationMatrix[2][1]*pts.get(i).getZ() + rotationMatrix[3][1];
			double newZ = rotationMatrix[0][2]*pts.get(i).getX() + rotationMatrix[1][2]*pts.get(i).getY() + rotationMatrix[2][2]*pts.get(i).getZ() + rotationMatrix[3][2];
			pts.set(i, new Point3D(newX, newY, newZ));
		}
	}
	
	public void translate(double x, double y) {
		for(int i=0; i<pts.size(); i++) {
			double newX = pts.get(i).getX() + x;
			double newY = pts.get(i).getY() + y;	
			pts.set(i, new Point3D(newX, newY, pts.get(i).getZ()));
		}
	}

	//prints the points in the face conveniently
	public String printFace() {
		String face = "";
		for(int i=0; i<pts.size(); i++) {
			face = face + "p" + i + ": " + pts.get(i).printPoint() + "   \n";
		}
		return face;
	}
	
	public void set_color(Color color) {
		myColor = color;
	}
	public Color get_color() {
		return myColor;
	}
	
}
