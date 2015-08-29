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

public class Rulette extends Entity{
	
	private boolean flipped = false;
	
	private AnimatedSprite anim;
	
	public Rulette(int i, int j) {
		super(i,j,false);
		anim = R.animations.rulette.idle;
	}
	
	public Rulette(float x, float y) {
		super(x, y, false);
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(R.characters.rulette.asBufferedImage(), null, getX(), getY());
	}
	
	public void render(PixelImage canvas) {
		PixelImage.blit(flipped ? anim.getImage().flipLocal() : anim.getImage(), canvas, this.getX(), this.getY());
	}

	@Override
	public Rectangle2D getBounds() {
		return new Rectangle2D.Float(this.getPosition().x, this.getPosition().y, R.characters.rulette.getWidth(), R.characters.rulette.getHeight());
	}

	@Override
	public void update(float delta) {
		flipped = this.getVelocity().x < -Settings.EPSILON;
		
		if(getBody().getLinearVelocity().length() > 0.1f) {
			//moving
			anim = R.animations.rulette.walk;
		} else {
			anim = R.animations.rulette.idle;
		}
		
		anim.update(delta);
	}
}
