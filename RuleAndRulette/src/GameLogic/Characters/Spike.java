package GameLogic.Characters;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import GUI.PixelImage;
import Resources.R;

public class Spike extends Entity{

	public Spike(int i, int j) {
		super(i,j,true);
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(R.environment.spikes.asBufferedImage(), null, getX(), getY());
	}

	@Override
	public void render(PixelImage canvas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle2D getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}
}
