package geom;

public class Matrix4x4 {
	
	public static float[][] getEmptyMatrix() {
		return new float[][] {
			{ 0f , 0f , 0f , 0f },
			{ 0f , 0f , 0f , 0f },
			{ 0f , 0f , 0f , 0f },
			{ 0f , 0f , 0f , 0f }
		};
	}
	
	public static float[][] getIdentityMatrix() {
		return new float[][] {
			{ 1f , 0f , 0f , 0f },
			{ 0f , 1f , 0f , 0f },
			{ 0f , 0f , 1f , 0f },
			{ 0f , 0f , 0f , 1f }
		};
	}
	
	public static void transformPoint (Vector3 in, Vector3 out, float[][] matrix) {

		float outX = in.getX()*matrix[0][0]  +  in.getY()*matrix[1][0]  +  in.getZ()*matrix[2][0]  +  matrix[3][0] ;
		float outY = in.getX()*matrix[0][1]  +  in.getY()*matrix[1][1]  +  in.getZ()*matrix[2][1]  +  matrix[3][1] ;
		float outZ = in.getX()*matrix[0][2]  +  in.getY()*matrix[1][2]  +  in.getZ()*matrix[2][2]  +  matrix[3][2] ;
		float w    = in.getX()*matrix[0][3]  +  in.getY()*matrix[1][3]  +  in.getZ()*matrix[2][3]  +  matrix[3][3] ;
		
		outX /= w;
		outY /= w;
		outZ /= w;
		
		out.setX(outX);
		out.setY(outY);
		out.setZ(outZ);
		
	}
	
	public static void transformTriangle (Triangle in, Triangle out, float[][] matrix) {
		
		transformPoint(in.p1, out.p1, matrix);
		transformPoint(in.p2, out.p2, matrix);
		transformPoint(in.p3, out.p3, matrix);
		
	}
	
	public static void transformMesh (Mesh in, Mesh out, float[][] matrix) {
		
		Triangle[] inTris = in.getTris();
		Triangle[] outTris = out.getTris();
		
		for (int i = 0; i < in.getTris().length; i++) {
			transformTriangle(inTris[i], outTris[i], matrix);
		}
		
	}
}
