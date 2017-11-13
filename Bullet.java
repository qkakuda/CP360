import java.awt.Color;

import java.awt.Polygon;
import java.awt.Rectangle;

public class Bullet {

	Color myColor = Color.white;
	Polygon me;
	int currx;
	int curry;
	
	int[] xcoord;
	int[] ycoord;
	public Bullet(int x, int y) {
		
		curry = y;
		xcoord = new int[]{x+2, x+2, x, x-2, x-2};
		ycoord = new int[] {y, y-4, y-7, y-4, y};
		me = new Polygon(xcoord, ycoord, 5);
	}
	
	public void movebullet() {
		curry -= 10;
		int[] new_ycoord = new int[] {curry, curry-4, curry-7, curry-4, curry};
		me = new Polygon(xcoord, new_ycoord, 5);
	}
	
	public boolean is_out_of_bounds(int ybound) {
		if (curry < -ybound ) {
			return true;
		}
		return false;
	}
	
}
