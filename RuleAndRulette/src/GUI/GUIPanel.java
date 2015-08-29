package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

import javax.swing.JPanel;

import org.jbox2d.common.Vec2;

import GameLogic.Game;
import GameLogic.Input;
import GameLogic.Characters.Entity;
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

		// if( game == null ){
		// return;
		// }
		//
		// // I DRAW STUFF HERE
		// List<Entity> entities = game.getEntities();
		// if(entities == null ){
		// return;
		// }
		//
		// for(int i = 0; i < entities.size(); i++){
		// entities.get(i).render(canvas);
		// }

		g.drawImage(canvas.asBufferedImage(), null, (int) (camera.x),
				(int) (camera.y));

		drawUI(g);
	};

	private void drawUI(Graphics2D g) {
		// Draw which level we are on
		BufferedImage levelImage = R.gui.level.asBufferedImage();
		g.drawImage(levelImage, null, 0, 0);
		BufferedImage[] levelNumbers = getCurrentLevelImage();
		int heightDiff = Math.abs(levelImage.getHeight() / 2
				- levelNumbers[0].getHeight() / 2);
		int y = levelImage.getMinY() + heightDiff;
		for (int i = 0; i < levelNumbers.length; i++) {
			g.drawImage(levelNumbers[i], null, levelImage.getMinX()
					+ levelImage.getWidth() + (i * levelNumbers[0].getWidth()),
					y);
		}
	}

	private BufferedImage[] getCurrentLevelImage() {
		String string = String.valueOf(game.getCurrentLevel());
		int numbers = string.length();
		BufferedImage[] images = new BufferedImage[numbers];
		for (int i = 0; i < numbers; i++) {
			int insideNumber = Integer
					.parseInt(String.valueOf(string.charAt(i)));
			switch (insideNumber) {
			case 1:
				images[i] = R.gui.one.asBufferedImage();
				break;
			case 2:
				images[i] = R.gui.two.asBufferedImage();
				break;
			case 3:
				images[i] = R.gui.three.asBufferedImage();
				break;
			case 4:
				images[i] = R.gui.four.asBufferedImage();
				break;
			case 5:
				images[i] = R.gui.five.asBufferedImage();
				break;
			case 6:
				images[i] = R.gui.six.asBufferedImage();
				break;
			case 7:
				images[i] = R.gui.seven.asBufferedImage();
				break;
			case 8:
				images[i] = R.gui.eight.asBufferedImage();
				break;
			case 9:
				images[i] = R.gui.nine.asBufferedImage();
				break;
			case 0:
				images[i] = R.gui.zero.asBufferedImage();
			}
		}
		return images;
	}

}
