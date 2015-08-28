package GameLogic.Characters;

import java.awt.Graphics2D;

import Resources.R;

public class Block extends Entity{

	public Block(int i, int j) {
		super(i,j);
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		this.velocity.setZero();
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(R.environment.block.asBufferedImage(), null, getX(), getY());
	}
}
