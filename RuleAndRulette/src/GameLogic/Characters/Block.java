package GameLogic.Characters;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

import GUI.PixelImage;
import Resources.R;

public class Block extends Entity{

	public Block(float x, float y, boolean isStatic) {
		super(x, y, isStatic);
	}

	@Override
	public void render(PixelImage canvas) {
		Point p = getRenderPoint();
		PixelImage.blit(R.environment.block, canvas, p.x, p.y);
	}

	@Override
	public Rectangle2D getBounds() {
		return new Rectangle2D.Float(this.getPosition().x - R.environment.block.getWidth()/2f, this.getPosition().y - R.environment.block.getHeight()/2f, R.environment.block.getWidth(), R.environment.block.getHeight());
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}
}
