package phys;

public class Body {
	
	Vec2 position;
	Vec2 velocity;
	
	private float width;
	private float height;
	
	Body(Vec2 position, float width, float height) {
		this.position = new Vec2();
		this.velocity = new Vec2();
		
		this.width = width;
		this.height = height;
	}
	
	public AABB getAABB() {
		return new AABB(position.x, position.y, width, height);
	}

}
