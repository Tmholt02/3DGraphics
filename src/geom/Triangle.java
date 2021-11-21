package geom;

import java.awt.Color;
import java.awt.Polygon;

public class Triangle implements Cloneable, Comparable<Object> {
	
	public Vector3 p1,p2,p3;
	public Vector3 normal;
	public Color color;
	
	public Triangle (Vector3 p1, Vector3 p2, Vector3 p3) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}
	
	public Triangle (
		float x1, float y1, float z1,
		float x2, float y2, float z2,
		float x3, float y3, float z3
		) {
		this.p1 = new Vector3(x1, y1, z1);
		this.p2 = new Vector3(x2, y2, z2);
		this.p3 = new Vector3(x3, y3, z3);
	}
	
	public Triangle () {
		this.p1 = new Vector3();
		this.p2 = new Vector3();
		this.p3 = new Vector3();
	}
	
	public Vector3 getCenter() {
		return new Vector3(
				(p1.getX()+p2.getX()+p3.getX())/3,
				(p1.getY()+p2.getY()+p3.getY())/3,
				(p1.getZ()+p2.getZ()+p3.getZ())/3
			);
	}
	
	public Vector3 calcNormal() {
		Vector3 line1 = p2.clone();
		line1.add(p1.getNegative());
		
		Vector3 line2 = p3.clone();
		line2.add(p1.getNegative());
		
		normal = Vector3.crossProduct(line1, line2);
		normal.normalize();
		
		return normal;
	}
	
	public void applyTransform(Transform transform) {
		scale(transform.scl());
		rotate(transform.rot());
		translate(transform.pos());
	}
	
	public void applyInverseTransform(Transform transform) {
		translate(transform.pos().getNegative());
		rotate(transform.rot().getNegative());
		scale(transform.scl());
	}
	
	public void translate (Vector3 p) {translate(p.getX(), p.getY(), p.getZ());}
	public void translate (float x, float y, float z) {
		p1.add(x,y,z);
		p2.add(x,y,z);
		p3.add(x,y,z);
	}
	
	public void scale (Vector3 p) {scale(p.getX(), p.getY(), p.getZ());}
	public void scale (float x, float y, float z) {
		p1.mpy(x,y,z);
		p2.mpy(x,y,z);
		p3.mpy(x,y,z);
	}
	
	public void rotate (Vector3 p) {rotate(p.getX(), p.getY(), p.getZ());}
	public void rotate (float thetaX, float thetaY, float thetaZ) {
		
		float[][] matrixX = Matrix4x4.getEmptyMatrix();
		matrixX[0][0] = 1f;
		matrixX[1][1] = (float) Math.cos(thetaX);
		matrixX[1][2] = (float) Math.sin(thetaX);
		matrixX[2][1] = (float) -Math.sin(thetaX);
		matrixX[2][2] = (float) Math.cos(thetaX);
		matrixX[3][3] = 1f;
		
		float[][] matrixY = Matrix4x4.getEmptyMatrix();
		matrixY[0][0] = (float) Math.cos(thetaY);
		matrixY[0][2] = (float) Math.sin(thetaY);
		matrixY[2][0] = (float) -Math.sin(thetaY);
		matrixY[1][1] = 1f;
		matrixY[2][2] = (float) Math.cos(thetaY);
		matrixY[3][3] = 1f;
		
		float[][] matrixZ = Matrix4x4.getEmptyMatrix();
		matrixZ[0][0] = (float) Math.cos(thetaZ);
		matrixZ[0][1] = (float) Math.sin(thetaZ);
		matrixZ[1][0] = (float) -Math.sin(thetaZ);
		matrixZ[1][1] = (float) Math.cos(thetaZ);
		matrixZ[2][2] = 1f;
		matrixZ[3][3] = 1f;
		
		Matrix4x4.transformTriangle(this,this,matrixX);
		Matrix4x4.transformTriangle(this,this,matrixY);
		Matrix4x4.transformTriangle(this,this,matrixZ);
		
	}
	
	
	public Polygon toPolygon() {
		return new Polygon(
				new int[]  { (int)p1.getX(),  (int)p2.getX(),  (int)p3.getX() },
				new int[]  { (int)p1.getY(),  (int)p2.getY(),  (int)p3.getY() },
				3
		);
	}
	
	@Override
	public Triangle clone() {
		return new Triangle( p1.clone(),  p2.clone(), p3.clone());
	}

	@Override
	public int compareTo(Object arg0) {
		if (!(arg0 instanceof Triangle))
			return 0;
		Triangle tri = (Triangle)arg0;
		float zMe = (p1.getZ()     + p2.getZ()     + p3.getZ())    /3;
		float zIn = (tri.p1.getZ() + tri.p2.getZ() + tri.p3.getZ())/3;
		if (zMe > zIn) return -1;
		if (zMe < zIn) return 1;
		return 0;
	}
}
