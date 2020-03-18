package execute;

import model.ImageProcessor;

public class Exucute {
	
	public static void main(String[] args) {
		ImageProcessor processor = ImageProcessor.getInstance();
		processor.processImage("linux-infected-180x180.jpg");
		System.exit(0);
	}
		
}
