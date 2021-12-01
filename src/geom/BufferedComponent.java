package geom;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

public class BufferedComponent extends JComponent  {
	
	/**
	 * This class uses a BufferedImage as a double buffer to reduce
	 * data racing and render time within a JFrame
	 */
	private static final long serialVersionUID = -1542219742256812648L;
	private Graphics2D front;
	private BufferedImage frontImage;
	
	public BufferedComponent (int width, int height)  {
		frontImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		front = frontImage.createGraphics();
		super.setPreferredSize(new Dimension(width, height));
		
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(frontImage, 0, 0, null);
	}
	
	public Graphics2D getBufferdGraphics() {
		return front;
	}
	
	
}
