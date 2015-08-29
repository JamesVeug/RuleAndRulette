package GUI;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;


public class PixelImage implements Cloneable {
	
	public static final int DEFAULT_CLEAR_COLOR = -1;
	
	private int width;
	private int height;

	private int[] data;
	
	private BufferedImage image;
	
	public PixelImage(int width, int height) {
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.width = width;
		this.height = height;
		
		this.data = ((DataBufferInt)(image.getRaster().getDataBuffer())).getData();
	}
	
	public PixelImage(BufferedImage src) {
		this(src.getWidth(), src.getHeight());
		
		//draw in the src image
		this.image.createGraphics().drawImage(src, null, 0, 0);
	}
	
	public void clear() {
		fill(DEFAULT_CLEAR_COLOR);
	}
	
	public void fill(int rgb) {
		Arrays.fill(data, rgb);
	}
	
	public int getRGB(int x, int y) {
		return data[y*width + x];
	}
	
	public void setRGB(int x, int y, int rgb) {
		data[y*width + x] = rgb;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public BufferedImage asBufferedImage() {
		return image;
	}
	
	public PixelImage getScaledInstance(float factor) {
		PixelImage out = new PixelImage((int)(this.width * factor), (int)(this.height * factor));
		
		for(int y = 0; y < out.height; y++) {
			for(int x = 0; x < out.width; x++) {
				out.setRGB(x, y, this.getRGB((int)(x/factor), (int)(y/factor)));
			}
		}
		
		return out;
	}
	
	public PixelImage getSubImage(int offsetX, int offsetY, int width, int height) {
		PixelImage out = new PixelImage(width, height);
		
		for(int y = 0; y < out.height; y++) {
			for(int x = 0; x < out.width; x++) {
				int srcX = offsetX + x;
				int srcY = offsetY + y;
				if(srcX >= 0 && srcX < this.getWidth() && srcY >= 0 && srcY < this.getHeight()) {
					out.setRGB(x, y, this.getRGB(srcX, srcY));
				}
			}
		}
		
		return out;
	}
	
	public PixelImage flipLocal() {
		for(int x = 0; x < this.getWidth()/2; x++) {
			for(int y = 0; y < this.getHeight(); y++) {
				int tmp = this.getRGB(x, y);
				this.setRGB(x, y, this.getRGB(this.getWidth()-1-x, y));
				this.setRGB(this.getWidth()-1-x, y, tmp);
			}
		}
		return this;
	}
	
	public PixelImage clone() {
		PixelImage out = new PixelImage(width, height);
		
		for(int i = 0; i < this.data.length; i++) {
			out.data[i] = this.data[i];
		}
		
		return out;
	}
	
	public static void blit(PixelImage src, PixelImage dst, int x, int y) {
		for(int srcY = 0; srcY < src.getHeight(); srcY++) {
			for(int srcX = 0; srcX < src.getWidth(); srcX++) {
				int dstX = x + srcX;
				int dstY = y + srcY;
				if(dstX >= 0 && dstX < dst.getWidth() && dstY >= 0 && dstY < dst.getHeight()) {
					int alpha = src.getRGB(srcX, srcY) >> 24 & 0xFF;
					if(alpha != 0) {
						dst.setRGB(dstX, dstY, src.getRGB(srcX, srcY));
					}
				}
			}
		}
	}

}
