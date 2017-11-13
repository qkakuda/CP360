import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GameMain {

	public static void main(String[] args) {

	    
	    JFrame frame = new JFrame();
	    frame.setTitle("Que Cal");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
	    GameCanvas gcanvas = new GameCanvas();
		Container cp = frame.getContentPane();
		cp.setLayout(new BorderLayout());
	    cp.add(gcanvas, BorderLayout.CENTER);

	    frame.pack();
		frame.setResizable(false);
	    frame.setVisible(true);
	}
}
