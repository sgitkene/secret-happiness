package Raytracer;

public class Material {
	private final double refractiveIndex, diffuse, ambient, shiny;
	private final Vector color;
	public Material(Vector a_color, double a_specular, double a_diffuse, double a_ambient, double a_shiny) {
		this.color = new Vector(a_color);
		this.refractiveIndex = a_specular;
		this.diffuse = a_diffuse;
		this.ambient = a_ambient;
		this.shiny = a_shiny;
	}
	public Material(Material a_mat){
		this.color = new Vector(a_mat.getColor());
		this.refractiveIndex = a_mat.getRefractiveIndex();
		this.diffuse = a_mat.getDiffuse();
		this.ambient = a_mat.getAmbient();
		this.shiny = a_mat.getShiny();
	}
	//TODO: not up with this implementation.
	public Material(String name){
		switch (name) {
		case "Mirror":
			this.color = new Vector(1,1,1);
			this.refractiveIndex = 1;
			this.diffuse = 0.01;
			this.ambient = 0;
			this.shiny = 1000;
			break;
		case "Plastic":
			this.color = new Vector(1,1,1);
			this.refractiveIndex = 0;
			this.diffuse = 1;
			this.ambient = 1;
			this.shiny = 1;
			break;
		case "Abalone":
			this.color = new Vector(1,1,1);
			this.refractiveIndex = 0.2;
			this.diffuse = 0.8;
			this.ambient = 0.2;
			this.shiny = 100;
			break;
		default:
			this.color = new Vector(1,0.4,0.4);
			this.refractiveIndex = 0.01;
			this.diffuse = 0.9;
			this.ambient = 1;
			this.shiny = 10;
		}
	}
	public double getShiny() {
		return shiny;
	}
	public double getDiffuse() {
		return diffuse;
	}
	public double getAmbient() {
		return ambient;
	}
	public double getRefractiveIndex() {
		return refractiveIndex;
	}
	public Vector getColor(){
		return color;
	}

}
