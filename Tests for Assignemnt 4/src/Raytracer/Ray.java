package Raytracer;

public class Ray {


	private final Vector origin, direction;
	public Ray(Vector a_origin, Vector a_direction) {
		
		this.origin = new Vector(a_origin);
		if (a_direction.norm()!=0){
			this.direction = new Vector(a_direction.normalize());
		}
		else{
			direction = new Vector(a_direction);
		}
	}
	public Ray(Ray a_ray) {
		
		this.origin = new Vector(a_ray.getOrigin());
		this.direction= new Vector(a_ray.getDirection());
	}
	public Vector getOrigin() {
		return origin;
	}
	public Vector getDirection() {
		return direction;
	}
}
