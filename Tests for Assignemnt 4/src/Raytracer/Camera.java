package Raytracer;

/* This creates a virtual Camera, and is used to determine
 * where cast rays should go through.
 * 
 */

public class Camera {

	private final Ray eye;		// Camera origin and direction.
	private final Vector up;	// What is to be considered upwards.
	
	private final Vector u;		//orthonormal basis
	private final Vector v;		//...
	private final Vector w;		//...
	
	
	public Camera(Ray eye, Vector up) {
		this.eye = new Ray(eye);
		this.up = new Vector(up);
		w = new Vector(eye.getDirection().minus().normalize());
		u = new Vector(up.cross(w).normalize());
		v = new Vector(w.cross(u).normalize().minus());
		
	}

	public Ray getEye() {
		return eye;
	}

	public Vector getUp() {
		return up;
	}
	
	public Vector getU() {
		return u;
	}
	
	public Vector getV() {
		return v;
	}
	
	public Vector getW() {
		return w;
	}
}
