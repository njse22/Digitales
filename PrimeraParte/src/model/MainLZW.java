package model;

import java.util.ArrayList;

public class MainLZW {
	
	/**
	 * constante que almacena la direccion de un archivo tipo txt que puede ser modificado manualmente.
	 * */
	public static final String ARCHIVO_MODIFICABLE ="./data/resultados primera entrega/textoModificable.txt";
	
	/**
	 * constante que almacena la direccion de un archivo tipo ascii del que se leera la informacion para
	 * su uso en la codificacion LZW.
	 * */
	public static final String ARCHIVO_ASCII ="./data/resultados primera entrega/textoMensaje.ascii";
	
	/**
	 * constante correspondiente a la dirreccion del archivo que contiene la informacion binaria de la codificacion de fuente.
	 * */
	public static final String ARCHIVO_CODIFICACION ="./data/resultados primera entrega/codificacion.txt";
	
	/**
	 * constate que almacena la direccion de el archivo que contine los resultados de la codificacion LZW.
	 * */
	public static final String RESULTADOS_CODIFICACION = "./data/resultados primera entrega/resumenCodificacion.txt";

	/**
	 * constate que almacena la direccion de el archivo que contine los resultados de la decodificacion LZW.
	 * */
	public static final String RESULTADOS_DECODIFICACION = "./data/resultados primera entrega/resumenDeCodificacion.txt";

	/**
	 * objeto encargado de proporcionar los metodos para la lectura y escritura de archivos planos.
	 * */
	private lectorEscritor lecEsc;
	
	/**
	 * objeto que se encargara de la codificacion de un mesaje en especifico.
	 * */
	private CodificacionLZW codificador;
	
	/**
	 * objeto que se encargara de la decodificacion de un mensaje en especifico.
	 * */
	private DecodificacionLZW decodificador;
	
	public MainLZW() {
		lecEsc = new lectorEscritor();
		codificador = new CodificacionLZW("");
		decodificador = new DecodificacionLZW();
	}
	
	/**
	 * metodo encargado de la inicializacion del texto del que se lee el mensaje a codificar
	 * tomando la informacion de un archivo .txt que puede ser modificado manualmente y copiandola en un 
	 * nuevo archivo en formato .ascii
	 * */
	public void inicializarTexto() {
		String cadena = lecEsc.leerTexto(ARCHIVO_MODIFICABLE);
		lecEsc.escribirTexto(cadena, ARCHIVO_ASCII);			
	}
	
	/**
	 * en este metodo se codifica y se guarda en un archivo de texto plano toda la informacion de la
	 * codificacion LZW
	 * */
	public void codificar() {
		String cadena = lecEsc.leerTexto(ARCHIVO_ASCII);//lee la informacion del texto que contiene el mensaje que se desea embiar y la guarda en una variable
		String cadena1 =lecEsc.leerBytes(ARCHIVO_ASCII);//lee la informacion binaria del texto que contiene el mensaje que se desea embiar y la guarda en una variable
		codificador.setMensaje(cadena);//le pasa el mensaje al codificador
		codificador.compresion();//da la orden de codificar
		codificador.getCadenaBinariaCompleta();// da la orden de obtener de traducir a binario toda la informacion de la codificacion de fuente del mensaje.
		System.out.println("Se codifico el mensaje!");
		String cadena2 =codificador.getCadenaBinaria(); 
		lecEsc.escribirTexto(cadena2, ARCHIVO_CODIFICACION);//guarda en un archivo de texto plano toda la codificacion de fuente del mensaje traducida a binario.
		String resumen ="+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ INFORMACION DEL MENSAJE +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n" 
				+"El mensaje a codificar es el siguiente:"+"\n"+cadena+"\n"
				+">> El mensaje traducido a binario siguiendo el formato ascii el el siguiente:\n"+cadena1+"\n \n"
				+">> Ocupando asi "+cadena1.length()+" bits \n \n"
				+"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ INFORMACION DE CODIFICACION +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ \n"
				+">> Mensaje despues de la compresion: \n"+ codificador.getCadenaCodificada()+"\n \n"
				+">> El numero de bits requeridos por simbolo es: "+codificador.getNumeroBits()+"\n \n"
				+">> Obtenemos a continuacion una cadena de bits que representa el mensaje ya codificado:\n"+cadena2+"\n \n"
				+">> La longitud de esta cadena es de "+cadena2.length()+" bits \n \n"
				+">> Ahora bien la relacion de comprecion con con respecto al formato ascii es la siguiente:\n"+ cadena1.length()+" bits / "+cadena2.length()+" bits";
		
//		for(int i = 0; i< codificador.getCaracteresDiccionario();i++) {
//			resumen = resumen + codificador.getDiccionario().get(i).getSimbolo()+" = "+codificador.getDiccionario().get(i).getIndice()+"\n";
//		}
		
		lecEsc.escribirTexto(resumen, RESULTADOS_CODIFICACION);
	}
	
	/**
	 * el siguiente metodo se encarga de hacer la decodificacion a partir de un archivo de texto plano
	 * que contine los bits a decodificar y guardar sus resultados en un archivo de texto.
	 * */
	public void decodificar() {
		String cadena = lecEsc.leerTexto(ARCHIVO_CODIFICACION);
		decodificador.setCadenaBinaria(cadena);
		decodificador.inicializar();
		decodificador.descomprecion();
		String resumen ="+++++++++++++++++++++++++++++++++++++++++++++++++ INFORMACION DE DECODIFICACION +++++++++++++++++++++++++++++++++++++++++++++++++\n \n" 
				+">> La informacion binaria del mensaje que se desea decodificar es la siguiente:\n"+cadena+"\n \n"
				+">> El mensaje a decodificar es el siguiente:\n"+decodificador.getCadenaCodificada()+"\n \n"
				+">> El mensaje decodificado es el siguiente: \n"+decodificador.getMensaje();
		lecEsc.escribirTexto(resumen, RESULTADOS_DECODIFICACION);
		System.out.println("Se decodifico el mensaje!");
	}
	
	public void mainLZW() {
		inicializarTexto();
		codificar();
		decodificar();
	}
}
