package Raytracer;

/**This is the buildplan for a cube.
 * It consists of 6 squares.
 * @author Max
 * This is is currently a 
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

	@Override
	/*
	 * (non-Javadoc)
	 * @see Raytracer.Shape#intersect(Raytracer.Ray)
	 * Since a Ray that hits a Cube always hits two squares of the cube
	 * we also have to determine which collision occurs earlier.
	 */
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
	/*
	 * (non-Javadoc)
	 * @see Raytracer.Shape#getNormVec(Raytracer.Vector)
	 * The client has to supply the point on which he wants the normal vector.
	 * The cube then shoots a ray through that point and checks with which
	 * square it intersects. It then returns that square's normal vector.
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
	/*
	 * (non-Javadoc)
	 * @see Raytracer.Shape#getMat()
	 */
	@Override
	public Material getMat() {
		return mat;
	}
	/*
	 * (non-Javadoc)
	 * @see Raytracer.Shape#getEmission()
	 */
	@Override
	public Vector getEmission() {
		return emission;
	}

}
