package execute;

import model.ImageProcessor;
import model.SingletonImageProcessor;

public class Exucute {
	
	public static void main(String[] args) {
//		SingletonImageProcessor processor = SingletonImageProcessor.getInstance();
//		processor.processImage("linux-infected-180x180.jpg");
//		System.exit(0);
		
		ImageProcessor processor2 = new ImageProcessor(); 
		processor2.processImage("linux-infected-180x180.jpg");
		System.out.println(processor2.getCodecImage());
		
	}
		
}
