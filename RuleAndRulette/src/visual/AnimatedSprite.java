package visual;

import GUI.PixelImage;

public class AnimatedSprite {
	
	private PixelImage image;
	private int numFrames;
	private int fps;
	
	private int tileX;
	
	private float time;
	
	private int frame = 0;
	
	public AnimatedSprite(PixelImage image, int numFrames, int fps) {
		this.image = image;
		this.numFrames = numFrames;
		this.tileX = image.getWidth()/numFrames;
		this.fps = fps;
	}
	
	public void reset() {
		this.frame = 0;
		this.time = 0;
	}

	public void update(float delta) {
		this.time += delta;
		
		if(this.time >= 1/(float)fps) {
			this.time = 0;
			frame = (frame+1)%numFrames;
		}
	}
	
	public PixelImage getImage() {
		return image.getSubImage(frame*tileX, 0, tileX, image.getHeight());
	}
	
}
