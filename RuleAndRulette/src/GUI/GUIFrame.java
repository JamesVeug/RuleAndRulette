package GUI;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import GameLogic.Game;

public class GUIFrame extends JFrame {
	private static final long serialVersionUID = -1822196509785001746L;

	GUIPanel entireScreenPanel; 
	Game game;

	private Timer gameTimer;
	
	public GUIFrame(){
		super();
		
		entireScreenPanel = new GUIPanel();
		add(entireScreenPanel);
		
		
		
		setSize(1024,600);
		setVisible(true);
		entireScreenPanel.requestFocus();
		
		setupGame();
	}
	
	/**
	 * Sets up a new game
	 */
	public void setupGame(){
		game = new Game();
		
		entireScreenPanel.setGame(game);
		
		game.startGame();
		
		gameTimer = new Timer();
		gameTimer.schedule(new TimerTask(){

			@Override
			public void run() {
				game.gameInteration(System.currentTimeMillis());
				entireScreenPanel.repaint();
			}			
		}, 0, 20);
	}
}
