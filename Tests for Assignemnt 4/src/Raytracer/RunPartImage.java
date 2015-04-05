/**
 * 
 */
package Raytracer;

import java.awt.image.BufferedImage;

/**
 * @author Max
 *
 */
public class RunPartImage implements Runnable {

	private BufferedImage image;
	private int samples;
	private Scene scene;
	private int start, end;
	public RunPartImage(BufferedImage image, int samples, Scene scene, int start, int end) {
		this.image=image;
		this.samples=samples;
		this.scene=scene;
		this.start=start;
		this.end=end;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		scene.raytrace(image, samples, start, end);
	}

}
