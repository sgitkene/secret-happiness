package Raytracer;

public class Plane extends Shape{
	private final Vector origin, a, b, norm, emission;
	private Material mat;
	public Plane(Vector a_origin, Vector a_a, Vector a_b, Material a_mat, Vector a_emission) {
		this.origin = new Vector(a_origin);
		this.a = new Vector(a_a);
		this.b = new Vector(a_b);
		this.norm = a.cross(b);
		this.mat = new Material(a_mat);
		this.emission = new Vector(a_emission);
		if (this.isCollinear()){
		}
	}
	public double intersect(Ray r){
		double result;
		result = this.origin.minus(r.getOrigin()).dot(this.norm)/r.getDirection().dot(this.norm);
		return result;
	}
	public Vector getA() {
		return a;
	}
	public Vector getB() {
		return b;
	}
	public Vector getOrigin() {
		return origin;
	}
	public Vector getNorm(){
		return norm;
	}
	public Vector getNormVec(Vector incoming){
		return norm.normalize();
	}
	public Material getMat(){
		return mat;
	}
	public Vector getEmission(){
		return emission;
	}
	
	public boolean isCollinear(){
		return (false);
	}

}
