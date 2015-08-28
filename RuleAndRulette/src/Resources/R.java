import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;


public class R {

	public static PixelImage logo = loadPixelImage("assets/logo.png");
	
	public static PixelImage loadPixelImage(String fname) {
		try {
			return new PixelImage(ImageIO.read(new File(fname)));
		} catch (IOException e) {
			System.err.println("Error loading \"" + fname + "\": " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		frame.add(new JComponent() {
			{
				this.setPreferredSize(new Dimension(300, 300));
			}
			protected void paintComponent(Graphics g) {
				g.drawImage(R.logo.getSubImage(20, 20, 30, 30).asBufferedImage(), 0, 0, null);
			}
		});
		
		frame.pack();
		frame.setVisible(true);
		
	}
	
}
