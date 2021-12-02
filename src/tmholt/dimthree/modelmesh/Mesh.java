package tmholt.dimthree.modelmesh;

import java.awt.Color;

import tmholt.dimthree.geom.Matrix4x4;
import tmholt.dimthree.geom.Triangle;
import tmholt.dimthree.geom.Vector3;

public class Mesh implements Cloneable {
	
	private Triangle[] tris;
	
	
	public Mesh (Triangle[] tris) {
		this.tris = tris;
	}
	
	
	public Mesh (Float[] pts) {
		tris = new Triangle[pts.length / 9];
		for (int i = 0; i < pts.length; i+=9) {
			tris[i/9] = new Triangle(pts[i + 0], pts[i + 1], pts[i + 2],   pts[i + 3], pts[i + 4], pts[i + 5],   pts[i + 6], pts[i + 7], pts[i + 8]);
		}
	}
	
	
	
	public void calcNormals() {
		for (Triangle triangle : getTris()) {
			triangle.calcNormal();
		}
	}
	
	
	
	public void applyTransform(Transform transform) {
		scale(transform.scl());
		rotate(transform.rot());
		translate(transform.pos());
	}
	
	public void applyInverseTransform(Transform transform) {
		translate(transform.pos().getNegative());
		rotate((Rotation3)transform.rot().getNegative());
		scale(transform.scl());
	}
	
	
	
	
	public void translate (Vector3 p) {translate(p.getX(), p.getY(), p.getZ());}
	public void translate (float x, float y, float z) {
		for (Triangle triangle : getTris()) {
			triangle.translate(x,y,z);
		}
	}
	
	public void scale (Vector3 p) {scale(p.getX(), p.getY(), p.getZ());}
	public void scale (float x, float y, float z) {
		for (Triangle triangle : getTris()) {
			triangle.scale(x,y,z);
		}
	}
	
	
	public void rotate (float thetaX, float thetaY, float thetaZ) {rotate(new Rotation3(thetaX,thetaY,thetaZ));}
	public void rotate (Rotation3 p) {
		float[][] matrixX = p.getMatrixX();
		float[][] matrixY = p.getMatrixY();
		float[][] matrixZ = p.getMatrixZ();
		Matrix4x4.transformMesh(this, this, matrixX);
		Matrix4x4.transformMesh(this, this, matrixY);
		Matrix4x4.transformMesh(this, this, matrixZ);
	}
	
	
	public void setColor (Color color) {
		for (Triangle tri : getTris()) {
			tri.color = color;
		}
	}
	
	
	@Override
	public Mesh clone() {
		Triangle[] cloneTris = new Triangle[tris.length];
		for (int i = 0; i < tris.length; i++) {
			cloneTris[i] = tris[i].clone();
		}
		return new Mesh(cloneTris);
	}
	
	public Mesh getEmptyMesh() {
		Triangle[] outTris = new Triangle[tris.length];
		for (int i = 0; i < tris.length; i++) {
			outTris[i] = new Triangle();
		}
		return new Mesh(outTris);
	}

	public Triangle[] getTris() {
		return tris;
	}

}
