package tmholt.dimthree.modelmesh;
import java.awt.Color;

public class Model implements Cloneable {
	
	private Mesh mesh;
	private Transform transform;
	
	public Model(Mesh mesh) {
		this.mesh = mesh;
		this.transform = new Transform();
	}
	
	public Model(Mesh mesh, Transform transform) {
		this.mesh = mesh;
		this.transform = transform;
	}
	
	public Transform getTransform() {return transform;}
	
	public Mesh toWorldspace() {
		Mesh out = mesh.clone();
		out.applyTransform(transform);
		out.calcNormals();
		return out;
	}
	
	@Override
	public Model clone() {
		return new Model(mesh.clone(),transform.clone());
	}
	
	public void setColor (Color color) {
		mesh.setColor(color);
	}
	
	
}
