package tmholt.dimthree;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import tmholt.dimthree.geom.Matrix4x4;
import tmholt.dimthree.geom.Triangle;
import tmholt.dimthree.geom.Vector3;
import tmholt.dimthree.modelmesh.Model;
import tmholt.dimthree.modelmesh.Transform;

public class Projector {
	
	private static Vector3 forward = new Vector3(0, 0, 1);
	private Vector3 clipPos;
	
	private int height, width;
	private float near, far, fovRad, aspectRatio;
	private float[][] matrix;
	private List<Model> list;
	private Graphics2D outG;
	private Transform camera;
	private Vector3 lightDirection;
	
	private boolean debug;
	
	
	public Projector (float near, float far, float fovDegrees, int height, int width, Vector3 lightDirection, Graphics2D outG) {
		
		this.near = near;
		this.far = far;
		this.fovRad = 1 / (float) Math.toRadians(fovDegrees);
		this.aspectRatio = (float)height / (float)width;
		this.height = height;
		this.width = width;
		
		matrix = Matrix4x4.getEmptyMatrix();
		buildMatrix();
		
		list = new LinkedList<>();
		
		this.outG = outG;
		
		camera = new Transform();
		
		this.lightDirection = lightDirection;
		lightDirection.normalize();
		
		clipPos = new Vector3(0f, 0f, near);
		
		debug = false;
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
	
	
	
	public void render() {
		
		List<Triangle> trisToRender = new ArrayList<>();
		
		for (Model model : list) {
			for (Triangle in : model.toWorldspace().getTris()) {
				
				// The line drawn towards the camera
				Vector3 p = camera.pos().getNegative();
				p.add(in.p1);
				// If it can be seen, draw
				if (Vector3.dotProduct(in.normal, p) < 0f) {
					in.applyInverseTransform(camera); // Reciprocate scale?
					for (Triangle clipped: in.getClippedAgainstPlane(clipPos, forward)) {
						Matrix4x4.transformTriangle(clipped, clipped, matrix);
						clipped.translate(1f, 1f, 0f);
						clipped.scale(width/2, height/2, 1);
						clipped.normal = in.normal;
						trisToRender.add(clipped);
					}
				}
			}
		}
		
		outG.clearRect(0, 0, width, height);
		
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
			
			outG.setColor(new Color((int)(dp*perRed), (int)(dp*perGrn), (int)(dp*perBlu)));
			outG.fillPolygon(tri.toPolygon());
			if (debug) {
				outG.setColor(Color.ORANGE);
				outG.draw(tri.toPolygon());
			}
		}
		
	}
	
	
	public Transform getCamera() {
		return camera;
	}
	
	public void setDebug (boolean debug) {
		this.debug = debug;
	}
	
	public boolean getDebug() {
		return debug;
	}
}
