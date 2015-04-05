/* Tester for SceneParser.
 * A text file should be supplied in the format [Shape] double doubl double ... double (amount of doubles needed for a working shape)
 * 
 */

package ParserTester;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import Raytracer.SceneParser;
import Raytracer.Shape;

public class Main {

	public static void main(String[] args) {
		SceneParser parser = new SceneParser(new File("scene.txt"));
		Set<Shape> shapes;
		try {
			shapes = parser.getShapes();
			System.out.println(shapes.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Exception: not parsed yet.");
		}
		System.out.println("test beendet.");
	}
}
