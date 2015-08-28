package phys;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Physics {
	
	private static Set<Body> bodies = new HashSet<Body>();
	
	private static Map<Body, AABB> bounds = new HashMap<Body, AABB>();
	
	private static Vec2 gravity = new Vec2(0, 9.8f);
	
	private Physics() { }
	
	public static void setGravity(float x, float y) {
		gravity.set(x, y);
	}
	
	public static Body create(Vec2 position, boolean _static) {
		Body body = new Body(position);
		body._static = _static;
		bodies.add(body);
		bounds.put(body, new AABB(0, 0));
		return body;
	}
	
	public static boolean destroy(Body body) {
		return bodies.remove(body);
	}
	
	public static void createCollider(Body body, float width, float height) {
		bounds.put(body, new AABB(width, height));
	}
	
	public static void tick(float delta) {
		for(Body body : bodies) {
			if(!body._static) {
				Vec2 oldPos = new Vec2(body.position);
				
				body.position.addLocal(body.velocity.mul(delta));
				body.position.addLocal(gravity.mul(delta));
				bounds.get(body).center.set(body.position);
				
				AABB me = bounds.get(body);
				for(AABB aabb : bounds.values()) {
					if(aabb != me) {
						if(me.intersects(aabb)) {
							body.position.set(oldPos);
							break;
						}
					}
				}
			}
		}
		
		
		
	}

}
