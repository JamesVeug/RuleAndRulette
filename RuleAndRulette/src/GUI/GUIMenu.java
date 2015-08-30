package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import visual.AnimatedSprite;
import Resources.R;

@SuppressWarnings("serial")
public class GUIMenu extends GUIPanel implements KeyListener, MouseListener, MouseMotionListener{
	private static final Font titleFont = new Font(R.fonts.kenpixel_mini_square.getName(), Font.BOLD, 45);
	private static final Font subTitleFont = new Font(R.fonts.kenpixel_mini_square.getName(), Font.ITALIC, 20);
	
	// Buttons on the screen
	List<GUIButton> buttons = new ArrayList<>();
	
	// numbers represent their positions in the list
	public static final int BUTTON_STARTGAME = 0;
	public static final int BUTTON_QUIT = 1;
	
	
	private AnimatedSprite rule = R.animations.rule.idle.clone();
	private AnimatedSprite rulette = R.animations.rulette.idle.clone();
	
	
	private GUIFrame frame;

	public GUIMenu(final GUIFrame frame){
		super();
		setBackgroundColor(Color.white);
		
		buttons.add(new GUIButton("Start Game",0,0,300,50));
		buttons.add(new GUIButton("Quit",0,0,200,50));
		this.frame = frame;
		
		new Thread(new Runnable() {
			public void run() {
				float delta = 1/60f;
				for(;;) {
					rule.update(delta);
					rulette.update(delta);
					frame.repaint();

					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Override
	public void paintComponent(Graphics gOld, int width, int height) {
		Graphics2D g = (Graphics2D)gOld;
		
		drawTitle(g,width,height);
		
		drawButtons(g,width,height);
		
		g.drawImage(rule.getImage().getScaledInstance(6).asBufferedImage(), null, 30, 30);
		g.drawImage(rulette.getImage().getScaledInstance(6).asBufferedImage(), null, (int)(g.getClipBounds().getWidth()-R.characters.rulette.getWidth()*6-30), 30);
		
		g.drawImage(R.controls.controls.getScaledInstance(2).asBufferedImage(), null, (int)(g.getClipBounds().getWidth()/2-R.controls.controls.getWidth()*2/2), (int)(g.getClipBounds().getHeight()-R.controls.controls.getHeight()*2-10));
	}

	private void drawTitle(Graphics2D g, int width, int height) {
		g.setFont(titleFont);
		String title = "Rule & Rulette";
		Rectangle2D bounds = g.getFontMetrics().getStringBounds(title,g);
		int textX = ((int)(width/2-bounds.getWidth()/2));
		int textY = ((int)(bounds.getHeight()));

		// Title Border
		if( frame.getGame() != null ){
			g.setColor(Color.white);
			g.fillRect(textX-10,(int)(textY-20-bounds.getHeight()/2),((int)bounds.getWidth()+20), ((int)bounds.getHeight()/2+40));
			
			g.setColor(Color.black);
			g.drawRect(textX-10,(int)(textY-20-bounds.getHeight()/2),((int)bounds.getWidth()+20), ((int)bounds.getHeight()/2+40));
		}
		
		// Title Text
		g.setColor(Color.black);
		g.drawString(title, textX, textY);
		
		
		g.setFont(subTitleFont);
		
		
		
		String subTile = "By Hamish Rae";
		bounds = g.getFontMetrics().getStringBounds(subTile,g);
		int subTextX = ((int)(width/2-bounds.getWidth()/2));
		int subTextY = ((int)(textY+bounds.getHeight()*2));
		
		
		String subTile2 = "and James Veugelaers";
		Rectangle2D bounds2 = g.getFontMetrics().getStringBounds(subTile2,g);
		int subTextX2 = (int) (subTextX+bounds.getWidth()/2-bounds2.getWidth()/2);
		int subTextY2 = (int) (subTextY+bounds.getHeight());

		if( frame.getGame() != null ){
			g.setColor(Color.white);
			g.fillRect(subTextX2-10, (int)(subTextY-bounds.getHeight()/2-10), (int)bounds2.getWidth()+20, 60);
		
			g.setColor(Color.black);
			g.drawRect(subTextX2-10, (int)(subTextY-bounds.getHeight()/2-10), (int)bounds2.getWidth()+20, 60);
		}
		
		g.setColor(Color.black);
		g.drawString(subTile, subTextX, subTextY);		
		g.drawString(subTile2, subTextX2, subTextY2);
	}

	private void drawButtons(Graphics2D g, int width, int height) {
		
		buttons.get(0).setY(height/2);
		buttons.get(0).setX(width/2);
		buttons.get(0).drawButton(g);
		buttons.get(0).setText(frame.getGame() == null ? "Start Game" : "Stop Game");
		
		for( int i = 1; i < buttons.size(); i++ ){
			GUIButton last = buttons.get(i-1);
			GUIButton b = buttons.get(i);
			b.setX(last.getX());
			b.setY(last.getY() + last.getHeight()*2);
			b.drawButton(g);
		}
	}
	
	private void buttonPressed(int index){
		switch(index){
			case BUTTON_STARTGAME:
				if( frame.getGame() == null ){
					frame.startGame();
				}
				else{
					frame.stopGame();					
				}
				break;
			case BUTTON_QUIT:
				frame.quit();
				break;		
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		/*if( frame.getState() == GUIFrame.STATES_GAME ){
			return;
		}
		
		boolean changed = false;
		
		for( GUIButton b : buttons){
			boolean sg = b.setHovering(b.onButton(e.getX(), e.getY()));
			changed = changed != true ? sg : true;
		}
		
		if( changed ){
			frame.repaint();
		}*/
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if( frame.getState() == GUIFrame.STATES_GAME ){
			return;
		}
		
		for( int i = 0; i < buttons.size(); i++ ){
			if( buttons.get(i).onButton(e.getX(), e.getY()) ){
				buttonPressed(i);
				break;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if( e.getKeyCode() == KeyEvent.VK_ESCAPE && frame.getGame() != null ){
			if( frame.getState() == GUIFrame.STATES_GAME ){
				frame.returnToMenu();
			}
			else{
				frame.returnToGame();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
