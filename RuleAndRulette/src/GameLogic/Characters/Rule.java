package GameLogic.Characters;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Settings;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

import visual.AnimatedSprite;
import GUI.PixelImage;
import Resources.R;

public class Rule extends Entity{
	
	boolean flipped = false;
	
	private AnimatedSprite anim;

	
	public Rule(float x, float y) {
		super(x, y, false);
		this.anim = R.animations.rule.idle;
	}
	
	@Override
	public void update(float delta) {
		flipped = this.getVelocity().x < -Settings.EPSILON;
		
		if(getBody().getLinearVelocity().length() > 0.1f) {
			//moving
			anim = R.animations.rule.walk;
		} else {
			anim = R.animations.rule.idle;
		}
		
		anim.update(delta);
	}

//	@Override
//	public void render(Graphics2D g) {
//		g.drawImage(R.characters.rule.asBufferedImage(), null, getX(), getY());
//	}
	
	public void render(PixelImage canvas) {
		Point p = getRenderPoint();
		PixelImage.blit(flipped ? anim.getImage().flipLocal() : anim.getImage(), canvas, p.x, p.y);
	}

	@Override
	public Rectangle2D getBounds() {
		return new Rectangle2D.Float(this.getPosition().x - R.characters.rule.getWidth()/2f, this.getPosition().y - R.characters.rule.getHeight()/2f, R.characters.rule.getWidth(), R.characters.rule.getHeight());
	}

	
}
