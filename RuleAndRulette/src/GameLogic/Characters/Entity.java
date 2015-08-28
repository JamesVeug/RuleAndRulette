package GameLogic.Characters;

import java.awt.Graphics2D;

import Resources.R;
import phys.Vec2;

public class Entity {
	
	public final int id = IDGenerator.next();
	
	private Vec2 position;
	private Vec2 velocity;
	
	public Entity() {
		position = new Vec2();
		velocity = new Vec2();
	}
	
	public int getX() {
		return (int) position.x;
	}
	
	public int getY() {
		return (int) position.y;
	}
	
	public void update(float delta) {
		position.addLocal(velocity);
	}
	
	public void render(Graphics2D g) {
		g.drawImage(R.logo.asBufferedImage(), null, getX(), getY());
	}
	
	private static class IDGenerator {
		private static int i = 0;
		public static int next() {
			return i++;
		}
	}

}
