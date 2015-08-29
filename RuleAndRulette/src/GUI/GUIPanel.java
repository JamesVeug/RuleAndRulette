package GUI;

import java.awt.Color;
import java.awt.Graphics;

public abstract class GUIPanel{
	
	private PixelImage canvas;
	private Color backgroundColor;
	
	public GUIPanel(){
		setBackgroundColor(Color.gray);
		setCanvas(new PixelImage(1024, 576));
	}
	
	public abstract void paintComponent(Graphics g, int width, int height);

	public PixelImage getCanvas() {
		return canvas;
	}

	public void setCanvas(PixelImage canvas) {
		this.canvas = canvas;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
}
