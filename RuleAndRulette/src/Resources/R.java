package Resources;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.locks.LockSupport;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import phys.Body;
import phys.Physics;
import phys.Vec2;
import GUI.PixelImage;
import GameLogic.Characters.Entity;


public class R {

	public static PixelImage logo = loadPixelImage("assets/logo.png").getScaledInstance(4);
	
	public static PixelImage loadPixelImage(String fname) {
		try {
			return new PixelImage(ImageIO.read(new File(fname)));
		} catch (IOException e) {
			System.err.println("Error loading \"" + fname + "\": " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		final Body body = Physics.create(new Vec2(0, 500), true);
		
		final Entity e = new Entity();
		Physics.createCollider(e.body, 60, 60);
		
		new Thread(new Runnable() {
			public void run() {
				for(;;) {
					frame.repaint();
					Physics.tick(1/60f);
					LockSupport.parkNanos(100000);
				}
			}
		}).start();
		
		frame.add(new JComponent() {
			{
				this.setPreferredSize(new Dimension(300, 300));
			}
			protected void paintComponent(Graphics g) {
				e.render((Graphics2D)g);
				g.drawImage(R.logo.asBufferedImage(), body.getX(), body.getY(), null);
			}
		});
		
		frame.pack();
		frame.setVisible(true);
		
	}
	
}
