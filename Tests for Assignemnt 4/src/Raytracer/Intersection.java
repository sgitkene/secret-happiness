package Raytracer;

public class Intersection {
	
	private final Shape shape;
	private final double t;
	
	public Intersection(Shape shape, double t) {
		this.shape = shape;
		this.t = t;
	}

	public Shape getShape() {
		return shape;
	}

	public double getT() {
		return t;
	}
	
	public boolean hasIntersected() {
		return (shape != null);
	}
	
}
