package execute;

import model.CodificacionCanal;
import model.ImageProcessor;
import model.MainLZW;
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
		
		
		

		/** inicializa y ejecuta la codificacion y decodificacion de fuente para texto plano **/
		MainLZW lzw = new MainLZW();
		lzw.mainLZW();
		 
		/** inicializa y ejecuta la codificacion de canal **/
		CodificacionCanal codCanal = new CodificacionCanal();
		codCanal.mainCodificacionCanal();
	}
		
}
