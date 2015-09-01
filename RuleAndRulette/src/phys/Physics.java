package phys;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;

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

import GUI.GUIGame;
import GUI.PixelImage;
import GameLogic.Score;
import GameLogic.Characters.Block;
import GameLogic.Characters.Entity;
import GameLogic.Characters.Rule;
import GameLogic.Characters.Rulette;
import GameLogic.Characters.Spike;
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
	
	public static boolean isRunningSlow = false;
	
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
	
	public static class BrokenHeartPool {
		private static Queue<BrokenHeartBox> items = new LinkedList<BrokenHeartBox>();
		
		public static int size() { return items.size(); }
		
		public static BrokenHeartBox get(float x, float y) {
			if(!items.isEmpty()) {
				BrokenHeartBox out = items.poll();
				out.getBody().setActive(true);
				out.setPosition(x, y);
				out.time = 0f;
				out.isDead = false;
				return out;
			} else {
				return new BrokenHeartBox(x, y);
			}
		}
		
		public static void free(BrokenHeartBox item) {
			Physics.spawned.remove(item);
			item.setPosition(-1000, -1000);
			item.getBody().setActive(false);
			items.offer(item);
		}
	}
	
	public static class HeartPool {
		private static Queue<HeartBox> items = new LinkedList<HeartBox>();
		
		public static int size() { return items.size(); }
		
		public static HeartBox get(float x, float y) {
			if(!items.isEmpty()) {
				HeartBox out = items.poll();
				out.getBody().setActive(true);
				out.setPosition(x, y);
				out.time = 0f;
				out.isDead = false;
				return out;
			} else {
				return new HeartBox(x, y);
			}
		}
		
		public static void free(HeartBox item) {
			Physics.spawned.remove(item);
			item.setPosition(-1000, -1000);
			item.getBody().setActive(false);
			items.offer(item);
		}
	}
	
	public static class PhysPool {
		private static Queue<PhysBox> items = new LinkedList<PhysBox>();
		
		public static int size() { return items.size(); } 
		
		public static PhysBox get(float x, float y) {
			if(!items.isEmpty()) {
				PhysBox out = items.poll();
				out.setPosition(x, y);	
				out.getBody().setActive(true);
				out.time = 0f;
				out.isDead = false;
				return out;
			} else {
				return new PhysBox(x, y);
			}
		}
		
		public static void free(PhysBox item) {
			Physics.spawned.remove(item);
			item.setPosition(-1000, -1000);
			item.getBody().setActive(false);
			items.offer(item);
		}
	}
	
//	public static class HeartSpawner {
//		
//		private static LinkedList<HeartBox> hearts = new LinkedList<HeartBox>();
//		private static LinkedList<HeartBox> live = new LinkedList<HeartBox>();
//		
//		private HeartSpawner instance = new HeartSpawner(200);
//		private HeartSpawner(int n) {
//			for(int i = 0; i < n; i++) {
//				HeartBox box = new HeartBox(-1, -1);
//				box.getBody().setActive(false);
//				hearts.addLast(box);
//			}
//		}
//
//		public static void spawn(float x, float y, int num) {
//			if(hearts.size() < num) {
//				int end = num - hearts.size();
//				for(int i = 0; i < end; i++) {
//					HeartBox box = new HeartBox(-1, -1);
//					box.getBody().setActive(false);
//					hearts.addLast(box);
//				}
//			}
//			
//			for(int i = 0; i < num && !hearts.isEmpty(); i++) {
//				HeartBox box = hearts.getFirst();
//				box.setPosition(x, y);	
//				box.getBody().setActive(true);
//				box.getBody().applyLinearImpulse(new Vec2(MathUtils.randomFloat(-1, 1), MathUtils.randomFloat(-1, 1)).mulLocal(0.2f), box.getBody().getLocalCenter());
//			}
//		}
//		
//		private static void free(HeartBox box) {
//			box.setPosition(-1, -1);
//			box.getBody().setActive(false);
//			hearts.addLast(box);
//		}
//		
//		public static void tick(float delta) {
//			for(Iterator<HeartBox> iter = live.iterator(); iter.hasNext();) {
//				HeartBox box = iter.next();
//				box.update(delta);
//				if(box.isDead()) {
//					iter.remove();
//					free(box);
//				}
//			}
//		}
//		
//		public static void render(PixelImage canvas) {
//			for(HeartBox box : live) {
//				box.render(canvas);
//			}
//		}
//		
//	}
	
	public static HashSet<Entity> spawned = new LinkedHashSet<Entity>();
	
	public static void spawn(final float x, final float y, int numBoxes) {
		
		if(Physics.isRunningSlow) {
			numBoxes *= 0.1f;
		}
		
		Sound.playSound(R.sound.effects.explosion);
		
		for(int i = 0; i < numBoxes; i++) {
//			Entity entity = new PhysBox(x, y);
			Entity entity = PhysPool.get(x+MathUtils.randomFloat(0, 1), y+MathUtils.randomFloat(0, 1));
			entity.getBody().applyLinearImpulse(new Vec2(MathUtils.randomFloat(-1, 1), MathUtils.randomFloat(-1, 1)).mulLocal(0.2f), entity.getBody().getLocalCenter());
			spawned.add(entity);
			Score.addScore(numBoxes);
		}
	}
	
	public static void spawnHeart(final float x, final float y, int numBoxes) {
		
		if(Physics.isRunningSlow) {
			numBoxes *= 0.1f;
		}
		
		Sound.playSound(R.sound.effects.explosion);
		
		for(int i = 0; i < numBoxes; i++) {
//			Entity entity = new HeartBox(x, y);
			Entity entity = HeartPool.get(x+MathUtils.randomFloat(0, 1), y+MathUtils.randomFloat(0, 1));
			entity.getBody().applyLinearImpulse(new Vec2(MathUtils.randomFloat(-1, 1), MathUtils.randomFloat(-1, 1)).mulLocal(0.2f), entity.getBody().getLocalCenter());
			spawned.add(entity);
			Score.addScore(numBoxes);
		}
	}

	public static void spawnBrokenHeart(final float x, final float y, int numBoxes) {
		
		if(Physics.isRunningSlow) {
			numBoxes *= 0.1f;
		}
		
		Sound.playSound(R.sound.effects.explosion);
		
		for(int i = 0; i < numBoxes; i++) {
//			Entity entity = new HeartBox(x, y);
			Entity entity = BrokenHeartPool.get(x+MathUtils.randomFloat(0, 1), y+MathUtils.randomFloat(0, 1));
			entity.getBody().applyLinearImpulse(new Vec2(MathUtils.randomFloat(-1, 1), MathUtils.randomFloat(-1, 1)).mulLocal(0.2f), entity.getBody().getLocalCenter());
			spawned.add(entity);
			Score.addScore(numBoxes);
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
				GUIGame.shake(50f);
				Physics.a = a;
				if(Math.random() < 0.02f) { ((Rule)a).hurt(); }
//				spawn(a.getPosition().x, a.getPosition().y, 20);
			}
			
			if(a.getClass() == Rulette.class && a.touching == 0) {
				if(Math.random() < 0.02f) { ((Rulette)a).hurt(); }
			}
			
			if( a.getClass() == Rulette.class && b != null && b.getClass() == Spike.class ){
				Rulette r = (Rulette)a;
				r.setDead(true);
			}
			else if( a.getClass() == Rule.class && b != null && b.getClass() == Spike.class ){
				Rule r = (Rule)a;
				r.setDead(true);
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
			
			if(b.getClass() == Rule.class && b.touching == 0) {
				if(Math.random() < 0.02f) { ((Rule)b).hurt(); }
			}
			
			if(b.getClass() == Rulette.class && b.touching == 0) {
				GUIGame.shake(5f);
				Physics.b = b;
				if(Math.random() < 0.02f) { ((Rulette)b).hurt(); }
	//			spawn(a.getPosition().x, a.getPosition().y, 20);
			}
			
			if( b.getClass() == Rulette.class && a != null && a.getClass() == Spike.class ){
				Rulette r = (Rulette)b;
				r.setDead(true);
			}
			else if( b.getClass() == Rule.class && a != null && a.getClass() == Spike.class ){
				Rule r = (Rule)b;
				r.setDead(true);
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
		private float time;
		
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
			time = 0f;
			
			square = images[((int)(Math.random()*images.length))%images.length];
			
//			this.getBody().applyLinearImpulse(new Vec2(MathUtils.randomFloat(-1, 1), MathUtils.randomFloat(-1, 1)).mulLocal(0.25f), this.getBody().getLocalCenter());
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
		private float time;
		
		public boolean isDead = false;
		
		private static PixelImage heart = R.environment.heart.getScaledInstance(0.5f);
		
		private PixelImage image = heart;
		
		public HeartBox(float x, float y) {
			super(x, y, false);
			time = 0f;
			
//			this.getBody().applyLinearImpulse(new Vec2(MathUtils.randomFloat(-1, 1), MathUtils.randomFloat(-1, 1)).mulLocal(0.25f), this.getBody().getLocalCenter());
		}
		
		public void reset() {
			this.isDead = false;
			this.time = 0;
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
	
	public static class BrokenHeartBox extends Entity implements IDeadable {
		
		public static float LIFETIME = 1.5f;
		
		private float lifetime = (float)(Math.random())*LIFETIME*1f;
		private float time;
		
		public boolean isDead = false;
		
		private static PixelImage heart = R.environment.broken_heart.getScaledInstance(0.5f);
		
		private PixelImage image = heart;
		
		public BrokenHeartBox(float x, float y) {
			super(x, y, false);
			time = 0f;
			
//			this.getBody().applyLinearImpulse(new Vec2(MathUtils.randomFloat(-1, 1), MathUtils.randomFloat(-1, 1)).mulLocal(0.25f), this.getBody().getLocalCenter());
		}
		
		public void reset() {
			this.isDead = false;
			this.time = 0;
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
