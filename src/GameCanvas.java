import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
///////TODO : check TODOs
public class GameCanvas extends JPanel implements ActionListener{
	private int width = 800;
	private int height = 900;
	private double angle = Math.PI*.125;
	private boolean changingXDirection = false;
	private boolean changingYDirection = false;
	
	private int e = 1250;
	private int sqrSideLength = 100;
	
	
	public int xcoord;
	public int ycoord;
	
	private int changex;
	private int changey;
	
	
	Square ship;
	Rectangle testsquare;
	ArrayList<Bullet> bullets = new ArrayList<>();
	
	Timer timer=new Timer(100, this);
	
	public GameCanvas() {	
		construct_ship();
		timer.start();
		
		setPreferredSize(new Dimension(width, height));
		setBackground(Color.black);
		CustomKeyListener customkeylisten = new CustomKeyListener(this);
		customkeylisten.addKeyListener(customkeylisten);
		customkeylisten.setFocusable(true);
		add(customkeylisten);
		
		xcoord = 0;
		ycoord = 0;
		
		changex = 0;
		changey = 0;
	}
	
    public void paintComponent(Graphics g) {
		super.paintComponent(g);  //without this no background color set.
		Graphics2D g2d = (Graphics2D)g; //cast so we can use JAVA2D.
		g2d.translate(getWidth()/2,getHeight()/2);
		
		//bullets
		ArrayList<Bullet> toRemove = new ArrayList<>();
		for (Bullet b : bullets) {
			g2d.setColor(Color.white);
			g2d.draw(b.me);
			b.movebullet();
			if (b.is_out_of_bounds(height/2)) {
			        toRemove.add(b);
			}
		}
   		bullets.removeAll(toRemove);

		drawSquare(g2d, ship);

		move_ship();
    }
    
    public void drawSquare(Graphics2D g2d, Square sq) {
	    	for (Face f : sq.get_faces()) {
	    		drawFace(g2d, f, f.get_color());
	    	}
    }
    
    //draw the face on the screen
	public void drawFace(Graphics2D g2d, Face face, Color color) {
		//check if the face should be visible
		if (face.faceVisible(e)) {
			ArrayList<Point3D> facepnts = face.getFacePoints();
			int numberOfPoints = facepnts.size();
			Point2D.Double[] projectedFacePnts  =  new Point2D.Double[numberOfPoints];
			int[] projectedXCoords = new int[numberOfPoints];
			int[] projectedYCoords = new int[numberOfPoints];
			for(int i=0; i<numberOfPoints; i++) {	
				projectedFacePnts[i] = projectOnToXY(facepnts.get(i));
				projectedXCoords[i] = (int)projectedFacePnts[i].getX();
				projectedYCoords[i] = (int)projectedFacePnts[i].getY();
			}
			
			Polygon f = new Polygon(projectedXCoords, projectedYCoords, numberOfPoints);
			g2d.setColor(getColor(face));
			g2d.fill(f);
		}

	}
	
	//project the 3d coordinates onto the xy plane for display
	public Point2D.Double projectOnToXY(Point3D point) {
		double projectedX = point.getX()/(1-point.getZ()/e);
		double projectedY = point.getY()/(1-point.getZ()/e);
		return new Point2D.Double(projectedX, projectedY);
	}
    
    public int getwidth() {
    	return width;
    }
    public int getheight() {
    	return height;
    }
    
    private Color getColor(Face face) {
    	double cos = face.midPointToEyeMidpointToNormAngle(e);
    	int redVal = (int) (50 - 150*cos);
    	return new Color(redVal, 0, 0);
    }
    
    private Color random_color() {
		Random rand = new Random();
		int  n1 = rand.nextInt(255) + 1;
		int  n2 = rand.nextInt(255) + 1;
		int  n3 = rand.nextInt(255) + 1;
		Color rando = new Color(n1,n2,n3);
		return rando;
    }

    
    private void move_ship() {
    		ship.translate(changex, changey);
    }
    
    public void updateChangex(int _changex) {
    		if (changex == _changex) {
    			changingXDirection = false;
    		}
    		else {
    			changingXDirection = true;
    		}
    		changex = _changex;	
    }
    
    public void updateChangey(int _changey) {
		if (changey == _changey) {
			changingYDirection = false;
		}
		else {
			changingYDirection = true;
		}
		changey = _changey;	
    }
    
    public void rotatex(int direction) {
    		if(changingXDirection) {
    			ship.rotate_square(0, 1, 0, direction*angle);
    		}
    }
    
    

	@Override
	public void actionPerformed(ActionEvent ev) {
		// TODO Auto-generated method stub
		if(ev.getSource()==timer){
		      repaint();// this will call at every 1 second
		    }
	}
    public int get_ship_size() {
    	return sqrSideLength;
    }
	public Point3D ship_center() {
		return ship.return_center();
	}
	public void addbullet(Bullet b) {
		bullets.add(b);
	}
	public void removebullet(Bullet b) {
		bullets.remove(b);
	}
	
 
	private void construct_ship() {
		//create points to represent cube
		Point3D p1 = new Point3D(-sqrSideLength/2,-sqrSideLength/2,-sqrSideLength/2);
		Point3D p2 = new Point3D(sqrSideLength/2,-sqrSideLength/2,-sqrSideLength/2);
		Point3D p3 = new Point3D(sqrSideLength/2,sqrSideLength/2,-sqrSideLength/2);
		Point3D p4 = new Point3D(-sqrSideLength/2,sqrSideLength/2,-sqrSideLength/2);
		 
		Point3D p5 = new Point3D(-sqrSideLength/2,-sqrSideLength/2,sqrSideLength/2);
		Point3D p6 = new Point3D(sqrSideLength/2,-sqrSideLength/2,sqrSideLength/2);
		Point3D p7 = new Point3D(sqrSideLength/2,sqrSideLength/2,sqrSideLength/2);
		Point3D p8 = new Point3D(-sqrSideLength/2,sqrSideLength/2,sqrSideLength/2);
		
		Point3D pwp = new Point3D(150,0,0);
		Point3D pwn = new Point3D(-150,0,0);
		Point3D pf = new Point3D(0,-75,0);
		 
		//create arrays of 3d points to represent the faces of the cube with these points
		//the points are added in clockwise order when viewing the face
		//f1Apts are the points of the cube representing the face opposite f1pts.  etc.
		ArrayList<Point3D> f1pts = new ArrayList<Point3D>();
		f1pts.add(p1);
		f1pts.add(p2);
		f1pts.add(p3);
		f1pts.add(p4);
		
		ArrayList<Point3D> f1Apts = new ArrayList<Point3D>();
		f1Apts.add(p6);
		f1Apts.add(p5);
		f1Apts.add(p8);
		f1Apts.add(p7);

//		ArrayList<Point3D> f2pts = new ArrayList<Point3D>();
//		f2pts.add(p5);
//		f2pts.add(p1);
//		f2pts.add(p4);
//		f2pts.add(p8);
//		
//		ArrayList<Point3D> f2Apts = new ArrayList<Point3D>();
//		f2Apts.add(p2);
//		f2Apts.add(p6);
//		f2Apts.add(p7);
//		f2Apts.add(p3);
		
		ArrayList<Point3D> f3pts = new ArrayList<Point3D>();
		f3pts.add(p2);
		f3pts.add(p1);
		f3pts.add(p5);
		f3pts.add(p6);
		
		ArrayList<Point3D> f3Apts = new ArrayList<Point3D>();
		f3Apts.add(p7);
		f3Apts.add(p8);
		f3Apts.add(p4);
		f3Apts.add(p3);
		
		//right wing face point lists
		ArrayList<Point3D> wpf1pts = new ArrayList<Point3D>();
		wpf1pts.add(p6);
		wpf1pts.add(p7);
		wpf1pts.add(pwp);
		
		ArrayList<Point3D> wpf1Apts = new ArrayList<Point3D>();
		wpf1Apts.add(p3);
		wpf1Apts.add(p2);
		wpf1Apts.add(pwp);
		
		ArrayList<Point3D> wpf2pts = new ArrayList<Point3D>();
		wpf2pts.add(p7);
		wpf2pts.add(p3);
		wpf2pts.add(pwp);
		
		ArrayList<Point3D> wpf2Apts = new ArrayList<Point3D>();
		wpf2Apts.add(p2);
		wpf2Apts.add(p6);
		wpf2Apts.add(pwp);
		
		//left wing face point lists
		ArrayList<Point3D> wnf1pts = new ArrayList<Point3D>();
		wnf1pts.add(p8);
		wnf1pts.add(p5);
		wnf1pts.add(pwn);
		
		ArrayList<Point3D> wnf1Apts = new ArrayList<Point3D>();
		wnf1Apts.add(p1);
		wnf1Apts.add(p4);
		wnf1Apts.add(pwn);
		
		ArrayList<Point3D> wnf2pts = new ArrayList<Point3D>();
		wnf2pts.add(p4);
		wnf2pts.add(p8);
		wnf2pts.add(pwn);
		
		ArrayList<Point3D> wnf2Apts = new ArrayList<Point3D>();
		wnf2Apts.add(p5);
		wnf2Apts.add(p1);
		wnf2Apts.add(pwn);
		
		
		 
		Face face1 = new Face(f1pts);
		Face face1A = new Face(f1Apts);
//		Face face2 = new Face(f2pts);
//		Face face2A = new Face(f2Apts);
		Face face3 = new Face(f3pts);
		Face face3A = new Face(f3Apts);
		
		//right wing faces
		Face wpf1 = new Face(wpf1pts);
		Face wpf1A = new Face(wpf1Apts);
		Face wpf2 = new Face(wpf2pts);
		Face wpf2A = new Face(wpf2Apts);
		
		//left wing faces
		Face wnf1 = new Face(wnf1pts);
		Face wnf1A = new Face(wnf1Apts);
		Face wnf2 = new Face(wnf2pts);
		Face wnf2A = new Face(wnf2Apts);
		
		
		face1.set_color(Color.cyan);
		face1A.set_color(Color.magenta);
//		face2.set_color(Color.white);
//		face2A.set_color(Color.green);
		face3.set_color(Color.PINK);
		face3A.set_color(Color.ORANGE);
		wpf1.set_color(Color.white);
		wpf1A.set_color(Color.white);
		wpf2.set_color(Color.green);
		wpf2A.set_color(Color.green);
		wnf1.set_color(Color.white);
		wnf1A.set_color(Color.white);
		wnf2.set_color(Color.green);
		wnf2A.set_color(Color.green);
		
		ArrayList<Face> shipFaces = new ArrayList<Face>();
		shipFaces.add(face1);
		shipFaces.add(face1A);
//		shipFaces.add(face2);
//		shipFaces.add(face2A);
		shipFaces.add(face3);
		shipFaces.add(face3A);
		shipFaces.add(wpf1);
		shipFaces.add(wpf1A);
		shipFaces.add(wpf2);
		shipFaces.add(wpf2A);
		shipFaces.add(wnf1);
		shipFaces.add(wnf1A);
		shipFaces.add(wnf2);
		shipFaces.add(wnf2A);
		
		ship = new Square(shipFaces);
	}
}
