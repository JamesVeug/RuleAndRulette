package GameLogic.Characters;

import java.awt.Point;
import java.awt.geom.Rectangle2D;

import GUI.PixelImage;
import Resources.R;

public class Spike extends Entity{

//	@Override
//	public void render(Graphics2D g) {
//		g.drawImage(R.environment.spikes.asBufferedImage(), null, getX(), getY());
//	}

	public Spike(float x, float y) {
		 super(x, y, true);
	}

	@Override
	public void render(PixelImage canvas) {
		Point p = getRenderPoint();
		PixelImage.blit(R.environment.spikes, canvas, p.x, p.y);
	}

	@Override
	public Rectangle2D getBounds() {
		return new Rectangle2D.Float(this.getPosition().x - R.environment.spikes.getWidth()/2f, this.getPosition().y - R.environment.spikes.getHeight()/2f, R.environment.spikes.getWidth(), R.environment.spikes.getHeight());
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}
}
