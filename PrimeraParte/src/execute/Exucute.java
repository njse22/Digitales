package execute;

import model.CodificacionCanal;
import model.ImageProcessor;

import model.lectorEscritor;

public class Exucute {
	
	public static void main(String[] args) {
		
		
		/** PRUEBA DE CODIFICACIÓN DE IMAGEN  **/
		ImageProcessor processor2 = new ImageProcessor(); 
		processor2.processImage("linux-infected-180x180.jpg");
		System.out.println(processor2.getCodecImage());
		
		/** PRUEBA DE CODIFICACIÓN DE CANAL **/
		CodificacionCanal cc = new CodificacionCanal();
		cc.inicializarMatriz();
		cc.sistematizar();
		cc.codificar();
		cc.resumen();
		
		/** DECODIFICACIÓN DE CANAL  **/
		CodificacionCanal dc = new CodificacionCanal();
		dc.inicializarMatriz();
		dc.sistematizar();
		dc.setMatrizParidad(cc.getMatriz());
		
		
		
	}
		
}
