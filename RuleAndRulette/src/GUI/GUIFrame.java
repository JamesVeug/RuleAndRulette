package GUI;

import javax.swing.JFrame;

import GameLogic.Game;

public class GUIFrame extends JFrame {
	private static final long serialVersionUID = -1822196509785001746L;

	GUIPanel entireScreenPanel; 
	Game game;
	
	public GUIFrame(){
		super();
		
		entireScreenPanel = new GUIPanel();
		add(entireScreenPanel);
		
		
		
		setSize(800,600);
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
	}
}
