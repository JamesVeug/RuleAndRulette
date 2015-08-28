package GameLogic.Characters;

import java.awt.Graphics2D;

import Resources.R;

public class Block extends Entity{

	@Override
	public void render(Graphics2D g) {
		g.drawImage(R.logo.asBufferedImage(), null, getX(), getY());
	}
}
