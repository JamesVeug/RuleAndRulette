package phys;

public class AABB {
	
	Vec2 center;
	Vec2 extents;
	
	public AABB(float x, float y, float width, float height) {
		this.center = new Vec2(x, y);
		this.extents = new Vec2(width/2, height/2);
	}

}
