package Raytracer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class SceneParser {
	Set<Shape> shapes = new HashSet<Shape>();
	Scanner scanner;
	boolean hasParsed = false;
	
	public SceneParser(File file) {
		try {
			parseFile(file);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception: failed to parse file.");
			hasParsed = false;
		}
	}
	
	public SceneParser(){
	}
	
	public void parseFile(File source) throws IOException{
		try {
			scanner = new Scanner(source);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Exception: file not found. ");
		}
		int i=0; //counts the number of shapes
		while (scanner.hasNextLine()){
			String line = scanner.nextLine();
			i++;
			Scanner l = new Scanner(line);
			if (line.startsWith("Plane")){
				l.skip("Plane");
				shapes.add(new Plane(new Vector(l.nextDouble(), l.nextDouble(), l.nextDouble()), new Vector(l.nextDouble(), l.nextDouble(), l.nextDouble()),
						new Vector(l.nextDouble(), l.nextDouble(), l.nextDouble()), new Material(new Vector(l.nextDouble(), l.nextDouble(), l.nextDouble()),
						l.nextDouble(), l.nextDouble(), l.nextDouble(), l.nextDouble()), new Vector(l.nextDouble(), l.nextDouble(), l.nextDouble())));
			}
			else if (line.startsWith("Sphere")){
				l.skip("Sphere");
				double[] n = new double[14];
				for (int na = 0; na<n.length; na++){
					n[na] = l.nextDouble();
				}
				shapes.add(new Sphere(n[0], new Vector(n[1], n[2], n[3]), new Material(new Vector(n[4],
						n[5], n[6]), n[7], n[8], n[9], n[10]), new Vector(n[11],
						n[12], n[13])));
			}
			else if (line.startsWith("Square")){
				l.skip("Square");
				shapes.add(new Square(new Vector(l.nextDouble(), l.nextDouble(), l.nextDouble()), new Vector(l.nextDouble(), l.nextDouble(), l.nextDouble()),
						new Vector(l.nextDouble(), l.nextDouble(), l.nextDouble()), new Material(new Vector(l.nextDouble(), l.nextDouble(), l.nextDouble()),
						l.nextDouble(), l.nextDouble(), l.nextDouble(), l.nextDouble()), new Vector(l.nextDouble(), l.nextDouble(), l.nextDouble())));
			}
			else if (line.startsWith("Triangle")){
				l.skip("Triangle");
				shapes.add(new Triangle(new Vector(l.nextDouble(), l.nextDouble(), l.nextDouble()), new Vector(l.nextDouble(), l.nextDouble(), l.nextDouble()),
						new Vector(l.nextDouble(), l.nextDouble(), l.nextDouble()), new Material(new Vector(l.nextDouble(), l.nextDouble(), l.nextDouble()),
						l.nextDouble(), l.nextDouble(), l.nextDouble(), l.nextDouble()), new Vector(l.nextDouble(), l.nextDouble(), l.nextDouble())));
			}
			else if (line.startsWith("Cube")){
				l.skip("Cube");
				shapes.add(new Cube(new Vector(l.nextDouble(), l.nextDouble(), l.nextDouble()), new Vector(l.nextDouble(), l.nextDouble(), l.nextDouble()), new Vector(l.nextDouble(), l.nextDouble(), l.nextDouble()),
						new Vector(l.nextDouble(), l.nextDouble(), l.nextDouble()), new Material(new Vector(l.nextDouble(), l.nextDouble(), l.nextDouble()),
						l.nextDouble(), l.nextDouble(), l.nextDouble(), l.nextDouble()), new Vector(l.nextDouble(), l.nextDouble(), l.nextDouble())));
			}
			else{
				l.close();
				throw (new IOException("Exception: Failed to parse line " + i + ". "));
			}
			l.close();
			
		}
		if (i>0){
			hasParsed = true;
		}
		else{
			System.out.println("File empty? Nothing parsed. ");
		}
	}
	
	public Set<Shape> getShapes() throws IOException{
		if (hasParsed){
			return shapes;
		}
		else{
			throw (new IOException("Exception: no File parsed! "));
		}
	}

}
