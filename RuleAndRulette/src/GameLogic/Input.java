package GameLogic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class Input implements KeyListener {
	
	private static Set<Integer> pressedKeys = new HashSet<Integer>();
	
	public static Input instance = new Input();
	
	private Input() { }

	@Override
	public void keyTyped(KeyEvent e) { }

	@Override
	public void keyPressed(KeyEvent e) {
		if(!pressedKeys.contains(e.getKeyCode())) {
			pressedKeys.add(e.getKeyCode());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(pressedKeys.contains(e.getKeyCode())) {
			pressedKeys.remove(e.getKeyCode());
		}
	}
	
	public static boolean isKeyDown(int keycode) {
		return pressedKeys.contains(keycode);
	}
	
	public static void removeKey(int keycode) {
		pressedKeys.remove(keycode);
	}

}
