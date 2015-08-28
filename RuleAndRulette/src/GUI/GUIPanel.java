package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

import GameLogic.Game;
import GameLogic.Characters.Entity;
import Resources.R;

public class GUIPanel extends JPanel implements KeyListener{
	private static final long serialVersionUID = -2946375771339622409L;
	
	
	private Game game;

	public GUIPanel(){
		setBackground(Color.gray);
		this.addKeyListener(this);
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	@Override
	public void paintComponent(Graphics gold) {
		super.paintComponent(gold);
		Graphics2D g = (Graphics2D)gold;
		
		if( game == null ){
			return;
		}
		
		// I DRAW STUFF HERE
		List<Entity> entities = game.getEntities();
		if(entities == null ){
			return;
		}
		
		for(int i = 0; i < entities.size(); i++){
			entities.get(i).render(g);
		}
		
		
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
	
	private BufferedImage[] getCurrentLevelImage(){
		String string = String.valueOf(game.getCurrentLevel());
		int numbers = string.length();
		BufferedImage[] images = new BufferedImage[numbers];
		
		for( int i = 0; i < numbers; i++){
			
			int insideNumber = Integer.parseInt(String.valueOf(string.charAt(i)));
			switch(insideNumber){
				case 1: images[i] = R.gui.one.asBufferedImage(); break;
				case 2: images[i] = R.gui.two.asBufferedImage();break;
				case 3: images[i] = R.gui.three.asBufferedImage();break;
				case 4: images[i] = R.gui.four.asBufferedImage();break;
				case 5: images[i] = R.gui.five.asBufferedImage();break;
				case 6: images[i] = R.gui.six.asBufferedImage();break;
				case 7: images[i] = R.gui.seven.asBufferedImage();break;
				case 8: images[i] = R.gui.eight.asBufferedImage();break;
				case 9: images[i] = R.gui.nine.asBufferedImage();break;
				case 0: images[i] = R.gui.zero.asBufferedImage();
			}
		}
		
		
		return images;
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
		else if( e.getKeyCode() == KeyEvent.VK_SPACE ){
			//System.out.println("STOP moving Down");
			game.jump();
		}
		
	}
	
	
}
