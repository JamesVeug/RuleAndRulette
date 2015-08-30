package GameLogic.Characters;

import java.awt.Point;
import java.awt.geom.Rectangle2D;

import org.jbox2d.common.Settings;

import visual.AnimatedSprite;
import GUI.PixelImage;
import Resources.R;

public class Rulette extends Entity{
	
	private boolean flipped = false;
	
	private AnimatedSprite anim;
	
	private float hurt = 0f;
	
	public Rulette(float x, float y) {
		super(x, y, false);
		anim = R.animations.rulette.idle;
	}

//	@Override
//	public void render(Graphics2D g) {
//		g.drawImage(R.characters.rulette.asBufferedImage(), null, getX(), getY());
//	}
	
	public void render(PixelImage canvas) {
		Point p = getRenderPoint();
		PixelImage.blit(flipped ? anim.getImage().flipLocal() : anim.getImage(), canvas, p.x, p.y);
	}

	@Override
	public Rectangle2D getBounds() {
		return new Rectangle2D.Float(this.getPosition().x - R.characters.rulette.getWidth()/2f, this.getPosition().y - R.characters.rulette.getHeight()/2f, R.characters.rulette.getWidth(), R.characters.rulette.getHeight());
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
		
		if(hurt > 0) {
			anim = R.animations.rulette.hurt;
			hurt -= delta;
		}
		
		anim.update(delta);
	}
	
	public void hurt() {
		this.hurt = 0.2f;
	}
}
