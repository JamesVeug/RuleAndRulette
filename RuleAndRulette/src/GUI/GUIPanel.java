package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JPanel;

import GameLogic.Game;
import GameLogic.Characters.Entity;

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
		Graphics2D g = (Graphics2D)gold;
		
		// I DRAW STUFF HERE
		List<Entity> entities = game.getEntities();
		if(entities == null ){
			return;
		}
		
		for(int i = 0; i < entities.size(); i++){
			entities.get(i).render(g);
		}
		
		
		
	};

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
		
	}
	
	
}
