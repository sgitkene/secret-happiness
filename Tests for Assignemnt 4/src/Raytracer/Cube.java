/**
 * 
 */
package Raytracer;

/**
 * @author Max
 *
 */
public class Cube extends Shape {

	private Vector emission, origin, a, b, c, center;
	private Material mat;
	private double length;
	private Square[] squares = new Square[6]; 
	public Cube(Vector origin, Vector a, Vector b, Vector c, Material mat, Vector emission) {
		this.origin = origin;
		this.a = a.normalize();
		this.b = b.normalize();
		this.c = c.normalize();
		this.emission = emission;
		this.mat = mat;
		length = a.norm();
		center = origin.plus(a.plus(b.smult(length).plus(c.smult(length))).smult(0.5));
		squares[0] = new Square(origin, this.a.smult(length), this.b, mat, emission);
		squares[1] = new Square(origin, this.b.smult(length), this.c, mat, emission);
		squares[2] = new Square(origin, this.c.smult(length), this.a, mat, emission);
		squares[3] = new Square(origin.plus(this.a.smult(length)), this.c.smult(length), this.b, mat, emission);
		squares[4] = new Square(origin.plus(this.b.smult(length)), this.a.smult(length), this.c, mat, emission);
		squares[5] = new Square(origin.plus(this.c.smult(length)), this.b.smult(length), this.a, mat, emission);
		
	}

	/* (non-Javadoc)
	 * @see assignment3.Shape#intersect(assignment3.Ray)
	 */
	@Override
	double intersect(Ray r) {
		double[] ds = new double[6];
		for (int i = 0; i<squares.length; i++){
			ds[i]=squares[i].intersect(r);
		}
		double min = Double.NaN;
		for (int i = 0; i<ds.length; i++){
			if (Double.isNaN(min)){
				min = ds[i];
			}
			else{
				if (!Double.isNaN(ds[i])){
					min = Double.min(ds[i], min);
				}
			}
		}
		return min;
	}

	/* (non-Javadoc)
	 * @see assignment3.Shape#getNormVec(assignment3.Vector)
	 */
	@Override
	Vector getNormVec(Vector incoming) {
		for (int i = 0; i<squares.length; i++){
			if (!Double.isNaN(squares[i].intersect(new Ray(center, incoming.minus(center))))){
				return squares[i].getNormVec(incoming);
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see assignment3.Shape#getMat()
	 */
	@Override
	public Material getMat() {
		return mat;
	}

	/* (non-Javadoc)
	 * @see assignment3.Shape#getEmission()
	 */
	@Override
	public Vector getEmission() {
		return emission;
	}

}
