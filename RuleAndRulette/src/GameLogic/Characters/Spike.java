package GameLogic.Characters;

import java.awt.Graphics2D;

import Resources.R;

public class Spike extends Entity{

	public Spike(int i, int j) {
		super(i,j);
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(R.environment.spikes.asBufferedImage(), null, getX(), getY());
	}
}
