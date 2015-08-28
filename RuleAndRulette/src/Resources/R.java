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
import visual.AnimatedSprite;
import GUI.PixelImage;
import GameLogic.Characters.Entity;


public class R {
	
	private static int DEFAULT_SCALE = 1;
	
	public static PixelImage logo = loadPixelImage("assets/logo.png").getScaledInstance(DEFAULT_SCALE);
	
	public static class characters {
		public static PixelImage rule = loadPixelImage("assets/characters/rule.jpg").getScaledInstance(DEFAULT_SCALE);
		public static PixelImage rulette = loadPixelImage("assets/characters/rulette.jpg").getScaledInstance(DEFAULT_SCALE);
	}
	
	public static class environment {
		public static PixelImage block = loadPixelImage("assets/environment/block.jpg").getScaledInstance(DEFAULT_SCALE);
	}
	
	public static class animations {
		public static AnimatedSprite mami = new AnimatedSprite(loadPixelImage("assets/animations/mami_run_f8_w70_h110.png"), 8, 1);
	}
	
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
		
		final Body body = Physics.create(new Vec2(0, 0), true);
		
		
		
		final Entity e = new Entity() { public void render(Graphics2D g) { 
				g.drawImage(R.environment.block.asBufferedImage(), body.getX(), body.getY(), null);
			};
		};
		
		new Thread(new Runnable() {
			public void run() {
				for(;;) {
					frame.repaint();
					Physics.tick(1/60f);
					R.animations.mami.update(1/60f);
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
				g.drawImage(R.animations.mami.getImage().asBufferedImage(),20, 20, null);
			}
		});
		
		frame.pack();
		frame.setVisible(true);
		
	}
	
}
