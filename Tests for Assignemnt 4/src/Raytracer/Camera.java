package Raytracer;

public class Camera {

	private final Ray eye;
	private final Vector up;
	
	private final Vector u;
	private final Vector v;
	private final Vector w;
	
	
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
