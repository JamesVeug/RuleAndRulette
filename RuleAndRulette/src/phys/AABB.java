package phys;

import org.jbox2d.common.Vec2;

public class AABB {
	
	public Vec2 center;
	public Vec2 extents;
	
	public AABB(float width, float height) {
		this.center = new Vec2();
		this.extents = new Vec2(width/2f, height/2f);
	}
	
	public AABB(float centerX, float centerY, float width, float height) {
		center = new Vec2(centerX, centerY);
		extents = new Vec2(width/2f, height/2f);
	}
	
	public boolean intersects(AABB other) {
		Vec2 c = this.center.sub(other.center);
		Vec2 e = this.extents.add(other.extents);
		
		return Math.abs(c.x) < e.x && Math.abs(c.y) < e.y;
	}

}
