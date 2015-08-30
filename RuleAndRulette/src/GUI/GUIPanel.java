package GUI;

import java.awt.Color;
import java.awt.Graphics;

public abstract class GUIPanel{
	
	private PixelImage canvas;
	
	private PixelImage canvasA;
	private PixelImage canvasB;

	private Color backgroundColor;
	
	public GUIPanel(){
		setBackgroundColor(Color.gray);
		canvasA = new PixelImage(1024, 576);
		canvasB = new PixelImage(1024, 576);
		setCanvas(canvasA);
	}
	
	public abstract void paintComponent(Graphics g, int width, int height);

	public PixelImage getCanvas() {
		return canvas;
	}
	
	public void toggleCanvas() {
		canvas = canvas == canvasA ? canvasB : canvasA;
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
