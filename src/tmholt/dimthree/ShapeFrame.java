import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

public class ShapeFrame extends JFrame {
	private static final long serialVersionUID = 2882035967430896080L;
	
	private List<Shape> list;
	
	@SuppressWarnings("unused")
	private Color backColor;
	private Color strokeColor;
	
	public ShapeFrame(Color strokeColor, Color backColor) {
		super("ShapeFrame");
		
		this.strokeColor = strokeColor;
		this.backColor = backColor;
		setBackground(backColor);
		
		list = new LinkedList<>();
	}
	
	public void addShape (Shape shape) {
		list.add(shape);
	}
	
	public void clearShapes() {
		list.clear();
	}
	
	@Override
	public void paint (Graphics g) {
		
		Graphics2D g2D = (Graphics2D)g;
		g2D.clearRect(0, 0, getWidth(), getHeight());
		g2D.setColor(strokeColor);
		for (Shape shape : list) {
			g2D.draw(shape);
		}
		
	}
	
	

}
