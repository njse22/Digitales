package model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class CodificacionLZW {
	/**
	 * Arreglo de objetos tipo NodoLZW que cumple lafuncion de representar
	 * el diccionario de de simbolos del mensaje codificado.
	 * */
	private ArrayList<NodoLZW> diccionario;
	
	/**
	 * Mensaje que se desea codificar.
	 * */
	private String mensaje;
	
	/**
	 * Lleva la cuenta del los indices del diccionario durante la inicializacion 
	 * del mismo y su uso en la codificacion.
	 * */
	private int contadorDiccionario;
	
	
	/**
	 * Es el mensaje que queda como resultado de la codificacion LZW
	 * */
	private String cadenaCodificada;
	
	
	/**
	 * Representa el numero de bits por simbolo, que se requeriran despues
	 * de hacer la codificacion.
	 * */
	private int numeroBits;
	
	/**
	 * Lleva la cuenta de la cantidad de simbolos representados en el diccionario
	 * pero solo en su inicializacion, NO despues de la codificacion.
	 * */
	private int caracteresDiccionario;
	
	/**
	 * cadena que contine la informacion binaria del mensaje ya codificcado.
	 * */
	private String cadenaBinaria;
	
	/**
	 * constructor de la clase encargada de la clase encargada de
	 * la codificacion LZW
	 * @param mensaje: corresponde al mensaje que se desea codificar.
	 * */
	public CodificacionLZW(String mensaje) {
		this.mensaje = mensaje;
		contadorDiccionario = 0;
		caracteresDiccionario =0;
		cadenaBinaria = "";
		diccionario = new ArrayList<NodoLZW>();
		setNumeroBits(0);
	}
	
	public ArrayList<NodoLZW> getDiccionario() {
		return diccionario;
	}

	public void setDiccionario(ArrayList<NodoLZW> diccionario) {
		this.diccionario = diccionario;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public int getContadorDiccionario() {
		return contadorDiccionario;
	}
	
	public String getCadenaCodificada() {
		return cadenaCodificada;
	}

	public void setCadenaCodificada(String cadenaCodificada) {
		this.cadenaCodificada = cadenaCodificada;
	}

	public int getNumeroBits() {
		return numeroBits;
	}

	public void setNumeroBits(int numeroBits) {
		this.numeroBits = numeroBits;
	}	
	
	public void setContadorDiccionario(int contadorDiccionario) {
		this.contadorDiccionario = contadorDiccionario;
	}

	public int getCaracteresDiccionario() {
		return caracteresDiccionario;
	}

	public void setCaracteresDiccionario(int caracteresDiccionario) {
		this.caracteresDiccionario = caracteresDiccionario;
	}
	
	

	
	public String getCadenaBinaria() {
		return cadenaBinaria;
	}

	public void setCadenaBinaria(String cadenaBinaria) {
		this.cadenaBinaria = cadenaBinaria;
	}

	/**
	 * metodo encargado de la inicializacion del diccionario del mensaje
	 * recorriendo todo el mensaje caracter por caracter y revisando si 
	 * este existe dentro del diccionario, si no es encontrado lo agrega, 
	 * de lo contrario continua
	 * */
	private void inicializarDiccionario() {
		char[] splitMensaje = mensaje.toCharArray();// convierte el mensaje a codificar en un arreglo que contiene cada uno de los simbolos por separado.
		
		for(int i = 0; i < splitMensaje.length; i++) {// avanza a traves del arreglo de simbolos
			if(buscarEnLista(""+splitMensaje[i]) == null) {//verifica si el simbolo esta o no esta en el diccionario.
				diccionario.add(new NodoLZW(""+splitMensaje[i], contadorDiccionario));// al no encontrar el simbolo lo agrega al diccionario.
				contadorDiccionario++;
				caracteresDiccionario++;
			}
		}
	}
	
	
	/**
	 * metodo de ejecutar el algoritmo de comprecion LZW, sobre el mensaje 
	 * que se dea comprimir 
	 * */
	public void compresion() {
		inicializarDiccionario();// este metodo se encarga de la inicializacion del diccionario.
		String w = "";
		String k = "";
		String wk = "";
		String cadenaSalida = "";
		for(int i = 0; i< mensaje.toCharArray().length;i++) {
			 k =""+ mensaje.charAt(i);
			 wk = w+k;
			 if(buscarEnLista(wk) != null) {
				 w = wk;
			 }else {
				 cadenaSalida = cadenaSalida +" "+ buscarEnLista(w).getIndice();
				 diccionario.add(new NodoLZW(wk, contadorDiccionario));// se van adicionando los nuevos simbolos a la cadena del mensaje codificado.
				 contadorDiccionario++;
				 w = k;
				 
			 }
		}
		cadenaSalida = cadenaSalida + " " +buscarEnLista(w).getIndice();
		numeroBits = (int) Math.ceil((Math.log(diccionario.get(diccionario.size()-1).getIndice()) / Math.log(2)));
		cadenaCodificada = cadenaSalida;// se agrega el mensaje a transmitir completamente codificado a su variable de clase.
	}
	
	/**
	 * metodo encargado de buscar sumbolos en el diccionario de codificacion.
	 * si el simbolo que se desea buscar es encontrado retorna un objeto tipo
	 * NodoLZW que contiene tanto el simbolo buscado como sy indice, ei no es
	 * encontrado retorna null.
	 * @param wk: simbolo que se desea buscar.
	 * @return retorna el objeto Nodo LZW o null.
	 * */
	private NodoLZW buscarEnLista(String wk) {
		
		for (int i = 0; i < diccionario.size();i++) {
			if (diccionario.get(i).getSimbolo().compareTo(wk) == 0)
				return diccionario.get(i);
		}
		
		return null;
	}
	
	/**
	 * metodo encargado de la traduccion a binario de la nuva cadena de 
	 * ya codificada con el algoritmoLZW.
	 * @return salidabinaria: representa la cadena ya traducida a binario.
	 * */
	public String salidaBinario() {
		String salidabinaria = "";
		
		String[] cod = cadenaCodificada.split(" ");//se transforma el mensaje codificado en un arreglo con cada uno de sus simbolos para facilitar su traduccion.
		
		
		for(int i = 1 ; i < cod.length;i++ ) {
			int valorBinario = valorBinario(Integer.parseInt(cod[i]));// toma el valor iecimo del arreglo y lo transforma en una cadena de bits empleando el metodo valorBinario.
			salidabinaria = salidabinaria + agregarCeros(valorBinario+"", numeroBits);//agrega los ceros que hacen falta al "valorBinario" y luego lo agrega a la cadena total que se desea retornar.
		}
		
		return salidabinaria;
	}
	
	
	/**
	 * en este metodo convertimos cualquier int en una cadena de bits.
	 * @param byte_n: numero que se desea convertir a binario. tipo -int.
	 * @return binario: numero traducido a binario.
	 * */
	private int valorBinario(int byte_n) {
		int exp = 0;
		int binario = 0;
		int digito = 0;
		while(byte_n != 0) {
			 digito = byte_n % 2;
			 binario = (int) ( binario + digito*Math.pow(10, exp));
			 exp++;
			 byte_n = byte_n/2;//los datos tipo int siempre toman solo la parte entera 
		}
		return binario;
	}
	
	/**
	 * metodo encargado de agregar los ceros a la parte izquierda un numero en binario
	 * esto con la finalidad de completar la cantidad de bits que debe tener segun sus entradas.
	 * si ingresa una cadena de bits y se requiere que tenga n numero de bits, si el numero de bits requeridos
	 * es mayor a la cantidad de bits que posee la cadena de entrada, esta se comprementa con bits a su izquierda.
	 * @param cadena: cadena de bits que sera revisada.
	 * @param numeroBits: el numero de bits que debe tener la cadena.
	 * @return cadenaCorregida: la cadena ya corregida con el numero de bits que debe tener (si es que fue necesaria la correccion). 
	 * */
	private String agregarCeros(String cadena, int numeroBits) {
		String cadenaCorregida = cadena;
		
		while(cadenaCorregida.toCharArray().length < numeroBits) {
			cadenaCorregida = "0"+cadenaCorregida;
		}
		
		return cadenaCorregida;
	}
	
	/**
	 * este metodo se encarga de convertir el diccionario en un arreglo de bits.
	 * el diccionario sera horaganizado de la siguiente manera:
	 * simbolo = indice,..., simbolo = indice.
	 * en su representacion binaria.
	 * */
	public String diccionarioBinario() {
		String salida = "";
		String cadenaCruda = "";
		String[] indices = cadenaCodificada.split(" ");
		for(int i = 0; i < caracteresDiccionario; i++) {
				// el diccionario se organiza de la sigiente forma:
				// simbolo/indice pero utilizando sis equivalentes en binario.
				salida =salida + agregarCeros(valorBinario(diccionario.get(i).getSimbolo().getBytes()[0])+"", 8) + agregarCeros(valorBinario(diccionario.get(i).getIndice())+"", numeroBits);
		}
		return salida;
	}
	
	/**
	 * en este metodo se toman, la cadena del mensaje traducida a binario, el diccionario traducido a binario,
	 * el numero de lineas que se ran requeridas del diccionario y el numero de bits que requiere cada simbilo ya codificado.
	 * porsteriormente se concatena todo lo anterior en una sola cadena de bits.
	 * los bits se organizaran de la siguiente manera:
	 * numero de simbolos del diccionario//numero de bits por simbolo//diccionario//mensaje. 
	 * */
	public void getCadenaBinariaCompleta() {
		String salida= "";
		
		salida = agregarCeros(valorBinario(caracteresDiccionario)+"", 8) + agregarCeros(valorBinario(numeroBits)+"", 8) + diccionarioBinario() + salidaBinario();
		
		cadenaBinaria = salida;
	}

}
