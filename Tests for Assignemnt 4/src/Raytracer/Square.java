package Raytracer;

public class Square extends Shape {
	
	private Vector origin, a, b, emission;
	private Material mat;
	private Triangle tri1,tri2;
	public Square(Vector origin, Vector a, Vector b, Material mat, Vector emission) {
		this.origin = origin;
		this.a = a;
		this.b = b.normalize();
		this.mat = mat;
		this.emission = emission;
		tri1 = new Triangle(origin, origin.plus(a), origin.plus(this.b.smult(a.norm())), mat, emission);
		tri2 = new Triangle(origin.plus(a.plus(this.b.smult(a.norm()))), origin.plus(a), origin.plus(this.b.smult(a.norm())), mat, emission);
	}

	@Override
	double intersect(Ray r) {
		double d = tri1.intersect(r);
		if (!Double.isNaN(d)){
			return d;
		}
		else {
			return tri2.intersect(r);
		}
	}

	@Override
	Vector getNormVec(Vector incoming) {
		return tri1.getNormVec(incoming);
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
