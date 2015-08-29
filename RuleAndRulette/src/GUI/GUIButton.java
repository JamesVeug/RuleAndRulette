package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import Resources.R;

public class GUIButton {
	private static final Font font = new Font(R.fonts.kenpixel_mini_square.getName(), Font.PLAIN, 30);

	private String words;
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean isHovering = false;

	public GUIButton(String words, int x, int y, int width, int height){
		this.words = words;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void drawButton(Graphics2D g){
		g.setColor(Color.white);
		
		if( isHovering ){
			g.setStroke(new BasicStroke(10));
		}else{
			g.setStroke(new BasicStroke(2));
		}
		g.fillRect(x-width/2, y-height/2, width, height);
		
		g.setColor(Color.black);
		g.drawRect(x-width/2, y-height/2, width, height);
		
		g.setColor(Color.black);
		g.setFont(font);
		
		Rectangle2D bounds = g.getFontMetrics().getStringBounds(words,g);
		int textX = ((int)(x-bounds.getWidth()/2));
		int textY = ((int)(y+bounds.getHeight()/4));
		g.drawString(words, textX, textY);		
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public boolean onButton(int x, int y){
		return x+width/2 >= this.x && x+width/2 <= this.x+width && y+height/2 >= this.y && y+height/2 <= this.y+height;
	}

	public boolean isHovering() {
		return isHovering;
	}

	public boolean setHovering(boolean isHovering) {
		boolean changed = isHovering != this.isHovering;
		this.isHovering = isHovering;
		return changed;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}

	public void setText(String string) {
		words = string;
	}
}
