package phys;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
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
import org.jbox2d.dynamics.Body;
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
import Sound.Sound;

public class Physics implements ContactListener {
	
	public static final float SCALE = 100f;
	public static final float INVSCALE = 1/SCALE;
	
	public static final Vec2 DEFAULT_GRAVITY = new Vec2(0, 9.8f);
	
	public static final World world = new World(DEFAULT_GRAVITY);
	
//	private static HashMap<Entity, Integer> contactCounter = new HashMap<Entity, Integer>();
	
	public static final Physics instance = new Physics();
	
	public static boolean hearts = false;
	
	public static Entity a = null;
	public static Entity b = null;
	
	
	private Physics() { 
		world.setContactListener(this);
		
		
		
		BodyDef bdef = new BodyDef();
		bdef.type =  BodyType.STATIC;
		bdef.position.set((1024/2f)*INVSCALE, (576/2f)*INVSCALE);
		Body body = world.createBody(bdef);
		
		PolygonShape pshape = new PolygonShape();
		
		//top
		pshape.setAsBox((1024/2f)*INVSCALE, (32/2f)*INVSCALE, new Vec2(0, ((576/2f)-32/2f)*INVSCALE), 0);
		body.createFixture(pshape, 0);
		
		//bottom
		pshape.setAsBox((1024/2f)*INVSCALE, (32/2f)*INVSCALE, new Vec2(0, ((-(576/2f))+32/2f)*INVSCALE), 0);
		body.createFixture(pshape, 0);
		
		//left
		pshape.setAsBox((32/2f)*INVSCALE, (576/2f)*INVSCALE, new Vec2(((-(1024/2f))+32/2f)*INVSCALE, 0), 0);
		body.createFixture(pshape, 0);
		
		//right
		pshape.setAsBox((32/2f)*INVSCALE, (576/2f)*INVSCALE, new Vec2((((1024/2f))-32/2f)*INVSCALE, 0), 0);
		body.createFixture(pshape, 0);
	}
	
	public static HashSet<Entity> spawned = new LinkedHashSet<Entity>();
	
	public static void spawn(final float x, final float y, int numBoxes) {
		
		Sound.playSound(R.sound.effects.explosion);
		
		for(int i = 0; i < numBoxes; i++) {
			Entity entity = new PhysBox(x, y);
//			entity.getBody().applyLinearImpulse(new Vec2(MathUtils.randomFloat(-1, 1), MathUtils.randomFloat(-1, 1)).mulLocal(0.2f), entity.getBody().getLocalCenter());
			spawned.add(entity);
		}
	}
	
	public static void spawnHeart(final float x, final float y, int numBoxes) {
		
		Sound.playSound(R.sound.effects.explosion);
		
		for(int i = 0; i < numBoxes; i++) {
			Entity entity = new HeartBox(x, y);
//			entity.getBody().applyLinearImpulse(new Vec2(MathUtils.randomFloat(-1, 1), MathUtils.randomFloat(-1, 1)).mulLocal(0.2f), entity.getBody().getLocalCenter());
			spawned.add(entity);
		}
	}

	
	
	@Override
	public void beginContact(Contact contact) {
		Entity a = (Entity) contact.m_fixtureA.m_body.m_userData;
		Entity b = (Entity) contact.m_fixtureB.m_body.m_userData;
		
		if(a != null) {
		
			if(a.getClass() == Rule.class) {// || a.getClass() == Rulette.class) {
	//			System.out.println("A: " + a.getBody().getLinearVelocity().y);
				if(a.getBody().getLinearVelocity().length() > 2.5f) {
					Sound.playSound(R.sound.effects.land_hard);
				} else if (a.getBody().getLinearVelocity().length() > 1f) {
					Sound.playSound(R.sound.effects.land_soft);
				}
			}
			
			if((a.getClass() == Rule.class && a.touching == 0)) { 
				GUIPanel.shake(5f);
				Physics.a = a;
//				spawn(a.getPosition().x, a.getPosition().y, 20);
			}
			
			a.touching++;
			
		}
		
		if(b != null) {
		
			if(b.getClass() == Rule.class) {// || b.getClass() == Rulette.class) {
	//			System.out.println("B: " + b.getBody().getLinearVelocity().y);
				if(b.getBody().getLinearVelocity().length() > 2.5f) {
					Sound.playSound(R.sound.effects.land_hard);
				} else if (b.getBody().getLinearVelocity().length() > 1f) {
					Sound.playSound(R.sound.effects.land_soft);
				}
			}
			
			if(b.getClass() == Rulette.class && b.touching == 0) {
				GUIPanel.shake(5f);
				Physics.b = b;
	//			spawn(a.getPosition().x, a.getPosition().y, 20);
			}
			
			
			b.touching++;
			
		}
		
		
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
		
		if(a != null) {
			a.touching--;
		}
		
		if(b != null) {
			b.touching--;
		}
		
		
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
	
	public static interface IDeadable {
		public boolean isDead();
		public void setDead(boolean isDead);
	}
	
	public static class PhysBox extends Entity implements IDeadable {
		
		public static int SIZE = 16;
		public static float LIFETIME = 1.5f;
		
		private float lifetime = (float)(Math.random())*LIFETIME*1f;
		private float time = 0;
		
		private boolean isDead = false;
		
		private static PixelImage[] images = new PixelImage[100];
		static {
			for(int i = 0; i < images.length; i++) {
				images[i] = new PixelImage(SIZE, SIZE);
				Graphics2D g = images[i].asBufferedImage().createGraphics();
				g.setColor(new Color((int)(Math.random()*Integer.MAX_VALUE)));
				g.drawRect(0, 0, SIZE-1, SIZE-1);
				g.dispose();
//				images[i] = R.environment.heart;
			}
		}
		
		private PixelImage square;
		
		public PhysBox(float x, float y) {
			super(x, y, false);
			
			square = images[((int)(Math.random()*images.length))%images.length];
			
			this.getBody().applyLinearImpulse(new Vec2(MathUtils.randomFloat(-1, 1), MathUtils.randomFloat(-1, 1)).mulLocal(0.25f), this.getBody().getLocalCenter());
		}

		@Override
		public void update(float delta) {
			time += delta;
			
			if(time >= lifetime) {
				isDead = true;
			}
			
		}

//		@Override
//		public void render(Graphics2D g) {
//			// TODO Auto-generated method stub
//			
//		}

		@Override
		public void render(PixelImage canvas) {
			Point p = getRenderPoint();
			PixelImage.blit(square, canvas, p.x, p.y);
		}

		@Override
		public Rectangle2D getBounds() {
			return new Rectangle2D.Float(this.getPosition().x-SIZE/2, this.getPosition().y-SIZE/2, SIZE-1, SIZE-1);
		}

		@Override
		public boolean isDead() {
			return isDead;
		}

		@Override
		public void setDead(boolean isDead) {
			this.isDead = isDead;
		}
		
	}
	
	public static class HeartBox extends Entity implements IDeadable {
		
		public static float LIFETIME = 1.5f;
		
		private float lifetime = (float)(Math.random())*LIFETIME*1f;
		private float time = 0;
		
		public boolean isDead = false;
		
		private static PixelImage heart = R.environment.heart.getScaledInstance(0.5f);
		
		private PixelImage image = heart;
		
		public HeartBox(float x, float y) {
			super(x, y, false);
			
			this.getBody().applyLinearImpulse(new Vec2(MathUtils.randomFloat(-1, 1), MathUtils.randomFloat(-1, 1)).mulLocal(0.25f), this.getBody().getLocalCenter());
		}

		@Override
		public void update(float delta) {
			time += delta;
			
			if(time >= lifetime) {
				isDead = true;
			}
			
		}

//		@Override
//		public void render(Graphics2D g) {
//			// TODO Auto-generated method stub
//			
//		}

		@Override
		public void render(PixelImage canvas) {
			Point p = getRenderPoint();
			PixelImage.blit(image, canvas, p.x, p.y);
		}

		@Override
		public Rectangle2D getBounds() {
			return new Rectangle2D.Float(this.getPosition().x-heart.getWidth()/2, this.getPosition().y-heart.getHeight()/2, heart.getWidth()-1, heart.getHeight()-1);
		}

		@Override
		public boolean isDead() {
			return isDead;
		}

		@Override
		public void setDead(boolean isDead) {
			this.isDead = isDead;
		}
		
	}

}
