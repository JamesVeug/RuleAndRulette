package phys;

public class Body {
	
	Vec2 position;
	Vec2 velocity;
	
	public boolean _static;
	
	Body() {
		this(new Vec2());
	}
	
	Body(Vec2 position) {
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
