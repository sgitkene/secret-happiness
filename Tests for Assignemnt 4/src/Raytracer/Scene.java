package Raytracer;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Scene {

	Camera camera;
	int imageWidth;
	int imageHeight;
	private final Vector a;
	private final Vector b;
	private final Vector c;
	private final Vector e;
	private final double U0 = -1.0;
	private final double U1 = 1.0;
	private final double V0 = -1.0;
	private final double V1 = 1.0;
	private final double S = 2.0;
	private final int MAX_DEPTH = 6;
	
	static final Material AIR = new Material(new Vector(1,1,1), 1,1,1,1);
	
	Set<Shape> shapes = new HashSet<Shape>();

	public Scene(Camera a_camera, int a_imageWidth, int a_imageHeight) {
		this.camera = a_camera;
		this.imageWidth = a_imageWidth;
		this.imageHeight = a_imageHeight;
		e = camera.getEye().getOrigin();
		a = new Vector(camera.getU().smult(U1-U0)); //2= U1-U0
		b = new Vector(camera.getV().smult(V1-V0)); //S=2
		c = new Vector(e.plus(camera.getU().smult(U0)).plus(camera.getV().smult(V0)).minus(camera.getW().smult(S)));

		
	}

	public void addShape(Shape shape) {
		shapes.add(shape);
	}

	/**
	 * Get the closest {@link Shape} that intersects with the given ray.
	 * 
	 * @param ray
	 *            The ray to shoot at shapes.
	 * @return An {@link Intersection} object to store shape and distance or
	 *         <code>null</code> if no intersection has been found.
	 */
	public Intersection intersect(Ray ray) {
		double t0=Double.POSITIVE_INFINITY;
		Shape S0 = null;
		Iterator<Shape> shapeIt=shapes.iterator();
		while(shapeIt.hasNext()){
			Shape nextShape = shapeIt.next();
			double insec=nextShape.intersect(ray);
			if (!Double.isNaN(insec) && (insec -t0 < Vector.EPS) && (insec > Vector.EPS)){
				S0=nextShape;
				t0=insec;
			}
		}
		return new Intersection(S0, t0); 
	}

	/**
	 * Get a ray from the camera through pixels identified by <code>i</code> and
	 * <code>j</code>.
	 * 
	 * @param i
	 *            The pixel (0-based) on the width of the picture.
	 * @param j
	 *            The pixel (0-based) on the height of the picture.
	 * @return A new ray from the camera through the pixel.
	 */
	public Ray getCameraRay(int i, int j) {
		double w = 1.0*i/imageWidth;
		double h = 1.0*j/imageHeight;
		Vector s = new Vector(c.plus(a.smult(w)).plus(b.smult(h)));
		
		return new Ray(e, s.minus(e).normalize());
	}

	/**
	 * Compute the light at some ray. Performs intersection tests and recursive
	 * sampling of up to {@link #MAX_DEPTH} ({@value #MAX_DEPTH}) levels.
	 * 
	 * @param ray
	 *            The ray to compute the light for.
	 * @param depth
	 *            The current recursion depth.
	 * @return The color encoded as vector.
	 */
	public Vector getLight(Ray ray, int depth, double a_opticDensity) { //what light ray, how deep recursion is and what material ray is in
		Vector color = new Vector(0,0,0);
		Intersection inter = intersect(ray);
		
		
		
		double opticDensity= a_opticDensity;
		if (inter.hasIntersected()) {
			Shape S=inter.getShape();
			if (depth>MAX_DEPTH) {
				return S.getEmission();
			}
			else {
				/*fresnel equations. Idea: randomly (weighted based on intensity) choose one type of how the ray continues travel. 
				* possibilities: 
				* *ambient: randomly continue (//no reflection)
				* specularly: fresnel equations for how the ray shall continue.
				* all object are assumed to be next to air (n=1.0)
				*/
				double snell1 = opticDensity;
				double snell2 = S.getMat().getRefractiveIndex(); // n2/n1 where n1 is air
				Vector p = ray.getOrigin().plus(ray.getDirection().smult(inter.getT())); //point of incident
				Vector N = inter.getShape().getNormVec(p); //normal vector at that point
				Vector r;
				boolean piercesSurface=false;
				if (N.dot(ray.getDirection())>0){ //check if ray spawned "inside"
					N=N.normalize().minus(); //if yes, normal vector goes against ray
				}
				else {
					N=N.normalize(); //otherwise normal vector doesn't do a thing
				}
				Vector l = ray.getDirection(); //for shortness
				Vector V= l.minus();
				Vector reflect = N.smult(V.dot(N)*2).minus(V); //reflection 
				if (S.getMat().getAmbient()-1.0<Vector.EPS){ //is material translucent?
					double c=N.minus().dot(l); //"angle" between vector and nomral
					double snell=snell1/snell2;
					Vector t = l.smult(snell).plus(N.smult(snell*c-Math.sqrt(1-(snell*snell*(1-c*c))))); //refracted vector
					
					//change if/else branches to switch case statements probably... use Math.random() and weights.
//					double Rs = Math.pow((V.dot(N)-snell*t.dot(N.minus()))/(V.dot(N)+snell*t.dot(N.minus())), 2); //fresnels equations concerning the intensity..
//					double Rp = Math.pow((-snell*V.dot(N)+t.dot(N.minus()))/(snell*V.dot(N)+t.dot(N.minus())), 2);//..there should be a better approximation..
//																												  //..because we ignore polarity.
//					double ref =(Rs+Rp)/2;
					double ref0= ((snell1-snell2)/(snell1+snell2));
					ref0= ref0*ref0;
					double ref = ref0 + ((1-ref0)*Math.pow((1-N.minus().dot(l)), 5)); //Schlick
					double ranDouble = ThreadLocalRandom.current().nextDouble();
				
					if (ref - ranDouble < 0){ //choose wether to take reflection or refraction based on calculations above that take angle of incident into account
						
						r = t;
						piercesSurface=true;
					}
					else {
						r=reflect;
					}
				
				}
				else if (S.getMat().getShiny()<0.3){ //if object is mirror, reflect
					r=reflect;
				}
				else { // not specular reflection, spawn a random new vector (renders surface matte and opaque).
					//random direction:
					double r1 = ThreadLocalRandom.current().nextDouble(2*Math.PI); // azimuthal angle
					double r2 = Math.pow(ThreadLocalRandom.current().nextDouble(0.5), 1.0/S.getMat().getShiny())*Math.PI; // polar angle

					double x = Math.cos(r1)*Math.sin(r2); 
					double y = Math.sin(r1)*Math.sin(r2);
					double z = Math.cos(r2);
					//make new orthonormal basis
					Vector w;
					if (ThreadLocalRandom.current().nextDouble()>1.0/Math.pow(S.getMat().getShiny(), 2)) {w=reflect;}
					else {w=N;}
					Vector u = new Vector(0,1,1).cross(w).normalize(); //the chosen vector should not be collinear with any plane's normal vector.
					Vector v = w.cross(u).normalize();
					//make vector using random direction and orthonormal basis
					r = new Vector(u.smult(x).plus(v.smult(y)).plus(w.smult(z)));
					if (r.dot(N)<0){
						r=new Vector(-r.getX(), -r.getY(), r.getZ());
					}
					
				}
				//check if optical density should change
				if (piercesSurface&&(opticDensity != S.getMat().getRefractiveIndex())){opticDensity=S.getMat().getRefractiveIndex();}
				else if (piercesSurface){opticDensity= AIR.getRefractiveIndex();} //known limitations: can't know when there's a sphere within a sphere
				Ray rR= new Ray(p, r);
				Vector rC=getLight(rR, depth + 1, opticDensity);
				color=S.getEmission().plus(S.getMat().getColor().mul(rC));
				
				return color;
			}
		}
		return color;
	}
	
	
	
	public void raytrace(final BufferedImage image, final int samples, final int startWidth, final int endWidth) {
		if ((startWidth<0) || (endWidth>imageWidth) || (startWidth>=endWidth)){
			System.out.println("Width invalid. FAIL");
			return;
		}
		for (int i = startWidth; i < endWidth; i++) {
			for (int j = 0; j < imageHeight; j++) {
				Ray leRay=getCameraRay(i,j);
				Vector light= new Vector(0,0,0);
				for (int samp=0; samp<samples; samp++){
					light = light.plus(getLight(leRay, 0, AIR.getRefractiveIndex()));
				}
				light=light.smult(1.0/samples);
				image.setRGB(i, j, light.toRGB());
			}
			//System.out.println("calculated " + i + " of " + image.getWidth() + ".");
		}
	}
}
