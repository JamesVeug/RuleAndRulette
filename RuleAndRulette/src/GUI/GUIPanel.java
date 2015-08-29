package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.locks.LockSupport;

import javax.swing.JPanel;

import org.jbox2d.common.Vec2;

import GameLogic.Game;
import GameLogic.Input;
import Resources.R;

public class GUIPanel extends JPanel {
	private static final long serialVersionUID = -2946375771339622409L;

	private static Vec2 camera = new Vec2();

	public static void shake(final float intensity) {
		new Thread(new Runnable() {
			public void run() {
				Vec2 dest = new Vec2();

				for (int i = 0; i < 5; i++) {
					dest.x = (float) (-1 + Math.random() * 2);
					dest.y = (float) (-1 + Math.random() * 2);

					dest.normalize();
					dest.mulLocal(intensity);

					for (float t = 0; t < 1; t += 0.07f) {
						camera.x = t * camera.x + (1 - t) * dest.x;
						camera.y = t * camera.y + (1 - t) * dest.y;
						LockSupport.parkNanos(100000);
					}
				}

				camera.setZero();
			}
		}).start();
	}

	private Game game;

	private PixelImage canvas;

	public GUIPanel(PixelImage canvas) {
		setBackground(Color.gray);
		this.addKeyListener(Input.instance);
		this.canvas = canvas;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public void paintComponent(Graphics gold) {
		super.paintComponent(gold);
		Graphics2D g = (Graphics2D) gold;

		//level
		Graphics2D g2d = canvas.asBufferedImage().createGraphics();
		g2d.setFont(new Font(R.fonts.kenpixel_mini_square.getName(), Font.PLAIN, 30));
		g2d.setColor(Color.black);
		g2d.drawString("Level: " + game.getCurrentLevel(), 38, 60);
		g2d.dispose();
		
		if( game.getStatus() == Game.GAMESTATUS_WON ){
			drawWonStatus(canvas);
		}
		
		g.drawImage(canvas.asBufferedImage(), null, (int) (camera.x), (int) (camera.y));
	};

	private void drawWonStatus(PixelImage canvas) {
		Graphics2D g = canvas.asBufferedImage().createGraphics();
		
		Point2D centerOfScreen = new Point2D.Double(canvas.getWidth()/2, canvas.getHeight()/2);  //new Point2D.Double(((int)this.getWidth()/2), ((int)(this.getHeight()/2)));
		

		BufferedImage heart = R.gui.heart.asBufferedImage();
		g.drawImage(heart, ((int)centerOfScreen.getX()-heart.getWidth()/2), ((int)(centerOfScreen.getY()/2)-heart.getHeight()/2), null);
		
		g.setFont(new Font(R.fonts.kenpixel_mini_square.getName(), Font.PLAIN, 60));
		g.setColor(Color.black);
		Rectangle2D bounds = g.getFontMetrics().getStringBounds("Success", g);
		int x = (int) (centerOfScreen.getX() - bounds.getWidth()/2);
		int y = (int) (centerOfScreen.getY() + bounds.getHeight()/4);
		g.drawString("Success", x, y);
		g.dispose();
	}

//	private void drawUI(Graphics2D g) {
		// Draw which level we are on
//		BufferedImage levelImage = R.gui.level.asBufferedImage();
//		g.drawImage(levelImage, null, 0, 0);
//		BufferedImage[] levelNumbers = getCurrentLevelImage();
//		int heightDiff = Math.abs(levelImage.getHeight() / 2
//				- levelNumbers[0].getHeight() / 2);
//		int y = levelImage.getMinY() + heightDiff;
//		for (int i = 0; i < levelNumbers.length; i++) {
//			g.drawImage(levelNumbers[i], null, levelImage.getMinX()
//					+ levelImage.getWidth() + (i * levelNumbers[0].getWidth()),
//					y);
//		}
		
//		String level = "Level: " + game.getCurrentLevel();
//		
//		g.setFont(new Font(R.fonts.kenpixel_mini_square.getName(), Font.PLAIN, 30));
//		g.setColor(Color.black);
//		Rectangle2D bounds = g.getFontMetrics().getStringBounds(level, g);
////		int x = (int) (centerOfScreen.getX() - bounds.getWidth()/2);
////		int y = (int) (centerOfScreen.getY() + bounds.getHeight()/4);
//		g.drawString(level, 38, 60);
//	}

//	private BufferedImage[] getCurrentLevelImage() {
//		String string = String.valueOf(game.getCurrentLevel());
//		int numbers = string.length();
//		BufferedImage[] images = new BufferedImage[numbers];
//		for (int i = 0; i < numbers; i++) {
//			int insideNumber = Integer
//					.parseInt(String.valueOf(string.charAt(i)));
//			switch (insideNumber) {
//			case 1:
//				images[i] = R.gui.one.asBufferedImage();
//				break;
//			case 2:
//				images[i] = R.gui.two.asBufferedImage();
//				break;
//			case 3:
//				images[i] = R.gui.three.asBufferedImage();
//				break;
//			case 4:
//				images[i] = R.gui.four.asBufferedImage();
//				break;
//			case 5:
//				images[i] = R.gui.five.asBufferedImage();
//				break;
//			case 6:
//				images[i] = R.gui.six.asBufferedImage();
//				break;
//			case 7:
//				images[i] = R.gui.seven.asBufferedImage();
//				break;
//			case 8:
//				images[i] = R.gui.eight.asBufferedImage();
//				break;
//			case 9:
//				images[i] = R.gui.nine.asBufferedImage();
//				break;
//			case 0:
//				images[i] = R.gui.zero.asBufferedImage();
//			}
//		}
//		return images;
//	}

}
