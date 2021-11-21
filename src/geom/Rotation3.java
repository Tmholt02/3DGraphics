package geom;

public class Rotation3 extends Vector3 {
	
	public static final float mod = (float)(2*Math.PI);
	
	public Rotation3(float x, float y, float z) {super(x,y,z);mod(mod,mod,mod);}
	public Rotation3() {super();mod(mod,mod,mod);}
	
	public void setX (float x)   {super.setX(x);super.modX(mod);}
	public void setY (float y)   {super.setY(y);super.modY(mod);}
	public void setZ (float z)   {super.setZ(z);super.modZ(mod);}
	
	public void addX (float x)   {super.addX(x);super.modX(mod);}
	public void addY (float y)   {super.addY(y);super.modY(mod);}
	public void addZ (float z)   {super.addZ(z);super.modZ(mod);}
	
	public void mpyX (float x)   {super.mpyX(x);super.modX(mod);}
	public void mpyY (float y)   {super.mpyY(y);super.modY(mod);}
	public void mpyZ (float z)   {super.mpyZ(z);super.modZ(mod);}
	
	public void modX (float x)   {super.modX(x);super.modX(mod);}
	public void modY (float y)   {super.modY(y);super.modY(mod);}
	public void modZ (float z)   {super.modZ(z);super.modZ(mod);}
	
	public float[][] getMatrixX() {
		float[][] matrixX = Matrix4x4.getEmptyMatrix();
		matrixX[0][0] = 1f;
		matrixX[1][1] = (float) Math.cos(getX());
		matrixX[1][2] = (float) Math.sin(getX());
		matrixX[2][1] = (float)-Math.sin(getX());
		matrixX[2][2] = (float) Math.cos(getX());
		matrixX[3][3] = 1f;
		
		return matrixX;
	}
	
	public float[][] getMatrixY() {
		float[][] matrixY = Matrix4x4.getEmptyMatrix();
		matrixY[0][0] = (float) Math.cos(getY());
		matrixY[0][2] = (float) Math.sin(getY());
		matrixY[2][0] = (float)-Math.sin(getY());
		matrixY[1][1] = 1f;
		matrixY[2][2] = (float) Math.cos(getY());
		matrixY[3][3] = 1f;
		
		return matrixY;
	}

	public float[][] getMatrixZ() {
		float[][] matrixZ = Matrix4x4.getEmptyMatrix();
		matrixZ[0][0] = (float) Math.cos(getZ());
		matrixZ[0][1] = (float) Math.sin(getZ());
		matrixZ[1][0] = (float)-Math.sin(getZ());
		matrixZ[1][1] = (float) Math.cos(getZ());
		matrixZ[2][2] = 1f;
		matrixZ[3][3] = 1f;
		
		return matrixZ;
	}
	
}
