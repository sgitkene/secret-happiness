package Raytracer;

public class Triangle extends Shape {
	
	private Vector a,b,c, ab, ac, norm, emission;
	private Material mat;
	private Plane plane;
	
	public Triangle(Vector a, Vector b, Vector c, Material mat, Vector emission) {
		// TODO Auto-generated constructor stub
		this.a = a;
		this.b = b;
		this.c = c;
		this.mat = mat;
		this.emission = emission;
		this.ab = b.minus(a);
		this.ac = c.minus(a);
		this.plane = new Plane(a, ab.normalize(), ac.normalize(), mat, emission);
		this.norm = plane.getNorm();
	}

	@Override
	double intersect(Ray r) { //http://www.blackpawn.com/texts/pointinpoly/default.html for explanation
		// TODO Auto-generated method stub
		double d = plane.intersect(r);
		Vector P = r.getDirection().smult(d).plus(r.getOrigin());
		if (sameSide(P, a, b, c) && sameSide (P, b, a, c) && sameSide(P, c, a, b)){
			return d;
		}
		else {
			return Double.NaN;
		}
	}
	private boolean sameSide(Vector p1, Vector p2, Vector a, Vector b){
		Vector cp1 = b.minus(a).cross(p1.minus(a));
		Vector cp2 = b.minus(a).cross(p2.minus(a));
		return (cp1.dot(cp2) >=0);
		
	}

	@Override
	Vector getNormVec(Vector incoming) {
		return plane.getNormVec(incoming);
	}

	@Override
	public Material getMat() {
		return mat;
	}

	@Override
	public Vector getEmission() {
		return emission;
	}

}
