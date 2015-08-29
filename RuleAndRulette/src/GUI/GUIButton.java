package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import Resources.R;

public class GUIButton {

	private String words;
	private int x;
	private int y;
	private int width;
	private int height;

	public GUIButton(String words, int x, int y, int width, int height){
		this.words = words;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void drawButton(Graphics2D g){
		g.setColor(Color.white);
		g.fillRect(x-width/2, y-height/2, width, height);
		
		g.setColor(Color.black);
		g.drawRect(x-width/2, y-height/2, width, height);
		
		g.setColor(Color.black);
		g.setFont(new Font(R.fonts.kenpixel_mini_square.getName(), Font.PLAIN, 30));
		
		Rectangle2D bounds = g.getFontMetrics().getStringBounds(words,g);
		int textX = ((int)(x-bounds.getWidth()/2));
		int textY = ((int)(y-bounds.getHeight()/2));
		g.drawString(words, textX, textY);		
	}
}
