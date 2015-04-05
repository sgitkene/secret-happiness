package Raytracer;

public class Vector {

	public static final Vector VEC_ZERO = new Vector(0, 0, 0);
	public static final double EPS = 0.0000000001;
	private final double x, y, z;
	

	//overloaded Constructor
	public Vector(double a_x, double a_y, double a_z) {
		super();
		this.x = a_x;
		this.y = a_y;
		this.z = a_z;
	}
	public Vector(Vector a_vector) {
		super();
		this.x = a_vector.getX();
		this.y = a_vector.getY();
		this.z = a_vector.getZ();
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getZ() {
		return this.z;
	}
	
	public Vector cross(Vector o) {
		double rx = this.y * o.z - this.z * o.y;
		double ry = this.z * o.x - this.x * o.z;
		double rz = this.x * o.y - this.y * o.x;
		Vector result = new Vector(rx, ry, rz);
		return result;
	}

	public double dot(Vector r) {
		return (this.x*r.x + this.y*r.y + this.z*r.z);
	}

	public Vector minus(Vector o) {
		Vector result = new Vector(this.x - o.x, this.y - o.y, this.z - o.z);
		return result;
	}
	public Vector minus() {
		Vector result = new Vector(-this.x, -this.y, -this.z);
		return result;
	}

	public Vector mul(Vector o) {
		Vector result = new Vector(this.x*o.getX(), this.y*o.getY(), this.z*o.getZ());
		return result;
	}

	public double norm() {
		return Math.sqrt(this.dot(this));
	}

	public Vector normalize() {
		if (this.norm() > EPS) {
			return this.smult(1.0/this.norm());
		}
		else{
			return VEC_ZERO;
		}
	}

	public Vector plus(Vector o) {
		Vector result = new Vector(this.x + o.x, this.y + o.y, this.z + o.z);
		return result;
	}

	public Vector smult(double f) {
		Vector result = new Vector(this.x*f, this.y*f, this.z*f);
		return result;
	}

	public int toRGB() {
		return ((int) (Math.pow(clamp(x, 0, 1),0.7) * 255. + .5) << 16)
				| ((int) (Math.pow(clamp(y, 0, 1), 0.7) * 255. + .5) << 8)
				| ((int) (Math.pow(clamp(z, 0, 1), 0.7) * 255. + .5));
	}

	private static double clamp(double value, double min, double max) {
		double d = value;
		if (d < min) {
			d = min;
		} else if (d > max) {
			d = max;
		}
		return d;
	}

	@Override
	public String toString() {
		return "Vector [x=" + x + ", y=" + y + ", z=" + z + "]";
	}

}
