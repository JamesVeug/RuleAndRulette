package phys;

import java.util.HashSet;
import java.util.Set;

public class Physics {
	
	private static Set<Body> bodies = new HashSet<Body>();
	
	private Physics() { }
	
	public static Body create(Vec2 position, float width, float height) {
		Body body = new Body(position, width, height);
		bodies.add(body);
		return body;
	}
	
	public static boolean destroy(Body body) {
		return bodies.remove(body);
	}
	
	public static void tick(float delta) {
		for(Body body : bodies) {
			body.position.addLocal(body.velocity.mul(delta));
		}
	}

}
