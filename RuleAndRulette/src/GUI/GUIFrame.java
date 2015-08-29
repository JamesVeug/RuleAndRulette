package GUI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import GameLogic.Game;
import GameLogic.Input;
import Main.GameLoop;

public class GUIFrame extends JFrame {
	private static final long serialVersionUID = -1822196509785001746L;

	private GUIGame gamePanel;
	private GUIMenu menuPanel;
	private Game game;
	private GameLoop gameLoop;
	
	public static final int STATES_MENU = 0;
	public static final int STATES_GAME = 1;
	
	private int STATE = STATES_MENU;

	
	public GUIFrame(){
		super();
		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		
		gamePanel = new GUIGame();
		menuPanel = new GUIMenu(this);
		
		JPanel customPanel = new JPanel(){
			
			@Override
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				if( game != null ){
					
					// Draw game if we can
					drawGame(g);
				}
				
				if( STATE != STATES_GAME ){
					
					// Fade the game
					if( game != null ){
						g.setColor(new Color(1f,1f,1f,0.5f));
						g.fillRect(0, 0, gamePanel.getCanvas().getWidth(), gamePanel.getCanvas().getHeight());
					}
					
					// Draw Menu
					drawMenu(g);
				}
			}

			private void drawGame(Graphics g) {
				setBackground(gamePanel.getBackgroundColor());
				gamePanel.paintComponent(g, getWidth(), getHeight());
				
			}

			private void drawMenu(Graphics g) {
				setBackground(menuPanel.getBackgroundColor());
				menuPanel.paintComponent(g, getWidth(), getHeight());
			}
		};
		customPanel.addKeyListener(Input.instance);
		//customPanel.addKeyListener(this);
		customPanel.addMouseMotionListener(Input.instance);
		customPanel.addMouseListener(Input.instance);
		customPanel.addKeyListener(menuPanel);
		customPanel.addMouseMotionListener(menuPanel);
		customPanel.addMouseListener(menuPanel);
		add(customPanel);
		
		setSize(1024,600);
		setVisible(true);
		customPanel.requestFocus();
	}
	
	/**
	 * Sets up a new game
	 */
	public void setupGame(){
		game = new Game();
		
		gamePanel.setGame(game);
		
		game.startGame();
		
		//starts the game loop
		GameLoop gameLoop = new GameLoop(60, 25, game, gamePanel, this);		
		gameLoop.start();
	}

	/**
	 * Called from within the menu
	 * Starts a new game
	 */
	public void startGame() {
		setupGame();
		STATE = STATES_GAME;
	}
	
	public Game getGame(){
		return game;
	}
	
	public int getState(){
		return STATE;
	}

	/**
	 * Called from within the menu
	 */
	public void quit() {
		System.exit(0);
	}

	public void returnToMenu() {
		STATE = STATES_MENU;
	}

	public void returnToGame() {
		STATE = STATES_GAME;
	}

	public void stopGame() {
		
		/*gamePanel.setGame(null);
		
		game.stopGame();
		
		//starts the game loop
		GameLoop loop = new GameLoop(60, 25, game, gamePanel, this);		
		loop.start();
		

		game = new Game();*/
	}
}
