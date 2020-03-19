package codificacionDecodificacion;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class CodificacionLZW {
	
//	private Hashtable<String,Integer> diccionario;
	private ArrayList<NodoLZW> diccionario;
	private String mensaje;
	
	private int contadorDiccionario;
	
	private String cadenaCodificada;
	
	private int numeroBits;
	
	private int caracteresDiccionario;
	
	public CodificacionLZW(String mensaje) {
		this.mensaje = mensaje;
		contadorDiccionario = 0;
		caracteresDiccionario =0;
		diccionario = new ArrayList<NodoLZW>();
		setNumeroBits(0);
		compresion();
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

	private void inicializarDiccionario() {
		char[] splitMensaje = mensaje.toCharArray();
		
		for(int i = 0; i < splitMensaje.length; i++) {
//			System.out.println(splitMensaje[i]);
			if(buscarEnLista(splitMensaje[i]+"") == null) {
				diccionario.add(new NodoLZW(splitMensaje[i]+"", i));
				contadorDiccionario++;
				caracteresDiccionario++;
			}
		}
	}
	
	public void compresion() {
		inicializarDiccionario();
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
				 diccionario.add(new NodoLZW(wk, contadorDiccionario));
				 contadorDiccionario++;
				 w = k;
				 
			 }
		}
		cadenaSalida = cadenaSalida + " " +buscarEnLista(w).getIndice();
		
		numeroBits = (int) Math.ceil((Math.log(diccionario.get(diccionario.size()-1).getIndice()) / Math.log(2)));
		System.out.println("numero de bits requeridos: "+ numeroBits);
		cadenaCodificada = cadenaSalida;
	}
	
	
	private NodoLZW buscarEnLista(String wk) {
		
		for (int i = 0; i < diccionario.size();i++) {
			if (diccionario.get(i).getSimbolo().compareTo(wk) == 0)
				return diccionario.get(i);
		}
		
		return null;
	}
	
	public String salidaBinario() {
		String salidabinaria = "";
		
		String[] cod = cadenaCodificada.split(" ");
		
		for(int i = 1 ; i < cod.length;i++ ) {
			int valorBinario = valorBinario(Integer.parseInt(cod[i]));
//			salidabinaria = salidabinaria+" "+ agregarCeros(valorBinario+"", numeroBits);
			salidabinaria = salidabinaria + agregarCeros(valorBinario+"", numeroBits);
		}
		
		return salidabinaria;
	}
	
	private int valorBinario(int byte_n) {
		int exp = 0;
		int binario = 0;
		int digito = 0;
		while(byte_n != 0) {
			 digito = byte_n % 2;
			 binario = (int) ( binario + digito*Math.pow(10, exp));
			 exp++;
			 byte_n = byte_n/2;
			
		}
		
		return binario;
	}
	
	private String agregarCeros(String cadena, int numeroBits) {
		String cadenaCorregida = cadena;
		
		while(cadenaCorregida.toCharArray().length < numeroBits) {
			cadenaCorregida = "0"+cadenaCorregida;
		}
		
		return cadenaCorregida;
	}
	
	
	public String diccionarioBinario() {
		String salida = "";
		String cadenaCruda = "";
		String[] indices = cadenaCodificada.split(" ");
		String igual = agregarCeros(valorBinario("=".getBytes()[0])+"", 8);
		String coma = agregarCeros(valorBinario(",".getBytes()[0])+"", 8);
		for(int i = 0; i < caracteresDiccionario; i++) {
			if(i == (caracteresDiccionario-1)) {
				salida =salida + agregarCeros(valorBinario(diccionario.get(i).getSimbolo().getBytes()[0])+"", 8) + igual + agregarCeros(valorBinario(diccionario.get(i).getIndice())+"", numeroBits);
			}else {
				salida =salida + agregarCeros(valorBinario(diccionario.get(i).getSimbolo().getBytes()[0])+"", 8) +igual+ agregarCeros(valorBinario(diccionario.get(i).getIndice())+"", numeroBits)+coma;
			}
		}
		return salida;
	}
	

}
