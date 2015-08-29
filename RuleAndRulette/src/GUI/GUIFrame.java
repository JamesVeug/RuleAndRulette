package GUI;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import GameLogic.Game;
import Main.GameLoop;

public class GUIFrame extends JFrame {
	private static final long serialVersionUID = -1822196509785001746L;

	private GUIPanel entireScreenPanel; 
	private Game game;
	
	private static final int STATES_MENU = 0;
	private static final int STATES_GAME = 1;
	
	private int STATE = STATES_MENU;

	
	public GUIFrame(){
		super();
		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		
		entireScreenPanel = new GUIPanel();
		add(entireScreenPanel);
		
		JPanel customPanel = new JPanel(){
			@Override
			public void paintComponent(Graphics g){
				if( STATE == STATES_MENU ){
					drawMenu(g);
				}
				else if( STATE == STATES_GAME ){
					drawGame(g);
				}
			}

			private void drawGame(Graphics g) {
				// TODO Auto-generated method stub
				
			}

			private void drawMenu(Graphics g) {
				// TODO Auto-generated method stub
				
			}
		};
		
		
		
		
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
		
		//starts the game loop
		GameLoop loop = new GameLoop(60, 25, game, entireScreenPanel);		
		loop.start();
	}
}
