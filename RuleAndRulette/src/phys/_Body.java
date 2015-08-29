package phys;

import org.jbox2d.common.Vec2;

public class _Body {
	
	Vec2 position;
	Vec2 velocity;
	
	public boolean _static;
	
	_Body() {
		this(new Vec2());
	}
	
	_Body(Vec2 position) {
		this.position = new Vec2(position);
		this.velocity = new Vec2();
	}
	
	public int getX() {
		return (int)position.x;
	}
	
	public int getY() {
		return (int)position.y;
	}

}
