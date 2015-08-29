package phys;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import GUI.GUIPanel;
import GUI.PixelImage;
import GameLogic.Characters.Block;
import GameLogic.Characters.Entity;
import GameLogic.Characters.Rule;
import GameLogic.Characters.Rulette;
import Resources.R;

public class Physics implements ContactListener {
	
	public static final float SCALE = 100f;
	public static final float INVSCALE = 1/SCALE;
	
	public static final Vec2 DEFAULT_GRAVITY = new Vec2(0, 9.8f);
	
	public static final World world = new World(DEFAULT_GRAVITY);
	
//	private static HashMap<Entity, Integer> contactCounter = new HashMap<Entity, Integer>();
	
	public static final Physics instance = new Physics();
	
	public static Entity a = null;
	public static Entity b = null;
	
	
	private Physics() { 
		world.setContactListener(this);
	}
	
	public static HashSet<Entity> spawned = new LinkedHashSet<Entity>();
	
	public static void spawn(final float x, final float y, int numBoxes) {
		
		for(int i = 0; i < numBoxes; i++) {
			Entity entity = new PhysBox(x, y);
			entity.getBody().applyLinearImpulse(new Vec2(MathUtils.randomFloat(-1, 1), MathUtils.randomFloat(-1, 1)).mulLocal(0.2f), entity.getBody().getLocalCenter());
			spawned.add(entity);
		}
	}

	
	
	@Override
	public void beginContact(Contact contact) {
		Entity a = (Entity) contact.m_fixtureA.m_body.m_userData;
		Entity b = (Entity) contact.m_fixtureB.m_body.m_userData;
		
		if((a.getClass() == Rule.class && a.touching == 0)) { 
			GUIPanel.shake(5f);
			Physics.a = a;
//			spawn(a.getPosition().x, a.getPosition().y, 20);
		}
		
		if(b.getClass() == Rulette.class && b.touching == 0) {
			GUIPanel.shake(5f);
			Physics.b = b;
//			spawn(a.getPosition().x, a.getPosition().y, 20);
		}
		
		a.touching++;
		b.touching++;
		
		
//		System.out.println("Begin Contact");
		
//		Physics.contactCounter.put(a, Physics.contactCounter.containsKey(a) ? Physics.contactCounter.get(a)+1 : 1);
//		Physics.contactCounter.put(b, Physics.contactCounter.containsKey(b) ? Physics.contactCounter.get(b)+1 : 1);
//		
//		if(a.getClass() == Rule.class) {
//			((Rule)a).setCanJump(true);
//		}
//		
//		if(b.getClass() == Rulette.class) {
//			((Rulette)b).setCanJump(true);
//		}
	}

	@Override
	public void endContact(Contact contact) {
		Entity a = (Entity) contact.m_fixtureA.m_body.m_userData;
		Entity b = (Entity) contact.m_fixtureB.m_body.m_userData;
		
		a.touching--;
		b.touching--;
		
//		System.out.println("End Contact");
		
//		Physics.contactCounter.put(a, Physics.contactCounter.containsKey(a) ? Physics.contactCounter.get(a)-1 : 0);
//		Physics.contactCounter.put(b, Physics.contactCounter.containsKey(b) ? Physics.contactCounter.get(b)-1 : 0);
//		
//		if(a.getClass() == Rule.class && Physics.contactCounter.get(a) == 0) {
//			((Rule)a).setCanJump(false);
//		}
//		
//		if(b.getClass() == Rulette.class && Physics.contactCounter.get(b) == 0) {
//			((Rulette)b).setCanJump(false);
//		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	
	
//	private static Set<Body> bodies = new HashSet<Body>();
//	
//	private static Map<Body, AABB> bounds = new HashMap<Body, AABB>();
//	
//	private static Vec2 gravity = new Vec2(0, 9.8f);
//	
//	private Physics() { }
//	
//	public static void setGravity(float x, float y) {
//		gravity.set(x, y);
//	}
//	
//	public static Body create(Vec2 position, boolean _static) {
//		Body body = new Body(position);
//		body._static = _static;
//		bodies.add(body);
//		bounds.put(body, new AABB(0, 0));
//		return body;
//	}
//	
//	public static boolean destroy(Body body) {
//		return bodies.remove(body);
//	}
//	
//	public static void createCollider(Body body, float width, float height) {
//		bounds.put(body, new AABB(width, height));
//	}
//	
//	public static void tick(float delta) {
//		for(Body body : bodies) {
//			if(!body._static) {
//				Vec2 oldPos = new Vec2(body.position);
//				
//				body.position.addLocal(body.velocity.mul(delta));
//				body.position.addLocal(gravity.mul(delta));
//				bounds.get(body).center.set(body.position);
//				
//				AABB me = bounds.get(body);
//				for(AABB aabb : bounds.values()) {
//					if(aabb != me) {
//						if(me.intersects(aabb)) {
//							body.position.set(oldPos);
//							break;
//						}
//					}
//				}
//			}
//		}
//		
//		
//		
//	}
	
	private static class PhysBox extends Entity {
		
		public static int SIZE = 16;
		
		private PixelImage square = new PixelImage(SIZE,SIZE);
		
		
		public PhysBox(float x, float y) {
			super(x, y, false);
			
			Graphics2D g = square.asBufferedImage().createGraphics();
			g.setColor(new Color((int)(Math.random()*Integer.MAX_VALUE)));
			g.drawRect(0, 0, SIZE-1, SIZE-1);
			g.dispose();
		}

		@Override
		public void update(float delta) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void render(Graphics2D g) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void render(PixelImage canvas) {
			PixelImage.blit(square, canvas, getX(), getY());
		}

		@Override
		public Rectangle2D getBounds() {
			return new Rectangle2D.Float(this.getPosition().x-SIZE/2, this.getPosition().y-SIZE/2, SIZE-1, SIZE-1);
		}
		
	}

}
