package GameLogic.Characters;

import java.awt.Graphics2D;

import Resources.R;

public class Rulette extends Entity{

	public Rulette(int i, int j) {
		super(i,j);
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(R.logo.asBufferedImage(), null, getX(), getY());
	}
}
