package Resources;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
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
	
	public static PixelImage logo = loadPixelImage("assets/logo.png");
	
	public static class fonts {
		public static Font kenpixel_mini_square;
		static {
			try {
				kenpixel_mini_square = Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/kenpixel_mini_square.ttf"));
				GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(kenpixel_mini_square);
			} catch (FontFormatException | IOException e) {
				System.err.println("Could not load font: " + e.getMessage());
				e.printStackTrace();
			} 
		}
	}
	
	public static class characters {
		public static PixelImage rule = loadPixelImage("assets/characters/rule.jpg");
		public static PixelImage rulette = loadPixelImage("assets/characters/rulette.jpg");
	}
	
	public static class environment {
		public static PixelImage block = loadPixelImage("assets/environment/block.png");
		public static PixelImage spikes = loadPixelImage("assets/environment/spikes.png");
		public static PixelImage heart = loadPixelImage("assets/environment/heart.png");
		public static PixelImage level = loadPixelImage("assets/environment/level.png");
	}
	
	public static class gui {
		public static PixelImage level = loadPixelImage("assets/gui/Level.png");
		public static PixelImage zero = loadPixelImage("assets/gui/zero.png");
		public static PixelImage one = loadPixelImage("assets/gui/one.png");
		public static PixelImage two = loadPixelImage("assets/gui/two.png");
		public static PixelImage three = loadPixelImage("assets/gui/three.png");
		public static PixelImage four = loadPixelImage("assets/gui/four.png");
		public static PixelImage five = loadPixelImage("assets/gui/five.png");
		public static PixelImage six = loadPixelImage("assets/gui/six.png");
		public static PixelImage seven = loadPixelImage("assets/gui/seven.png");
		public static PixelImage eight = loadPixelImage("assets/gui/eight.png");
		public static PixelImage nine = loadPixelImage("assets/gui/nine.png");
		
//		public static PixelImage heart = loadPixelImage("assets/gui/heart.png");
		public static PixelImage heart = loadPixelImage("assets/environment/heart.png").getScaledInstance(5);
		public static PixelImage broken_heart = loadPixelImage("assets/gui/broken_heart.png");
		public static PixelImage success = loadPixelImage("assets/gui/success.png");
		public static PixelImage failed = loadPixelImage("assets/gui/failed.png");
		
		public static class volume {
			public static PixelImage mute = loadPixelImage("assets/gui/volume_mute.png");
			public static PixelImage unmute = loadPixelImage("assets/gui/volume_unmute.png");
		}
		
	}
	
	public static class sound{
		
		public static class music {
			
			public static String getRandom() {
				return bu_an_eating_candy_loud;
//				return new String[] {
//					bu_a_hunting_road,
//					bu_a_sea_and_branches,
//					bu_an_eating_candy
//				}[(int) (Math.random()*3)];
			}
			
			public static String bu_a_hunting_road = loadSoundURL("music/bu-a-hunting-road.wav");
			public static String bu_a_sea_and_branches = loadSoundURL("music/bu-a-sea-and-branches.wav");
			public static String bu_an_eating_candy = loadSoundURL("music/bu-an-eating-candy.wav");
			public static String bu_an_eating_candy_loud = loadSoundURL("music/bu-an-eating-candy-loud.wav");
		}
		
		public static class effects {
			public static String jump =  loadSoundURL("effects/jump.wav");
			public static String land_hard =  loadSoundURL("effects/land_hard.wav");
			public static String land_soft =  loadSoundURL("effects/land_soft.wav");
			public static String explosion =  loadSoundURL("effects/explosion.wav");
		}
		
//		public static String jump =  loadSoundURL("Jump.wav");
//		public static String land_hard =  loadSoundURL("land_hard.wav");
//		public static String land_soft =  loadSoundURL("land_soft.wav");
//		public static String explosion =  loadSoundURL("explosion.wav");
	}
	
	public static class animations {
		
		public static class rule {
			public static AnimatedSprite idle = new AnimatedSprite(loadPixelImage("assets/animations/rule/rule_idle_f8_w32_h32.png"), 8, 8);
			public static AnimatedSprite walk = new AnimatedSprite(loadPixelImage("assets/animations/rule/rule_walk_f7_w32_h32.png"), 7, 16);
			public static AnimatedSprite hurt = new AnimatedSprite(loadPixelImage("assets/animations/rule/rule_hurt_f1_w32_h32.png"), 1, 1);
		}
		
		public static class rulette {
			public static AnimatedSprite idle = new AnimatedSprite(loadPixelImage("assets/animations/rulette/rulette_idle_f8_w32_h32.png"), 8, 8);
			public static AnimatedSprite walk = new AnimatedSprite(loadPixelImage("assets/animations/rulette/rulette_walk_f7_w32_h32.png"), 7, 16);
			public static AnimatedSprite hurt = new AnimatedSprite(loadPixelImage("assets/animations/rulette/rulette_hurt_f1_w32_h32.png"), 1, 1);
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
	
//	private static PixelImage canvas;
//	
//	public static void main(String[] args) {		
//		final JFrame frame = new JFrame();
//		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//		
////		new Thread(new Runnable() {
////			public void run() {
////				for(;;) {
////					frame.repaint();
////					Physics.tick(1/60f);
////					R.animations.mami.update(1/60f);
////					LockSupport.parkNanos(100000);
////				}
////			}
////		}).start();
//				
//		
//		final PixelImage canvas1 = new PixelImage(1920, 1080);
//		final PixelImage canvas2 = new PixelImage(1920, 1080);
//		
//		canvas = canvas1;
//		
//		GameLoop loop = new GameLoop(60, 50) {
//
//			List<Entity> entities = new ArrayList<Entity>();
//			
////			{
////				for(int i = 0; i < 100; i++) {
////					float x = (float)(Math.random()*1920);
////					float y = (float)(Math.random()*1080);
////					entities.add(Math.random() < 0.5f ? new Rule(x, y) : new Rulette(x, y));
////				}
////			}
//			
//			@Override
//			protected void tick(float delta) {
//				for(int i = 0; i < 5; i++) {
//					float x = (float)(Math.random()*1920);
//					float y = (float)(Math.random()*1080);
//					entities.add(Math.random() < 0.5f ? new Rule(x, y) : new Rulette(x, y));
//				}
//				
//				for(Iterator<Entity> iter = entities.iterator(); iter.hasNext();) {
//					Entity e = iter.next();
//					e.update(delta);
//					if(e.getY() > 1080) {
//						iter.remove();
//					}
//				}
//			}
//
//			@Override
//			protected void fixedTick(float delta) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			protected void render() {
//				canvas.clear();
//				for(Entity e : entities) {
//					e.render(canvas);
//				}
//
//				frame.repaint();
//			}
//			
//		};
//		
//		loop.start();
//		
//		
//		
//		
//		frame.add(new JComponent() {
//			{
//				this.setPreferredSize(new Dimension(1920, 1080));
//			}
//			protected void paintComponent(Graphics g) {
//				g.drawImage(canvas.asBufferedImage() ,0, 0, null);
////				e.render((Graphics2D)g);
////				g.drawImage(R.animations.mami.getImage().asBufferedImage(),20, 20, null);
//			}
//		});
//		
//		frame.pack();
//		frame.setVisible(true);
//		
//	}
	
}
