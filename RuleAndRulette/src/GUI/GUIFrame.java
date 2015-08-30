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

	private JPanel mainPanel;
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
		
		mainPanel = new JPanel(){
			
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
		mainPanel.addKeyListener(menuPanel);
		mainPanel.addMouseMotionListener(Input.instance);
		mainPanel.addMouseListener(Input.instance);
		add(mainPanel);
		
		setSize(1024,600);
		setVisible(true);
		mainPanel.requestFocus();
		setState(STATES_MENU);
	}
	
	public void setState(int newState){
		this.STATE = newState;
		
		if( newState == STATES_MENU ){
			mainPanel.addMouseMotionListener(menuPanel);
			mainPanel.addMouseListener(menuPanel);
			
			mainPanel.removeMouseListener(gamePanel);
			mainPanel.removeKeyListener(Input.instance);
		}else if( newState == STATES_GAME ){
			mainPanel.removeMouseMotionListener(menuPanel);
			mainPanel.removeMouseListener(menuPanel);
			
			mainPanel.addMouseListener(gamePanel);	
			mainPanel.addKeyListener(Input.instance);		
		}
	}
	
	/**
	 * Sets up a new game
	 */
	public void setupGame(){
		game = new Game();
		
		gamePanel.setGame(game);
		
		game.startGame();
		
		//starts the game loop
		gameLoop = new GameLoop(30, 25, game, gamePanel, this);		
		gameLoop.start();
	}

	/**
	 * Called from within the menu
	 * Starts a new game
	 */
	public void startGame() {
		setupGame();
		setState(STATES_GAME);
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
		setState(STATES_MENU);
	}

	public void returnToGame() {
		setState(STATES_GAME);
	}

	public void stopGame() {

		// Stops the game loop		
		gameLoop.stopGameLoop();
		while(gameLoop.isAlive()){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		game.stopGame();
		
		gamePanel.setGame(null);
		
		game = null;
	}
}
