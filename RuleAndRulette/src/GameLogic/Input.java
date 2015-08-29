package GameLogic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;
import java.util.Set;

public class Input implements KeyListener, MouseListener, MouseMotionListener{
	
	private static Set<Integer> pressedKeys = new HashSet<Integer>();
	private static Set<Integer> pressedKeysOnce = new HashSet<Integer>();
	
	public static Input instance = new Input();
	
	// Mouse
	private static int currentMouseX = 0;
	private static int currentMouseY = 0;
	private static boolean isMousePressedDown = false;
	
	private Input() { }

	@Override
	public void keyTyped(KeyEvent e) { }

	@Override
	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode();
		if(!pressedKeys.contains(keycode)) {
			pressedKeys.add(keycode);
			pressedKeysOnce.add(keycode);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {		
		int keycode = e.getKeyCode();
		if(pressedKeys.contains(keycode)) {
			pressedKeys.remove(keycode);
			pressedKeysOnce.remove(keycode);
		}
	}
	
	public static boolean isKeyDown(int keycode) {
		return pressedKeys.contains(keycode);
	}
	
	public static boolean isKeyDownOnce(int keycode) {
		return pressedKeysOnce.remove(keycode);
	}
	
	public static void removeKey(int keycode) {
		pressedKeys.remove(keycode);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		currentMouseX = e.getX();
		currentMouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		isMousePressedDown = true;
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		isMousePressedDown = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public boolean isMousePressedDown(){
		return isMousePressedDown;
	}
	
	public int getMouseX(){
		return currentMouseX;
	}
	
	public int getMouseY(){
		return currentMouseY;
	}
}
