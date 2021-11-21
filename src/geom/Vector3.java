package geom;

public class Vector3 implements Cloneable {
	
	private float x,y,z;
	
	public Vector3 (float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3 () {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	
	public void setX (float x)   {this.x=x;}
	public void setY (float y)   {this.y=y;}
	public void setZ (float z)   {this.z=z;}
	public void set  (Vector3 p) {set(p.x,p.y,p.z);}
	public void set  (float xyz) {set(xyz, xyz, xyz);}
	public void set  (float x, float y, float z) {
		setX(x);
		setY(y);
		setZ(z);
	}
	
	public void addX (float x)   {this.x+=x;}
	public void addY (float y)   {this.y+=y;}
	public void addZ (float z)   {this.z+=z;}
	public void add  (Vector3 p) {add(p.x,p.y,p.z);}
	public void add  (float xyz) {add(xyz, xyz, xyz);}
	public void add  (float x, float y, float z) {
		addX(x);
		addY(y);
		addZ(z);
	}
	
	public void mpyX (float x)   {this.x*=x;}
	public void mpyY (float y)   {this.y*=y;}
	public void mpyZ (float z)   {this.z*=z;}
	public void mpy  (Vector3 p) {mpy(p.x,p.y,p.z);}
	public void mpy  (float xyz) {mpy(xyz, xyz, xyz);}
	public void mpy  (float x, float y, float z) {
		mpyX(x);
		mpyY(y);
		mpyZ(z);
	}
	
	public void modX (float x)   {this.x%=x;}
	public void modY (float y)   {this.y%=y;}
	public void modZ (float z)   {this.z%=z;}
	public void mod  (Vector3 p) {mod(p.x,p.y,p.z);}
	public void mod  (float xyz) {mod(xyz, xyz, xyz);}
	public void mod  (float x, float y, float z) {
		modX(x);
		modY(y);
		modZ(z);
	}

	public float getX() {return x;}
	public float getY() {return y;}
	public float getZ() {return z;}
	
	public Vector3 getNegative() {
		Vector3 cln = this.clone();
		cln.mpy(-1f,-1f,-1f);
		return cln;
	}
	
	
	public void normalize() {
		float l = 1f/(float)Math.sqrt(x*x + y*y + z*z);
		mpy(l, l, l);
	}
	
	
	@Override
	public Vector3 clone() {
		return new Vector3(x, y, z);
	}

	
	public static float dotProduct(Vector3 line1, Vector3 line2) {
		return line1.getX() * line2.getX() + 
			   line1.getY() * line2.getY() +
			   line1.getZ() * line2.getZ();
	}
	
	public static Vector3 crossProduct(Vector3 line1, Vector3 line2) {
		Vector3 out = new Vector3();
		out.x = line1.getY()*line2.getZ() - line1.getZ()*line2.getY();
		out.y = line1.getZ()*line2.getX() - line1.getX()*line2.getZ();
		out.z = line1.getX()*line2.getY() - line1.getY()*line2.getX();
		return out;
	}
	
	public String toString() {
		return "[x: " + x +",  y: " + y + ",  z: " + z + "]";
	}
	
	
}