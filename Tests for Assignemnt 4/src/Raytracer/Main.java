package Raytracer;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

	public static void main(String[] args) {

		final int numberOfTests = 1;
		final int width = 1024; // in pixel
		final int height = 1024;
		final int threads = 64;

		final int samples = 1024;
		
		// test loop:
		for (int runs=1; runs <=numberOfTests; runs++){
			long nanos = System.nanoTime();
			Camera camera = new Camera(new Ray(new Vector(100, 100, 100),
					new Vector(-1, -1, -1)), new Vector(0, 1, 0));
			final Scene scene = new Scene(camera, width, height);
	
			//ground
			scene.addShape(new Plane(new Vector(0,0,0), new Vector(1,0,0), new Vector(0,0,1), new Material(new Vector(0.8,0.8,0.8), 1.4,1,2,1), new Vector(0,0,0)));
			//horizon
			//scene.addShape(new Sphere(1e5, new Vector(0,0,0), new Material(new Vector(1, 1, 1), 1,1,2,1), new Vector(0.1,0.1,0.1)));
			
			//Sun
			scene.addShape(new Sphere(5, new Vector(0, 10, 0),
					new Vector(0.0, 0.0, 0.0), new Vector(20, 20, 20)));
			//Sun2
			scene.addShape(new Sphere(10, new Vector(100, 100, -200),
					new Vector(0.0, 0.0, 0.0), new Vector(12, 12, 12)));
			//little cubes
			for (int i = 3; i<13; i++){
				scene.addShape(new Cube(new Vector(Math.cos(0.5*i)*8*i, 0.1, Math.sin(0.5*i)*8*i), new Vector(0, 4, 0), new Vector(2, 0, 0), new Vector(0, 0, -2), new Material(new Vector(0.7,0.8,0.9), 1.6, 1, 0, 0.1), new Vector(0,0,0)));
			}
			/*final Camera camera = new Camera(new Ray(new Vector(50, 52, 295.6),
					new Vector(0, -0.042612, -1)), new Vector(0, 1, 0));
	
			final Scene scene = new Scene(camera, width, height);
			
			// triangle
			scene.addShape(new Cube(new Vector(20, 10, 200), new Vector(0, 2, 0), new Vector(10, 0, 0), new Vector(0,0,-20), new Material(new Vector(1,1,1), 1.6,1,0,0.01), new Vector(0,0,0)));
	
			// Left
			scene.addShape(new Plane(new Vector(0,0,0), new Vector(0,1,0), new Vector(0,0,1), new Material(new Vector(1,0.5,0.5), 1.4,1,2,1), new Vector(0,0,0)));

			// Right
			scene.addShape(new Plane(new Vector(100,0,0), new Vector(0,1,0), new Vector(0,0,1), new Material(new Vector(0.5,0.5,1), 1.4,1,2,1), new Vector(0,0,0)));

			// Back
			scene.addShape(new Plane(new Vector(0,0,000), new Vector(1,0,0), new Vector(0,1,0), new Material(new Vector(0.8,0.8,0.8), 1.4,1,2,1), new Vector(0,0,0)));

			//scene.addShape(new Sphere(1e5, new Vector(50, 40.8, 1e5), new Material(new Vector(1, 1, 1), 1,1,2,0.3), new Vector(
			//				0.0, 0.0, 0.0)));
			// Front
			scene.addShape(new Plane(new Vector(0,0,-600), new Vector(1,0,0), new Vector(0,1,0), new Material(new Vector(0.8,0.8,0.8), 1.4,1,2,1), new Vector(0,0,0)));

			//scene.addShape(new Sphere(1e5, new Vector(50, 40.8, -1e5 + 600),
			//		new Vector(1.0, 1.0, 1.0), new Vector(0, 0, 0)));
			// Bottom
			scene.addShape(new Plane(new Vector(0,0,0), new Vector(1,0,0), new Vector(0,0,1), new Material(new Vector(0.8,0.8,0.8), 1.4,1,2,1), new Vector(0,0,0)));
			// Top
			scene.addShape(new Plane(new Vector(0,80,0), new Vector(1,0,0), new Vector(0,0,1), new Material(new Vector(0.8,0.8,0.8), 1.4,1,2,1), new Vector(0,0,0)));

		//	scene.addShape(new Sphere(1e5, new Vector(50, -1e5 + 81.6, 81.6),
		//			new Vector(0.75, 0.75, 0.75), new Vector(0.0, 0.0, 0.0)));
			// Sphere 1
			scene.addShape(new Sphere(16.5, new Vector(27.0, 16.5, 47.0),
					new Material(new Vector(1,1,1), 1,1,2,1.3), new Vector(0.0, 0.0, 0.0)));
			// Sphere 2 (glass)
			scene.addShape(new Sphere(16, new Vector(71, 17, 88.0),
					new Material(new Vector(1, 1, 1), 2.1,1,0,1), new Vector(0.0, 0.0, 0.0)));
			// Sphere 3
			scene.addShape(new Sphere(7, new Vector(55, 7, 45),
					new Material(new Vector(1, 1, 0.5), 1, 1,2,1), new Vector(0.0, 0.0, 0.0)));
			// Sphere 4
			scene.addShape(new Sphere(15, new Vector(100-17, 81.6-25, 100),
					new Material(new Vector(0.999, 0.999, 0.999), 1.6,1,0,0.01), new Vector(0.0, 0.0, 0.0)));
			// Light-source
			scene.addShape(new Sphere(6000, new Vector(50, 6081.6, 81.6),
					new Vector(0.0, 0.0, 0.0), new Vector(12, 13, 12)));
			//Light-source2
			scene.addShape(new Sphere(1200, new Vector(50, 80+1200-0.27, 50),
					new Vector(0.0, 0.0, 0.0), new Vector(11, 7, 7)));
			*/
			final BufferedImage image = new BufferedImage(width, height,
					ColorSpace.TYPE_RGB);
			
	
//			//scene.raytrace(image, samples);
//			
//			//Botched in Threads:
//			Runnable[] runnables = new Runnable[threads];
//			Thread[] threadsu = new Thread[threads];
//			for (int num=0; num<threads;num++)
//			{
//				runnables[num]=new RunPartImage(image, samples, scene, num*(width/threads), (num+1)*(width/threads));
//				threadsu[num]=new Thread(runnables[num]);
//				threadsu[num].start();
//			}
//			boolean isAnyThreadAlive=true; //totally botched in wait for all threads to finish.
//			while (isAnyThreadAlive) {isAnyThreadAlive = false; for (int y = 0; y<threadsu.length; y++){ if (threadsu[y].isAlive()){isAnyThreadAlive = true;}} // le epic wait() botched in
//			
//			}
//			//this is so botched
//			//botched adj. clumsily made or repaired in an unacceptable or incompetent manner. lol
			//make threads, have them calculate, join them:
			Thread[] threadsu = new Thread[threads];
			for (int num = 0; num < threads; num++){
				threadsu[num]=new Thread(new RunPartImage(image, samples, scene, num*(width/threads), (num+1)*(width/threads)));
				threadsu[num].start();
			}
			for (int num = 0; num < threads; num++){
				try {
					threadsu[num].join();
				} catch (InterruptedException e) {
					// Auto-generated catch block
					e.printStackTrace();
				}
			}
			//write image to disk:
			try {
				ImageIO.write(image, "PNG", new File("rayTest" + threads + "_" + runs + "_" + samples+".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("On run number " + runs + " of " + numberOfTests + " with " + threads + " threads and "+ samples +" samples." + "\nTime elapsed [ms]: "
					+ (System.nanoTime() - nanos) / 1_000_000);
		}
	}
}
