import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;
////////TODO : you cannot hold down space and shoot
class CustomKeyListener extends JPanel implements KeyListener{
	
	GameCanvas canvas;
	
	boolean leftpressed = false;
	boolean rightpressed = false;
	boolean uppressed = false;
	boolean downpressed = false;
	
	
	
	public CustomKeyListener(GameCanvas c) {
		canvas = c;
	}
	
	public void keyTyped(KeyEvent e) {           
     
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT) {
			if (!rightpressed) {
				leftpressed = true;
				canvas.updateChangex(-4);
				canvas.rotatex(-1);			
			}						
		}
		else if (key == KeyEvent.VK_RIGHT) {
			if (!leftpressed) {
				rightpressed = true;
				canvas.updateChangex(4);
				canvas.rotatex(1);		
			}		
		}
		else if (key == KeyEvent.VK_UP) {
			if (!downpressed) {
				uppressed = true;
				canvas.updateChangey(-4);	
			}
		}
		else if (key == KeyEvent.VK_DOWN) {
			if (!uppressed) {
				downpressed = true;	
				canvas.updateChangey(4);
			}
    	}
		if (key == KeyEvent.VK_SPACE) {
			//TODO shoot shit from the center of the square not this stuff
			int bulletx = (int) canvas.ship_center().getX();
			int bullety = (int) canvas.ship_center().getY() - canvas.get_ship_size()/2 ;
			Bullet b = new Bullet(bulletx, bullety);
			canvas.addbullet(b);
    	}
		canvas.repaint();  
     }

      public void keyReleased(KeyEvent e) {  
    	  int key = e.getKeyCode();
    	  if (key != KeyEvent.VK_SPACE) {
    		  canvas.updateChangex(0);
    		  canvas.updateChangey(0);
        	  if (leftpressed) {
        		  canvas.rotatex(1);  
        		  leftpressed = false;
        	  }
        	  else if (rightpressed) {
        		  canvas.rotatex(-1);  
        		  rightpressed = false;
        	  }
        	  if (uppressed) {
        		  uppressed = false;
        	  }
        	  else if (downpressed) {
        		  downpressed = false;
        	  }
        	  
    	  }

//    	  int key = e.getKeyCode();
//	    	  if (key == KeyEvent.VK_LEFT) {
//	    		  canvas.updateChangex(0);
//	    		  canvas.updateChangey(0);
//	    		  canvas.rotatex(-1);
//	    	  }    
//	    	  else if (key == KeyEvent.VK_RIGHT) {
//	    		  rightpressed = false;
//	    		  canvas.updateChangex(0);
//	    		  canvas.updateChangey(0);
//	    		  canvas.rotatex(1);
//	    	  }
//	    	  else if (key == KeyEvent.VK_UP) {
//	    		  uppressed = false;
//	    		  canvas.updateChangex(0);
//	    		  canvas.updateChangey(0);
//	    	  }    
//	    	  else if (key == KeyEvent.VK_DOWN) {
//	    		  downpressed = false;
//	    		  canvas.updateChangex(0);
//	    		  canvas.updateChangey(0);
//	    	  }
      }
      
      private void fix_rotations_updown() {
    	  if (leftpressed = true) {
    		  canvas.rotatex(1);
    		  leftpressed = false;
    	  }
    	  else if (rightpressed = true) {
    		  canvas.rotatex(-1);
    		  rightpressed = false;
    	  }
    	  if (uppressed = true) {
    		  canvas.pitch(1);
    		  uppressed = false;
    	  }
      }
   } 


