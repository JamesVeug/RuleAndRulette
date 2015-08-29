package GameLogic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class Input implements KeyListener {
	
	private static Set<Integer> pressedKeys = new HashSet<Integer>();
	private static Set<Integer> pressedKeysOnce = new HashSet<Integer>();
	
	public static Input instance = new Input();
	
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

}
