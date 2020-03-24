package execute;

import model.ImageProcessor;

import model.SingletonImageProcessor;
import model.lectorEscritor;

public class Exucute {
	
	public static void main(String[] args) {
//		SingletonImageProcessor processor = SingletonImageProcessor.getInstance();
//		processor.processImage("linux-infected-180x180.jpg");
//		System.exit(0);
		
//		ImageProcessor processor2 = new ImageProcessor(); 
//		processor2.processImage("linux-infected-180x180.jpg");
//		System.out.println(processor2.getCodecImage());
		
		lectorEscritor l = new lectorEscritor();
//		System.out.println(l.leerTexto("C:\\Users\\57318\\Documents\\ICESI\\prubas comunicaciones digitales\\cossitas.ascii"));
		System.out.println(l.leerBytes("/home/slayer-nation/ICESI/Septimo_Semestre/Comunicaciones_Digitales/cossitas.ascii"));
		System.out.println(l.leerBytes("C:\\Users\\57318\\Documents\\ICESI\\prubas comunicaciones digitales\\cossitas.ascii"));
	}
		
}
