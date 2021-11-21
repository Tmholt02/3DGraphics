import java.awt.Color;
import java.io.File;

import geom.MeshFactory;
import geom.Transform;

public class Main {

	public static void main(String[] args) {
		
		// Start our system!
		int width  = 1800;
		int height = 1000;
		float fovDeg = 50f;
		float near = .1f;
		float far = 10f;
		Projector projector = new Projector("Projector Frame", near, far, fovDeg, height, width);
		
		// Define a prism and all its triangles
		Model prism = new Model(MeshFactory.readFile(new File("Icosphere.obj")));
		prism.setColor(new Color(255, 55, 2));
		Transform t = prism.getTransform();
		
		// Move the prism to a more interesting place
		t.pos().set(.5f, 1f, 3f);
		
		// Register the prism with the projector
		projector.register(prism);
		
		// Make Some Walls!
		Model rec = new Model(MeshFactory.getCube());
		Transform recT = rec.getTransform();
		recT.scl().set(.3f, 6f, 4f);
		recT.pos().add(-6f, 0f, 3f);
		for (int i = 0; i < 30; i++) {
			Model cln = rec.clone();
			projector.register(cln);
			recT.pos().add(0f, 0f, 5f);
			recT.rot().add(.10f, 0f, .01f);   
		}
		
		// Paint it once
		projector.render();
		
		// Try to rotate the prism
		new Thread(()->{
			int r = 255;
			int g = 0;
			int b = 0;
			while (true) {
				prism.setColor(new Color(r, g, b));
				if (b == 0 && r != 0) {
					r--;
					g+=1;    
				}
				else if (r == 0 && g != 0) {
					g--;
					b++;
				}
				else {
					b--;
					r++;
				}
				t.rot().add(.043f,.0f,.053f);
				projector.render();
				try {Thread.sleep(60);} catch (Exception e) {}
			}
		}).start();
		
		// Input Thread
		new Thread(()->{
			while (true) {
				try {Thread.sleep(10);} catch (Exception e) {}
				
				if (Input.W.isPressed()) {
					projector.getCamera().move(.1f, 0, 0);
				}
				if (Input.S.isPressed()) {
					projector.getCamera().move(-.1f, 0, 0);
				}
				if (Input.Q.isPressed()) {
					projector.getCamera().move(0, 0, .1f);
				}
				if (Input.E.isPressed()) {
					projector.getCamera().move(0, 0, -.1f);
				}
				if (Input.A.isPressed()) {
					projector.getCamera().move(0, -.1f, 0);
				}
				if (Input.D.isPressed()) {
					projector.getCamera().move(0, .1f, 0);
				}
				
				if (Input.J.isPressed()) {
					projector.getCamera().rot().add(0, .1f, 0);
				}
				if (Input.L.isPressed()) {
					projector.getCamera().rot().add(0, -.1f, 0);
				}
				if (Input.I.isPressed()) {
					projector.getCamera().rot().add(.1f, 0, 0);
				}
				if (Input.K.isPressed()) {
					projector.getCamera().rot().add(-.1f, 0, 0);
				}
				if (Input.U.isPressed()) {
					projector.getCamera().rot().add(0, 0, .1f);
				}
				if (Input.O.isPressed()) {
					projector.getCamera().rot().add(0, 0, -.1f);
				}
				
			}
		}).start();
		
	}
	
	
//	public static double count = 0;
//	public static void main(String[] args) {
//		
//		int width  = 1800;
//		int height = 1000;
//		float fovDeg = 50f;
//		float near = .1f;
//		float far = 10f;
//		Projector projector = new Projector("Projector Frame", near, far, fovDeg, height, width);
//		
//		
//		Model cube = new Model(MeshFactory.getCube());
//		Transform cubeTransform = cube.getTransform();
//
//		cubeTransform.pos().set(.5f, 1f, 3f);
//		
//		projector.register(cube);
//		projector.render();
//		
//		new Thread(()->{
//			while (true) {
//				count+=0.2;
//				cubeTransform.scl().set((float)Math.sin(count)/2 + 1f,(float)Math.cos(count)/2 + 1f, 1f);
//				projector.render();
//				try {Thread.sleep(10);} catch (Exception e) {}
//			}
//		}).start();
//		
//	}

}
