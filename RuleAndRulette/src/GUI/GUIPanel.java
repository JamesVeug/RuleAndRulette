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

public class GUIPanel extends JPanel implements KeyListener{
	private static final long serialVersionUID = -2946375771339622409L;
	
	private static Vec2 camera = new Vec2();
	
	public static void shake(final float intensity) {
		new Thread(new Runnable() {
			public void run() {
				Vec2 dest = new Vec2();
				
				for(int i = 0; i < 5; i++) {
					dest.x = (float) (-1 + Math.random()*2);
					dest.y = (float) (-1 + Math.random()*2);
					
					dest.normalize();
					dest.mulLocal(intensity);
					
					for(float t = 0; t < 1; t+=0.07f) {
						camera.x = t*camera.x + (1-t)*dest.x;
						camera.y = t*camera.y + (1-t)*dest.y;
						LockSupport.parkNanos(100000);
					}
				}
				
				camera.setZero();
			}
		}).start();		
	}
	
	private Game game;
	
	private PixelImage canvas;

	public GUIPanel(PixelImage canvas){
		setBackground(Color.gray);
		this.addKeyListener(this);
		
		this.addKeyListener(Input.instance);
		this.canvas = canvas;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	@Override
	public void paintComponent(Graphics gold) {
		super.paintComponent(gold);
		Graphics2D g = (Graphics2D)gold;
		
//		if( game == null ){
//			return;
//		}
//		
//		// I DRAW STUFF HERE
//		List<Entity> entities = game.getEntities();
//		if(entities == null ){
//			return;
//		}
//		
//		for(int i = 0; i < entities.size(); i++){
//			entities.get(i).render(canvas);
//		}
		
		g.drawImage(canvas.asBufferedImage(), null, (int)(camera.x), (int)(camera.y));
		
		
		drawUI(g);
	};

	private void drawUI(Graphics2D g) {
		// Draw which level we are on
		BufferedImage levelImage = R.gui.level.asBufferedImage();
		g.drawImage(levelImage, null, 0,0);
		BufferedImage[] levelNumbers = getCurrentLevelImage();
		
		int heightDiff = Math.abs(levelImage.getHeight()/2-levelNumbers[0].getHeight()/2);
		int y = levelImage.getMinY()+heightDiff;
		for(int i = 0; i < levelNumbers.length; i++){
			g.drawImage(levelNumbers[i], null, levelImage.getMinX()+levelImage.getWidth()+(i*levelNumbers[0].getWidth()),y);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if( e.getKeyCode() == KeyEvent.VK_D ){
			//System.out.println("Start moving right");
			game.startMovingCharacterRight();
		}
		else if( e.getKeyCode() == KeyEvent.VK_A ){
			//System.out.println("Start moving left");
			game.startMovingCharacterLeft();
		}
		else if( e.getKeyCode() == KeyEvent.VK_W ){
			//System.out.println("Start moving UP");
			game.startMovingCharacterUp();
		}
		else if( e.getKeyCode() == KeyEvent.VK_S ){
			//System.out.println("Start moving Down");
			game.startMovingCharacterDown();
		}
		
		switch(e.getKeyCode()) {
		case KeyEvent.VK_SPACE :
			game.jumpMovingCharacter();
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if( e.getKeyCode() == KeyEvent.VK_D ){
			//System.out.println("STOP moving right");
			game.stopMovingCharacterRight();
		}
		else if( e.getKeyCode() == KeyEvent.VK_A ){
			//System.out.println("STOP moving left");
			game.stopMovingCharacterLeft();
		}
		else if( e.getKeyCode() == KeyEvent.VK_W ){
			//System.out.println("STOP moving UP");
			game.stopMovingCharacterUp();
		}
		else if( e.getKeyCode() == KeyEvent.VK_S ){
			//System.out.println("STOP moving Down");
			game.stopMovingCharacterDown();
		}
		
	}
	
	
}
