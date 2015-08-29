package Resources;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import visual.AnimatedSprite;
import GUI.PixelImage;
import GameLogic.Characters.Entity;
import GameLogic.Characters.Rule;
import GameLogic.Characters.Rulette;
import Main.GameLoop;
import Main.Main;


public class R {
	
	private static int DEFAULT_SCALE = 1;
	
	public static PixelImage logo = loadPixelImage("assets/logo.png").getScaledInstance(DEFAULT_SCALE);
	
	public static class characters {
		public static PixelImage rule = loadPixelImage("assets/characters/rule.jpg").getScaledInstance(DEFAULT_SCALE);
		public static PixelImage rulette = loadPixelImage("assets/characters/rulette.jpg").getScaledInstance(DEFAULT_SCALE);
	}
	
	public static class environment {
		public static PixelImage block = loadPixelImage("assets/environment/block.jpg").getScaledInstance(DEFAULT_SCALE);
		public static PixelImage spikes = loadPixelImage("assets/environment/spikes.png").getScaledInstance(DEFAULT_SCALE);
	}
	
	public static class gui {
		public static PixelImage level = loadPixelImage("assets/gui/Level.png").getScaledInstance(DEFAULT_SCALE);
		public static PixelImage zero = loadPixelImage("assets/gui/zero.png").getScaledInstance(DEFAULT_SCALE);
		public static PixelImage one = loadPixelImage("assets/gui/one.png").getScaledInstance(DEFAULT_SCALE);
		public static PixelImage two = loadPixelImage("assets/gui/two.png").getScaledInstance(DEFAULT_SCALE);
		public static PixelImage three = loadPixelImage("assets/gui/three.png").getScaledInstance(DEFAULT_SCALE);
		public static PixelImage four = loadPixelImage("assets/gui/four.png").getScaledInstance(DEFAULT_SCALE);
		public static PixelImage five = loadPixelImage("assets/gui/five.png").getScaledInstance(DEFAULT_SCALE);
		public static PixelImage six = loadPixelImage("assets/gui/six.png").getScaledInstance(DEFAULT_SCALE);
		public static PixelImage seven = loadPixelImage("assets/gui/seven.png").getScaledInstance(DEFAULT_SCALE);
		public static PixelImage eight = loadPixelImage("assets/gui/eight.png").getScaledInstance(DEFAULT_SCALE);
		public static PixelImage nine = loadPixelImage("assets/gui/nine.png").getScaledInstance(DEFAULT_SCALE);
		
		public static PixelImage heart = loadPixelImage("assets/gui/heart.png").getScaledInstance(DEFAULT_SCALE);
		public static PixelImage broken_heart = loadPixelImage("assets/gui/broken_heart.png").getScaledInstance(DEFAULT_SCALE);
		public static PixelImage success = loadPixelImage("assets/gui/success.png").getScaledInstance(DEFAULT_SCALE);
		public static PixelImage failed = loadPixelImage("assets/gui/failed.png").getScaledInstance(DEFAULT_SCALE);
	}
	
	public static class sound{
		public static String jump =  loadSoundURL("Jump.wav");
		public static String land_hard =  loadSoundURL("land_hard.wav");
		public static String land_soft =  loadSoundURL("land_soft.wav");
		public static String explosion =  loadSoundURL("explosion.wav");
	}
	
	public static class animations {
		
		public static class rule {
			public static AnimatedSprite idle = new AnimatedSprite(loadPixelImage("assets/animations/rule/rule_idle_f8_w32_h32.png"), 8, 8);
			public static AnimatedSprite walk = new AnimatedSprite(loadPixelImage("assets/animations/rule/rule_walk_f7_w32_h32.png"), 7, 16);
		}
		
		public static class rulette {
			public static AnimatedSprite idle = new AnimatedSprite(loadPixelImage("assets/animations/rulette/rulette_idle_f8_w32_h32.png"), 8, 8);
			public static AnimatedSprite walk = new AnimatedSprite(loadPixelImage("assets/animations/rulette/rulette_walk_f7_w32_h32.png"), 7, 16);
		}
		
		public static AnimatedSprite mami_run = new AnimatedSprite(loadPixelImage("assets/animations/mami_run_f8_w70_h110.png"), 8, 16);
		public static AnimatedSprite mami_idle = new AnimatedSprite(loadPixelImage("assets/animations/mami_idle_f7_w63_h119.png"), 7, 16);
	}
	
	public static PixelImage loadPixelImage(String fname) {
		try {
			return new PixelImage(ImageIO.read(new File(fname)));
		} catch (IOException e) {
			System.err.println("Error loading \"" + fname + "\": " + e.getMessage());
			//e.printStackTrace();
			return null;
		}
	}
	
	public static String loadSoundURL(String fname) {
		return "assets/sounds/"+fname;
	}
	
	private static PixelImage canvas;
	
	public static void main(String[] args) {		
		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
//		new Thread(new Runnable() {
//			public void run() {
//				for(;;) {
//					frame.repaint();
//					Physics.tick(1/60f);
//					R.animations.mami.update(1/60f);
//					LockSupport.parkNanos(100000);
//				}
//			}
//		}).start();
				
		
		final PixelImage canvas1 = new PixelImage(1920, 1080);
		final PixelImage canvas2 = new PixelImage(1920, 1080);
		
		canvas = canvas1;
		
		GameLoop loop = new GameLoop(60, 50) {

			List<Entity> entities = new ArrayList<Entity>();
			
//			{
//				for(int i = 0; i < 100; i++) {
//					float x = (float)(Math.random()*1920);
//					float y = (float)(Math.random()*1080);
//					entities.add(Math.random() < 0.5f ? new Rule(x, y) : new Rulette(x, y));
//				}
//			}
			
			@Override
			protected void tick(float delta) {
				for(int i = 0; i < 5; i++) {
					float x = (float)(Math.random()*1920);
					float y = (float)(Math.random()*1080);
					entities.add(Math.random() < 0.5f ? new Rule(x, y) : new Rulette(x, y));
				}
				
				for(Iterator<Entity> iter = entities.iterator(); iter.hasNext();) {
					Entity e = iter.next();
					e.update(delta);
					if(e.getY() > 1080) {
						iter.remove();
					}
				}
			}

			@Override
			protected void fixedTick(float delta) {
				// TODO Auto-generated method stub
				
			}

			@Override
			protected void render() {
				canvas.clear();
				for(Entity e : entities) {
					e.render(canvas);
				}

				frame.repaint();
			}
			
		};
		
		loop.start();
		
		
		
		
		frame.add(new JComponent() {
			{
				this.setPreferredSize(new Dimension(1920, 1080));
			}
			protected void paintComponent(Graphics g) {
				g.drawImage(canvas.asBufferedImage() ,0, 0, null);
//				e.render((Graphics2D)g);
//				g.drawImage(R.animations.mami.getImage().asBufferedImage(),20, 20, null);
			}
		});
		
		frame.pack();
		frame.setVisible(true);
		
	}
	
}
