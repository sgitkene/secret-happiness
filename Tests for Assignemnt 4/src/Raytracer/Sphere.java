package Raytracer;


public class Sphere extends Shape{

	private double radius;
	private Vector center, emission;
	private Material mat;

	public Sphere(double a_radius, Vector position, Material a_mat, Vector a_emission) {
		this.radius = a_radius;
		this.center = new Vector(position);
		this.mat = new Material(a_mat);
		this.emission = new Vector(a_emission);
	}
	public Sphere(double a_radius, Vector position, Vector a_color, Vector a_emission) {
		this.radius = a_radius;
		this.center = new Vector(position);
		this.mat = new Material(a_color, 1,1,2,1);
		this.emission = new Vector(a_emission);
	}
	public Vector getCenter() {
		return center;
	}
	public Material getMat(){
		return mat;
	}
	public Vector getNormVec(Vector incoming) {
		return new Vector(incoming.minus(this.center));
	}
	public Vector getEmission(){
		return emission;
	}
	
	public double intersect(Ray r) {
		Vector c = this.center;
		Vector p = r.getOrigin();
		Vector v = c.minus(p);
		Vector d = r.getDirection();
		double b = v.dot(d);
		double det = b*b - v.dot(v) + radius*radius;
		if (det < 0.0){
			return Double.NaN;
		}
		else {
			det = Math.sqrt(det);
		}
		if (b-det > Vector.EPS) { return b-det;}
		if (b+det > Vector.EPS) { return b+det;}
		
		return Double.NaN;
		//discr=Math.pow(v.dot(d), 2) - v.dot(v) + Math.pow(this.radius, 2);
		
		/*if (v.dot(d) < Vector.EPS) {
			if (v.norm() - this.radius > Vector.EPS){
				return Double.NaN;
			}
			else if ((v.norm() - this.radius < Vector.EPS) && (v.norm() - this.radius > -Vector.EPS)){
				return 0;
			}
			else {
				
			}
			*/
			
		/*// TODO
		if (discr >= 0){
			t_1 = v.minus().dot(d)+Math.sqrt(discr);
			t_2 = v.minus().dot(d)-Math.sqrt(discr);
			if (Double.min(t_1, t_2)>0){
				return Double.min(t_1, t_2);
			}
			else if (Double.max(t_1, t_2)>0) {
				return Double.max(t_1, t_2);
			}
			else return Double.NaN;
		}
		else{
			return Double.NaN;
		}*/
	}
	/*public Ray reflect(Ray incoming) {
		Vector p = new Vector();//point of impact
		Vector N = getNormVec(p).normalize();
		Vector d = new Vector(N.smult(2*N.dot(incoming.getDirection())));
		return new Ray(p,d);
	}*/


}
