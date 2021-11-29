import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import geom.Matrix4x4;
import geom.Mesh;
import geom.Transform;
import geom.Vector3;
import geom.Triangle;

public class Projector {
	
	private int height, width;
	private float near, far, fovRad, aspectRatio;
	private float[][] matrix;
	private List<Model> list;
	private MeshFrame frame;
	private BufferedImage image;
	private Transform camera;
	private Vector3 lightDirection;
	
	
	public Projector (String name, float near, float far, float fovDegrees, int height, int width) {
		
		this.near = near;
		this.far = far;
		this.fovRad = 1 / (float) Math.toRadians(fovDegrees);
		this.aspectRatio = (float)height / (float)width;
		this.height = height;
		this.width = width;
		
		matrix = Matrix4x4.getEmptyMatrix();
		buildMatrix();
		
		list = new LinkedList<>();
		
		frame = new MeshFrame(name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.setVisible(true);
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		
		camera = new Transform();
		lightDirection = new Vector3(8f, -7f,-10f);
		lightDirection.normalize();
	}
	
	
	private void buildMatrix() {
		matrix[0][0] = aspectRatio * fovRad;
		matrix[1][1] = fovRad;
		matrix[2][2] = far / (far - near);
		matrix[3][2] = (-far * near) / (far - near);
		matrix[2][3] = 1f;
	}
	
	
	public void register (Model mesh) {
		list.add(mesh);
	}
	
	public static Vector3 temp = new Vector3(0,0,1);
	public void render() {
		temp.normalize();
		Graphics2D g2D = (Graphics2D)image.createGraphics();
		g2D.clearRect(0, 0, width, height);
		
		List<Triangle> trisToRender = new ArrayList<>();
		
		for (Model model : list) {
			for (Triangle in : model.toWorldspace().getTris()) {
				
				// The line drawn towards the camera
				Vector3 p = camera.pos().getNegative();
				p.add(in.p1);
				// If it can be seen, draw
				if (Vector3.dotProduct(in.normal, p) < 0f) {
					in.applyInverseTransform(camera); // Reciprocate scale?
					for (Triangle clipped: in.getClippedAgainstPlane(new Vector3(0f, 0f, .001f), temp)) {
						Matrix4x4.transformTriangle(clipped, clipped, matrix);
						clipped.translate(1f, 1f, 0f);
						clipped.scale(width/2, height/2, 1);
						trisToRender.add(clipped);
					}
				}
			}
		}
		
		
		Collections.sort(trisToRender);
		for (int i = 0; i < trisToRender.size(); i++) {
			Triangle tri = trisToRender.get(i);
			Vector3 normal = tri.normal;
			
			float dp = Vector3.dotProduct(normal, lightDirection);
			if (dp < 0) dp = 0f;
			dp*=245;
			dp+=10;
			
			float perRed = 1f;
			float perGrn = 1f;
			float perBlu = 1f;
			
			if (tri.color != null) {
				perRed = tri.color.getRed()   /255f;
				perGrn = tri.color.getGreen() /255f;
				perBlu = tri.color.getBlue()  /255f;
			}
			
			g2D.setColor(new Color((int)(dp*perRed),(int)(dp*perGrn),(int)(dp*perBlu)));
			g2D.fillPolygon(tri.toPolygon());
			g2D.setColor(Color.ORANGE);
			g2D.draw(tri.toPolygon());
		}
		
		frame.repaint();
		
	}
	
	
	public Transform getCamera() {
		return camera;
	}
	
	
	private class MeshFrame extends JFrame {
		private static final long serialVersionUID = 1L;
		public MeshFrame (String name) { super(name); }
		@Override
		public void paint(Graphics g) {
			g.drawImage(image, 0, 0, width, height, Color.GRAY, null);
		}
	}
}
