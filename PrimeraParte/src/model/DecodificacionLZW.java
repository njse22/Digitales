package model;

import java.util.ArrayList;

public class DecodificacionLZW {

	/**
	 * Arreglo de objetos tipo NodoLZW que cumple lafuncion de representar
	 * el diccionario de de simbolos del mensaje codificado.
	 * */
	private ArrayList<NodoLZW> diccionario;

	/**
	 * cadena de bits que se empleara para la inicializacion de los datos para la decodificacion.
	 * */
	private String cadenaBinaria;
	
	/**
	 * Mensaje que se desea decodificar.
	 * */
	private String mensaje;
	
	/**
	 * Lleva la cuenta del los indices del diccionario durante la inicializacion 
	 * del mismo y su uso en la decodificacion.
	 * */
	private int contadorDiccionario;
	
	/**
	 * es el mensaje que se resive ya codificaddo.
	 * */
	private String cadenaCodificada;
	
	/**
	 * representa la cadena despues del proceso de decodificacion.
	 * */
	private String cadenaDecodificada;
	
	/**
	 * Representa el numero de bits por simbolo, que se requeriran despues
	 * de hacer la codificacion.
	 * */
	private int numeroBits;
	
	/**
	 * numero de simbolos de deccionario resivido.
	 * */
	private int caracteresDiccionario;
	
	
	public DecodificacionLZW() {
		diccionario = new ArrayList<NodoLZW>();
		cadenaCodificada = "";
		cadenaDecodificada = "";
		cadenaBinaria = "";
		mensaje = "";
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

	public void setContadorDiccionario(int contadorDiccionario) {
		this.contadorDiccionario = contadorDiccionario;
	}

	public String getCadenaCodificada() {
		return cadenaCodificada;
	}

	public void setCadenaCodificada(String cadenaCodificada) {
		this.cadenaCodificada = cadenaCodificada;
	}

	
	public String getCadenaBinaria() {
		return cadenaBinaria;
	}

	public void setCadenaBinaria(String cadenaBinaria) {
		this.cadenaBinaria = cadenaBinaria;
	}

	public String getCadenaDecodificada() {
		return cadenaDecodificada;
	}

	public void setCadenaDecodificada(String cadenaDecodificada) {
		this.cadenaDecodificada = cadenaDecodificada;
	}
	
	public int getNumeroBits() {
		return numeroBits;
	}

	public void setNumeroBits(int numeroBits) {
		this.numeroBits = numeroBits;
	}

	public int getCaracteresDiccionario() {
		return caracteresDiccionario;
	}

	public void setCaracteresDiccionario(int caracteresDiccionario) {
		this.caracteresDiccionario = caracteresDiccionario;
	}
	
	
	/**
	 * este meto es el encargado de convertir una cadena de bits cruda en informacion que atude a 
	 * decodificar el mensaje que contiene.
	 * leera y asignara sus partes de la siguiente manera:
	 * - primera seccion para el atributo caracteres diccionario.
	 * - segunda seccion para el atributo numeroBits.
	 * - tercera seccion para el diccionario de simbolos.
	 * - 
	 * */
	public void inicializar () {
		String bits = cadenaBinaria;
		int contadorPosicion = 0; // contador que permite el desplasamiento por cada bit de la cadena de bits.                                     
		String cadenacruda = ""; // String que representara una cadena de bits en especifico que sera tradicida.
		                                                              //---------------------------------------------------------+  
		while(contadorPosicion <= 7) {                                //en esta seccion se determina el tamaño que tendra el     + 
			cadenacruda = cadenacruda + bits.charAt(contadorPosicion);//diccionario inicial de datos                             +
			contadorPosicion++;                                       //para asi facilitar su lectura en la cadena de bits cruda.+
		}	                                                          //                                                         + 
		caracteresDiccionario = getNumero(cadenacruda);// se agrega el numero de simbolos.     


		cadenacruda = "";// se vacia la cadena para su posterior uso 
		                                                              //---------------------------------------------------------+
		while(contadorPosicion <= 15) {                               //en esta seccion se toma la caena de bits que ------------+
			cadenacruda = cadenacruda + bits.charAt(contadorPosicion);//contiene el numero de bits por simbolo que---------------+
			contadorPosicion++;                                       //dentro de la ya realizada codificacion-------------------+  
		}	                                                          //---------------------------------------------------------+
		                                                              //---------------------------------------------------------+
		numeroBits = getNumero(cadenacruda);// se agrega el numero de bits por simbolo.
		cadenacruda = "";
		
		
		int contadordic = caracteresDiccionario;// lleva la cuenta regresiva de la la cantidad de simbolos del diccionario que ha leido y agregado.
		while(contadordic > 0) {//dentro de este ciclo se traduciran todos los datos concernientes al diccionario.             
			int contador1 = contadorPosicion+8;// contador para sustraer el numero de bits conserniente el simbolo
			while(contadorPosicion < contador1) {// dentro de este ciclo se recorre la cadena de bists prinsipal y se extraen los requeridos para el simbolo
				cadenacruda = cadenacruda + bits.charAt(contadorPosicion);
				contadorPosicion++;
			}
			byte[] letra = new byte[1];
			letra[0]= (byte) getNumero(cadenacruda);
			String cadenaLetra = new String(letra);// se guarda el simbolo en una varialble local 
			cadenacruda = "";
			cadenacruda = "";
			contador1 = contadorPosicion+numeroBits;// se inicializa de nuevo el contador con la diferencia de que esta vez se tomara un numero de bits diferente (el correspondiente a la cantidad susada en el mensaje)
			
			while(contadorPosicion < contador1) {//dentro de este ciclo se procede a la extraccion de la seccion de vits que corresponde al indice del simbolo
				cadenacruda = cadenacruda + bits.charAt(contadorPosicion);
				contadorPosicion++;
			}
			int cadenaIndice = getNumero(cadenacruda);// se guarda el indice en una variable local
			cadenacruda = "";
			
			diccionario.add(new NodoLZW(cadenaLetra, cadenaIndice));// se agrega el simbolo y su indice al diccionario
			
			contadordic--;
		}
		
		contadorPosicion++;			                                        //en esta seccion se procese a traducir la seccion---------+
		while(contadorPosicion < bits.length()) {                           //---------------------------------------------------------+
			int contador2 = contadorPosicion+numeroBits;                    //correspondiente al mensaje que se desea decodificar------+
			while(contadorPosicion < contador2) {                           //para despuer irlo concatenando al atributo --------------+
				cadenacruda = cadenacruda + bits.charAt(contadorPosicion-1);//cadenaCodificada-----------------------------------------+
				contadorPosicion++;                                         //---------------------------------------------------------+
			}                                                               //---------------------------------------------------------+
			cadenaCodificada =cadenaCodificada +" "+getNumero(cadenacruda);
			cadenacruda= "";
		}
		
	}
	
	/**
	 * metodo encargado de convertit una cadena de bits en un numero entero
	 * @return salida: el numero ya traducido
	 * */
	private int getNumero(String bits) {
		int numeroBits = bits.length();
		int salida = 0;
		for (int i = 0, j = numeroBits-1; i < numeroBits;i++,j--) { // se recorre toda la cadena de bits y se suman las potencias de base 2 para obtener el numero
			if(bits.charAt(j) == '1') {
				salida = salida + (int)Math.pow(2, i);
			}
		}
		return salida;
	}
	
	/**
	 *metodo encargado de la descompresion del mensaje resivido 
	 * */
	public void descomprecion() {
		String salida= "";
		String caracter ="";
		int codigoViejo;
		int codigoNuevo;
		String cadena= "";
		String[] codigo = cadenaCodificada.split(" ");
		codigoViejo = (int)Integer.parseInt(codigo[1]);
		caracter = buscarEnDiccionario(codigoViejo);
		salida = salida + caracter;
		for( int i = 2; i <codigo.length;i++ ) {
			codigoNuevo = (int)Integer.parseInt(codigo[i]);
			if(buscarEnDiccionario(codigoNuevo) == null) {
				cadena = buscarEnDiccionario(codigoViejo);
				cadena = cadena+caracter;
			}else {
				cadena = buscarEnDiccionario(codigoNuevo);
			}
			
			salida =salida+ cadena;
			caracter = cadena.charAt(0)+"";
			diccionario.add(new NodoLZW(buscarEnDiccionario(codigoViejo)+caracter, caracteresDiccionario+i-2));
			codigoViejo = codigoNuevo;
		}
		
		mensaje = salida;
	}
	
	/**
	 * metodo encargado de buscar un simbolo por su indice si lo encuentra devuelve el simbolo de lo contraro devuelve null
	 * @return devuelve el estring correspondiente al simbolo o null.
	 * */
	private String buscarEnDiccionario(int indice) {
		for( int i = 0; i < diccionario.size();i++) {
			if (diccionario.get(i).getIndice() == indice) {
				return diccionario.get(i).getSimbolo();
			}
		}
		
		return null;
	}

	
}
