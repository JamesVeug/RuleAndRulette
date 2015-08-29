package GameLogic.Characters;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import GUI.PixelImage;
import Resources.R;

public class Block extends Entity{

	public Block(float x, float y, boolean isStatic) {
		super(x, y, isStatic);
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(R.environment.block.asBufferedImage(), null, getX(), getY());
	}

	@Override
	public void render(PixelImage canvas) {
		PixelImage.blit(R.environment.block, canvas, this.getX(), this.getY());
	}

	@Override
	public Rectangle2D getBounds() {
		return new Rectangle2D.Float(this.getPosition().x, this.getPosition().y, R.environment.block.getWidth(), R.environment.block.getHeight());
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}
}
