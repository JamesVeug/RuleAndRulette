package GameLogic.Characters;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.List;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

import phys.Physics;
import GUI.PixelImage;
import GameLogic.Game;
import Resources.R;

public abstract class Entity {
	
	public static final float SCALE = 100f;
	public static final float INVSCALE = 1/SCALE;
	
	public static final Vec2 GRAVITY = new Vec2(0, 9.8f);
	public static final Vec2 TERMINAL_VELOCITY = new Vec2(20, 20);
	
	public final int ID = IDGenerator.next();
	
	public int touching = 0;
	
	private Body body;

	
	public Entity(boolean isStatic) {
		this(0, 0, isStatic);
	}
	
	public Entity(float x, float y, boolean isStatic) {		
		BodyDef bdef = new BodyDef();
		bdef.userData = this;
		bdef.type = isStatic ? BodyType.STATIC : BodyType.DYNAMIC;
		bdef.position.set(x*INVSCALE, y*INVSCALE);
		bdef.fixedRotation = true;
		body = Physics.world.createBody(bdef);
		
		Rectangle2D bounds = this.getBounds();
		
		PolygonShape pshape = new PolygonShape();
		pshape.setAsBox((float)((bounds.getWidth()-1f)/2f)*INVSCALE, (float)((bounds.getHeight()-1f)/2f)*INVSCALE);
		
		CircleShape cshape = new CircleShape();
		cshape.m_radius = (float) ((bounds.getWidth()-1)/2f)*INVSCALE;
		
		
		FixtureDef fdef = new FixtureDef();
		fdef.density = isStatic ? 0 : 1f;
		fdef.density = !isStatic && this.getClass() == Block.class ? 0.1f : fdef.density;
		fdef.restitution = 0.1f;
		fdef.shape = this.getClass() == Rule.class || this.getClass() == Rulette.class ? cshape : pshape;
		Fixture f = body.createFixture(fdef);
	}
	
	public Body getBody() {
		return body;
	}
	
//	public int getX() {
//		return (int) (body.getPosition().x*SCALE);
//	}
//	
//	public int getY() {
//		return (int) (body.getPosition().y*SCALE);
//	}
	
	public Point getRenderPoint() {
		Rectangle2D bounds = getBounds();
		return new Point((int)(body.getPosition().x*SCALE - bounds.getWidth()/2f), (int)(body.getPosition().y*SCALE - bounds.getHeight()/2f));
	}
	
//	/** immutable */
	public Vec2 getVelocity() {
		return new Vec2(body.getLinearVelocity());
	}
	
	/** immutable */
	public Vec2 getPosition() {
		return new Vec2(body.getPosition().mul(SCALE)); //scale from physics world to pixels
	}
	
	public void setPosition(float x, float y) {
//		body.setTransform(new Vec2(x*INVSCALE, y*INVSCALE), body.getAngle());
		body.m_xf.p.set(x*INVSCALE, y*INVSCALE);
	}
	
	public void setPosition(Vec2 position) {
		body.setTransform(new Vec2(position.x*INVSCALE, position.y*INVSCALE), body.getAngle());
//		body.m_xf.p.set(position.mul(INVSCALE));
	}
//	
//	public void setPosition(float x, float y) {
//		body.m_xf.p.set(x, y);
//	}
//	
//	public void setVelocity(float x, float y) {
//		body.m_linearVelocity.set(x, y);
//	}
//	
	public void setVelocity(Vec2 velocity) {
		body.m_linearVelocity.set(velocity);
		body.setAwake(true);
	}
	
	
	public void setVelocityX(float x) {
		body.m_linearVelocity.x = x;
		body.setAwake(true);
	}
	
	public void setVelocityY(float y) {
		body.m_linearVelocity.y = y;
		body.setAwake(true);
	}
	
	public abstract void update(float delta);
	
//	public abstract void render(Graphics2D g);
	
	public abstract void render(PixelImage canvas);
	
	public abstract Rectangle2D getBounds();
	
	private static class IDGenerator {
		private static int i = 0;
		public static int next() {
			return i++;
		}
	}

}
