package Raytracer;

public abstract class Shape {
	abstract double intersect(Ray r);
	abstract Vector getNormVec(Vector incoming);
	public abstract Material getMat();
	public abstract Vector getEmission();
	
	
}
