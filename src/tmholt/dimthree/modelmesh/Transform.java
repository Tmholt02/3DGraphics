package tmholt.dimthree.modelmesh;

import tmholt.dimthree.geom.Matrix4x4;
import tmholt.dimthree.geom.Vector3;

public class Transform implements Cloneable {
	
	private Vector3 position, scale;
	private Rotation3 rotation;
	
	public Transform() {
		position = new Vector3(0f,0f,0f);
		rotation = new Rotation3(0f,0f,0f);
		scale    = new Vector3(1f,1f,1f);
	}
	
	public Vector3 pos() {return position;}
	public float posX() {return pos().getX();}
	public float posY() {return pos().getY();}
	public float posZ() {return pos().getZ();}

	public Rotation3 rot() {return rotation;}
	public float rotX() {return rot().getX();}
	public float rotY() {return rot().getY();}
	public float rotZ() {return rot().getZ();}
	
	public Vector3 scl() {return scale;}
	public float sclX() {return scl().getX();}
	public float sclY() {return scl().getY();}
	public float sclZ() {return scl().getZ();}
	

	@Override
	public Transform clone() {
		Transform clone = new Transform();
		clone.pos().set(pos().clone());
		clone.rot().set(rot().clone());
		clone.scl().set(scl().clone());
		return clone;
	}
	
	public void move (float ahead, float right, float down) {		
		
		float[][] matrixX = rotation.getMatrixX();
		float[][] matrixY = rotation.getMatrixY();
		float[][] matrixZ = rotation.getMatrixZ();
		
		Vector3 shift = new Vector3(right,down,ahead);
		
		Matrix4x4.transformPoint(shift,shift,matrixX);
		Matrix4x4.transformPoint(shift,shift,matrixY);
		Matrix4x4.transformPoint(shift,shift,matrixZ);
		
		position.add(shift);
	}
	
}
