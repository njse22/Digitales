package execute;

import model.ImageProcessor;

import model.lectorEscritor;

public class Exucute {
	
	public static void main(String[] args) {
		
		ImageProcessor processor2 = new ImageProcessor(); 
		processor2.processImage("linux-infected-180x180.jpg");
		System.out.println(processor2.getCodecImage());
		
	}
		
}
