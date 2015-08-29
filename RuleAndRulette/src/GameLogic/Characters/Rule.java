package GameLogic.Characters;

import java.awt.Graphics2D;
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
	
//	private static class animations {
//		public final static AnimatedSprite WALK_RIGHT = R.animations.mami_run;
//	}

	public Rule(int i, int j) {
		super(i,j,false);
		anim = R.animations.rule.idle;
	}
	
	public Rule(float x, float y) {
		super(x, y, false);
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

	@Override
	public void render(Graphics2D g) {
		g.drawImage(R.characters.rule.asBufferedImage(), null, getX(), getY());
	}
	
	public void render(PixelImage canvas) {
		PixelImage.blit(flipped ? anim.getImage().flipLocal() : anim.getImage(), canvas, this.getX(), this.getY());
	}

	@Override
	public Rectangle2D getBounds() {
		return new Rectangle2D.Float(this.getPosition().x, this.getPosition().y, R.characters.rule.getWidth(), R.characters.rule.getHeight());
	}

	
}
