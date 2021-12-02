
package geom;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import java.util.Scanner;

public class MeshFactory {
	
	public static Mesh readFile(File file) {
		try {
			// Count up the faces and such
			Scanner scan = new Scanner(file);
			scan.nextLine();
			scan.nextLine();
			scan.nextLine();
			int vecCount = 0;
			int faceCount = 0;
			while (scan.hasNext()) {
				switch (scan.next()) {
					case "v":vecCount++;break;
					case "f":faceCount++;break;
				}
				scan.nextLine();
			}
			scan.close();

			
			// Scan in the Mesh
			scan = new Scanner(file);
			Vector3[] vecs = new Vector3[vecCount];
			scan.nextLine();
			scan.nextLine();
			scan.nextLine();
			for (int i = 0; i < vecCount; i++) {
				scan.next();
				vecs[i] = new Vector3(scan.nextFloat(),scan.nextFloat(),scan.nextFloat());
				scan.nextLine();
			}
			Triangle[] faces = new Triangle[faceCount];
			scan.nextLine();
			for (int i = 0; i < faceCount; i++) {
				scan.next();
				faces[i] = new Triangle(vecs[scan.nextInt()-1],vecs[scan.nextInt()-1],vecs[scan.nextInt()-1]);
				scan.nextLine();
			}
			scan.close();
			Mesh mesh = new Mesh(faces);
			mesh.calcNormals();
			return mesh;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Mesh getCube() {
		Mesh mesh = new Mesh(new Triangle[] {
				
				// SOUTH
				new Triangle( 0f,0f,0f,   0f,1f,0f,   1f,1f,0f ),
				new Triangle( 0f,0f,0f,   1f,1f,0f,   1f,0f,0f ),
				
				// EAST
				new Triangle( 1f,0f,0f,   1f,1f,0f,   1f,1f,1f ),
				new Triangle( 1f,0f,0f,   1f,1f,1f,   1f,0f,1f ),
				
				// NORTH
				new Triangle( 1f,0f,1f,   1f,1f,1f,   0f,1f,1f ),
				new Triangle( 1f,0f,1f,   0f,1f,1f,   0f,0f,1f ),
				
				// WEST
				new Triangle( 0f,0f,1f,   0f,1f,1f,   0f,1f,0f ),
				new Triangle( 0f,0f,1f,   0f,1f,0f,   0f,0f,0f ),
				
				// UP
				new Triangle( 0f,1f,0f,   0f,1f,1f,   1f,1f,1f ),
				new Triangle( 0f,1f,0f,   1f,1f,1f,   1f,1f,0f ),
				
				// DOWN
				new Triangle( 1f,0f,1f,   0f,0f,1f,   0f,0f,0f ),
				new Triangle( 1f,0f,1f,   0f,0f,0f,   1f,0f,0f )
				
		});
		mesh.translate(-.5f,-.5f,-.5f);
		mesh.calcNormals();
		return mesh;
	}
	
	public static Mesh getTriangularPrism() {
		Mesh mesh = new Mesh(new Triangle[] {

				// SOUTH
				new Triangle( 0f,0f,0f,   .5f,1f,0f,   1f,0f,0f ),
				
				// EAST
				new Triangle( 1f,0f,0f,   .5f,1f,0f,   .5f,1f,1f ),
				new Triangle( 1f,0f,0f,   .5f,1f,1f,   1f,0f,1f ),
				
				// NORTH
				new Triangle( 0f,0f,1f,   1f,0f,1f,   .5f,1f,1f ),
				
				// WEST
				new Triangle( 0f,0f,1f,   .5f,1f,1f,   .5f,1f,0f ),
				new Triangle( 0f,0f,1f,   .5f,1f,0f,   0f,0f,0f ),
				
				// DOWN
				new Triangle( 1f,0f,1f,   0f,0f,1f,   0f,0f,0f ),
				new Triangle( 1f,0f,1f,   0f,0f,0f,   1f,0f,0f )
				
		});
		mesh.translate(-.5f,-.33f,-.5f);
		mesh.calcNormals();
		return mesh;
	}
}
