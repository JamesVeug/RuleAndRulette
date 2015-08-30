package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.locks.LockSupport;

import javax.swing.JPanel;

import org.jbox2d.common.Vec2;

import GameLogic.Game;
import GameLogic.Input;
import GameLogic.Score;
import Resources.R;

public class GUIGame extends GUIPanel implements MouseListener {
	private static final long serialVersionUID = -2946375771339622409L;

	private static Vec2 camera = new Vec2();
	private static Point2D SOUND_UIPOSITION = new Point2D.Double(800, 32);
	private static Point2D MUSIC_UIPOSITION = new Point2D.Double(896, 32);
	
	public static final Object CANVAS_LOCK = new Object();

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


	public GUIGame() {
		super();
	}

	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public void paintComponent(Graphics gold, int width, int height) {
		Graphics2D g = (Graphics2D) gold;

		//level
//		drawUI(g);
//		
//		drawStatus(getCanvas());
		synchronized(CANVAS_LOCK) {
			g.drawImage(getCanvas().asBufferedImage(), null, (int) (camera.x), (int) (camera.y));
		}
		
	};
	
	public void __RENDER() {
		drawUI();
		
		drawStatus(getCanvas());
	}
	
	private void drawStatus(PixelImage canvas){
		if( game.getStatus() == Game.GAMESTATUS_WON ){
			drawWonStatus(canvas);
		} else if( game.getStatus() == Game.GAMESTATUS_FAILED ){
			drawFailStatus(canvas);
		}
	}

	private void drawWonStatus(PixelImage canvas) {
		Graphics2D g = canvas.asBufferedImage().createGraphics();
		
		Point2D centerOfScreen = new Point2D.Double(canvas.getWidth()/2, canvas.getHeight()/2);  //new Point2D.Double(((int)this.getWidth()/2), ((int)(this.getHeight()/2)));
		

		BufferedImage heart = R.gui.heart.asBufferedImage();
		g.drawImage(heart, ((int)centerOfScreen.getX()-heart.getWidth()/2), ((int)(centerOfScreen.getY()/1.6f)-heart.getHeight()/2), null);
		
		g.setFont(new Font(R.fonts.kenpixel_mini_square.getName(), Font.PLAIN, 60));
		g.setColor(Color.black);
		Rectangle2D bounds = g.getFontMetrics().getStringBounds("Success", g);
		int x = (int) (centerOfScreen.getX() - bounds.getWidth()/2);
		int y = (int) (centerOfScreen.getY() + bounds.getHeight()/2.1f);
		g.drawString("Success", x, y);
		g.dispose();
	}
	
	private void drawFailStatus(PixelImage canvas) {
		Graphics2D g = canvas.asBufferedImage().createGraphics();
		
		Point2D centerOfScreen = new Point2D.Double(canvas.getWidth()/2, canvas.getHeight()/2);

		BufferedImage brokenheart = R.gui.broken_heart.asBufferedImage();
		g.drawImage(brokenheart, ((int)centerOfScreen.getX()-brokenheart.getWidth()/2), ((int)(centerOfScreen.getY()/2)-brokenheart.getHeight()/2), null);
		
		g.setFont(new Font(R.fonts.kenpixel_mini_square.getName(), Font.PLAIN, 60));
		g.setColor(Color.black);
		Rectangle2D bounds = g.getFontMetrics().getStringBounds("Failed", g);
		int x = (int) (centerOfScreen.getX() - bounds.getWidth()/2);
		int y = (int) (centerOfScreen.getY() + bounds.getHeight()/4);
		g.drawString("Failed", x, y);
		g.dispose();
	}

	private void drawUI(){
		Graphics2D g2d = getCanvas().asBufferedImage().createGraphics();
		g2d.setFont(new Font(R.fonts.kenpixel_mini_square.getName(), Font.PLAIN, 30));
		
		// Level
		g2d.setColor(Color.black);
		String level = String.valueOf(game.getCurrentLevel());
		while( level.length() < 4){ level = "0"+level; }
		g2d.drawString("Level: " + level, 36, 56);
		
		// Score
		Rectangle2D textBounds = g2d.getFontMetrics().getStringBounds("Score: 9999999999999", g2d);
		
		g2d.setColor(Score.isDecreasing() ? Color.red : Color.black);
		int scoreX = (int) (getCanvas().getWidth()/2 - textBounds.getWidth()/2);
		int scoreY = 56;
		String string = String.valueOf(Score.getScore());
		while( string.length() < 13){ string = "0"+string; }
		g2d.drawString("Score: " + string, scoreX, scoreY);
		
		
		//
		// SOUND
		//
		
		// Clip Sound
		int clipSpeakerX = (int)SOUND_UIPOSITION.getX();
		int clipSpeakerY = (int)SOUND_UIPOSITION.getY();
		if( Sound.Sound.isClipsMuted() ){
			g2d.drawImage(R.gui.volume.mute.getScaledInstance(2).asBufferedImage(), clipSpeakerX, clipSpeakerY, null);
		}
		else{
			g2d.drawImage(R.gui.volume.unmute.getScaledInstance(2).asBufferedImage(), clipSpeakerX, clipSpeakerY, null);
		}
		int clipVolume = Math.round(Sound.Sound.getClipVolume()*100);

		g2d.setColor(Color.black);
		g2d.drawString(clipVolume + "%", clipSpeakerX+32, clipSpeakerY+26);
		
		// Music Sound
		int musicSpeakerX = (int)MUSIC_UIPOSITION.getX();
		int musicSpeakerY = (int)MUSIC_UIPOSITION.getY();
		if( Sound.Sound.isMusicMuted() ){
			g2d.drawImage(R.gui.volume.mute.getScaledInstance(2).asBufferedImage(), musicSpeakerX, musicSpeakerY, null);
		}
		else{
			g2d.drawImage(R.gui.volume.unmute.getScaledInstance(2).asBufferedImage(), musicSpeakerX, musicSpeakerY, null);
		}
		int musicVolume = Math.round(Sound.Sound.getMusicVolume()*100);

		g2d.setColor(Color.black);
		g2d.drawString(musicVolume + "%", musicSpeakerX+32, musicSpeakerY+26);
		
		
		
		g2d.dispose();		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if( e.getX() > MUSIC_UIPOSITION.getX() && e.getX() < MUSIC_UIPOSITION.getX()+32 && 
				e.getY() > MUSIC_UIPOSITION.getY() && e.getY() < MUSIC_UIPOSITION.getY()+32 ){
			if( Sound.Sound.isMusicMuted() ){
				Sound.Sound.unmuteMusic();
			}
			else{
				Sound.Sound.muteMusic();
			}			
		}
		else if( e.getX() > SOUND_UIPOSITION.getX() && e.getX() < SOUND_UIPOSITION.getX()+32 && 
				e.getY() > SOUND_UIPOSITION.getY() && e.getY() < SOUND_UIPOSITION.getY()+32 ){
			if( Sound.Sound.isClipsMuted() ){
				Sound.Sound.unmuteClips();
			}
			else{
				Sound.Sound.muteClips();
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

}
