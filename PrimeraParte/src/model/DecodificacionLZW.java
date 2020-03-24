package model;

import java.util.ArrayList;

public class DecodificacionLZW {

	
	private ArrayList<NodoLZW> diccionario;

	private String mensaje;
	
	private int contadorDiccionario;
	
	private String cadenaCodificada;
	
	private int numeroBits;
	
	private int caracteresDiccionario;
	
	
	public DecodificacionLZW() {
		diccionario = new ArrayList<NodoLZW>();
		cadenaCodificada = "";
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
	
	public void inicializar (String bits) {
		int contadorPosicion = 0;
		String cadenacruda = "";
		while(contadorPosicion <= 7) {
			cadenacruda = cadenacruda + bits.charAt(contadorPosicion);
			contadorPosicion++;
		}	
		
		caracteresDiccionario = getNumero(cadenacruda);
		cadenacruda = "";
		
		while(contadorPosicion <= 15) {
			cadenacruda = cadenacruda + bits.charAt(contadorPosicion);
			contadorPosicion++;
		}	
		
		numeroBits = getNumero(cadenacruda);
		cadenacruda = "";
		
		
		int contadordic = caracteresDiccionario;
		while(contadordic > 0) {
			System.out.println("poscicionA: "+contadorPosicion);
			int contador1 = contadorPosicion+8;
			while(contadorPosicion < contador1) {
				cadenacruda = cadenacruda + bits.charAt(contadorPosicion);
				contadorPosicion++;
			}
			byte[] letra = new byte[1];
			letra[0]= (byte) getNumero(cadenacruda);
			System.out.println("poscicionB: "+contadorPosicion);
//			System.out.println("bits: "+cadenacruda);
//			System.out.println("numero: "+getNumero(cadenacruda));
//			System.out.println("byte: "+letra[0]);
			String cadenaLetra = new String(letra);
//			System.out.println("simbolo: "+cadenaLetra);
			
			cadenacruda = "";
			contador1 = contadorPosicion+8;
			System.out.println("poscicionA: "+contadorPosicion);

			while(contadorPosicion < contador1) {
				cadenacruda = cadenacruda + bits.charAt(contadorPosicion);
				contadorPosicion++;
			}
			letra[0]= (byte) getNumero(cadenacruda);
			System.out.println("poscicion: "+contadorPosicion);
//			System.out.println("bits: "+cadenacruda);
//			System.out.println("numero: "+getNumero(cadenacruda));
//			System.out.println("byte: "+letra[0]);
			String cadenaSigno = new String(letra);
//			System.out.println("signo: "+cadenaSigno);
			
			cadenacruda = "";
			contador1 = contadorPosicion+numeroBits;
			System.out.println("poscicionA: "+contadorPosicion);
			
			while(contadorPosicion < contador1) {
				cadenacruda = cadenacruda + bits.charAt(contadorPosicion);
				contadorPosicion++;
			}
			System.out.println("poscicion: "+contadorPosicion);
//			System.out.println("bits: "+cadenacruda);
//			System.out.println("numero: "+getNumero(cadenacruda));
			int cadenaIndice = getNumero(cadenacruda);
//			System.out.println("indice: "+cadenaIndice);
			
			
			cadenacruda = "";
			contador1 = contadorPosicion+8;
			System.out.println("poscicionA: "+contadorPosicion);
			
			while(contadorPosicion < contador1) {
				cadenacruda = cadenacruda + bits.charAt(contadorPosicion);
				contadorPosicion++;
			}
			letra[0]= (byte) getNumero(cadenacruda);
			System.out.println("poscicion: "+contadorPosicion);
//			System.out.println("bits: "+cadenacruda);
//			System.out.println("numero: "+getNumero(cadenacruda));
//			System.out.println("byte: "+letra[0]);
			String cadenaComa = new String(letra);
//			System.out.println("coma: "+cadenaComa);
			cadenacruda = "";
			
			diccionario.add(new NodoLZW(cadenaLetra, cadenaIndice));
			
			contadordic--;
		}
		
		
		while(contadorPosicion < bits.length()) {
			
			int contador2 = contadorPosicion+numeroBits;
			while(contadorPosicion < contador2) {
				System.out.println(">>: "+contadorPosicion);
				System.out.println("&& "+ contador2);
				cadenacruda = cadenacruda + bits.charAt(contadorPosicion);
				contadorPosicion++;
				System.out.println(bits.length());
				System.out.println(">>::: "+contadorPosicion);
			}
			
			cadenaCodificada =cadenaCodificada +" "+getNumero(cadenacruda);
			System.out.println("##"+cadenaCodificada);
			cadenacruda= "";
		}
		
	}
	
	
	public int getNumero(String bits) {
		int numeroBits = bits.length();
		int salida = 0;
		for (int i = 0, j = numeroBits-1; i < numeroBits;i++,j--) {
			if(bits.charAt(j) == '1') {
				salida = salida + (int)Math.pow(2, i);
			}
		}
		return salida;
	}
	
	
	public String descomprecion() {
		String salida= "";
		String caracter ="";
		int codigoViejo;
		int codigoNuevo;
		String cadena= "";
		String[] codigo = cadenaCodificada.split(" ");
		System.out.println(codigo[2]);
		codigoViejo = (int)Integer.parseInt(codigo[1]);
		caracter = buscarEnDiccionario(codigoViejo);
		System.out.println(caracter);
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
		
		
		
		return salida;
	}
	
	
	private String buscarEnDiccionario(int indice) {
		for( int i = 0; i < diccionario.size();i++) {
			if (diccionario.get(i).getIndice() == indice) {
				return diccionario.get(i).getSimbolo();
			}
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		DecodificacionLZW d = new DecodificacionLZW();
		lectorEscritor l = new lectorEscritor();
//		System.out.println(d.getNumero("011000110"));
		d.inicializar("0010011100001001010010010011110100000000000101100001000000011110100000000100101100011010000011110100000001000101100011000010011110100000001100101100011001000011110100000010000101100011001110011110100000100000101100011100100011110100000100100101100011001010011110100000101000101100011101000011110100000110000101100011101110011110100000111000101100011010110011110100001000100101100011011100011110100001001100101100001011100011110100001010100101100010011110011110100001011100101100010001100011110100001101000101100011010010011110100001110000101100011110010011110100001111100101100011001100011110100010001000101100011011110011110100010011100101100001011000011110100010101000101100011100110011110100011001000101100001101010011110100011111100101100010100000011110100100000100101100010011010011110100100001000101100011011010011110100100111000101100010101000011110100110010000101100011000110011110100111100100101100011101010011110100111101100101100011100000011110100111110000101100011011000011110100111110100101100011000100011110101001001100101100000010100011110101010000100101100010101110011110101010001100101100011010100011110101110100100101100010010100011110101111001000101100010000010011110110001100000101100011110100011110110100100100101100011101100011110110111011100101100010100110011110111000011100101110000000000000000001000000010000000011000000100000000001000000011000000001000001000000001001000001010000000011000001100000000001000001110000001010000001010000010001000001010000010011000000100000010101000000001000010111000010011000000001000011010000001001000011100000000100000000011000011111000101100000100010000001100000001010000001001000010011000100111000100111000010011000101010000000001000100111000100010000011100000010011000011100000110010000000010000001010000101011000001110000100111000001001000010001000101100000110011000111111000000001001000001001000010000111100000100111000110101000010011000110011000000010000100111001001110000001010000101100000111010000000001000001100001001101001011110000101101001010111000100111000110101000001001000111100001100100001011000000111111001100110000111001000110011001110001000000001000110010000110110000101100000000001001111001000100111001111011001111100001111101001101101000100111000100010000000001001001110001000110000100010001000010000111001000000100000110010001011111010000100010010011000000011000001001000000001000000100001110110001101000010011101000111100010100001000000001010100011001101101000101001000101011000101101010000110010001000010001010000000001010001100000000001010010011000110110000001001010010101000000011001101111000101101001010101001111001001101101000001100000000011001111101000010001010100000000000001001100100001101011001110000000100111001111101000101011001111011010110001010010011010000111001101001001010110000000001000010011000001010000001110000000001011101001000100111010010011001010000010110010000101011011110010000011100001001110011000000011000010000000001011000100000101100011000110001111011011001000010010101011001011011001101000001000000011100000001001001111101010010001000011100010010011000111100100011000001001000001001010010000100000001110000000010000011100010001010001010000000110101000110100001111110011000000010000100000110000000110010010111000001111011000001001010110010000110011011010011000101000000101010000000001001111100000011100101001001101001001000000011001100101011110101001101000011110111010101110000101011001111101000110010010110111000101001000110011001010101000001000000000010000001100001010000010010011011011110001010001000110100000000011010010101101110111001001010001000110000101001001111100001111100000011111000010101010100001000111110000000001110000111000110010011111100001000100001000110001001110001011100001010101000010011000001000100011011001100111000110011000001001001111011000010011100110001100110011001010100001110000001011000100000011010011001010111011100011011011111101000111111001100001000010001011110001001101011000001010001001001010110000011010010001101111000001100001111011010000101000000011001101100000101000101001000100100110000000001011101011101001010100111101000110010001010000000100111001101100000110011010001111000101110011100100011100110010010010001101111000100010001011100000000001001111101100111000001111001000000010000111100010100011100001010001111111000100111010000100001111010000000011000011100000000001011111001011111011101000011100010011000110001010011010101011111001101010011000100000001010100001001001111101000100111100011111010111101000101001101110100101100101000100111000000100010100001");
		System.out.println("lineas diccionario: "+d.getCaracteresDiccionario());
		System.out.println("numero de bits por simpolo en el mensaje: "+d.getNumeroBits());
		System.out.println("diccionario >>: ");
		for(int i = 0; i < d.getDiccionario().size();i++) {
			System.out.println(d.getDiccionario().get(i).getSimbolo()+" = "+d.getDiccionario().get(i).getIndice());
		}
//		d.setCadenaCodificada(" 0 1 2 3 4 1 3 1 8 9 10 3 12 1 14 10 10 17 10 19 4 21 1 23 19 1 26 9 28 4 3 31 45 34 12 10 9 19 39 39 19 42 1 40 34 28 19 28 50 2 10 44 14 39 9 17 45 52 63 1 65 66 61 40 54 19 52 2 39 78 10 45 59 1 12 78 95 46 88 39 54 9 61 100 89 64 103 58 52 114 1 50 55 45 1 121 39 123 124 125 110 39 34 1 78 71 34 67 58 4 50 96 133 147 3 9 1 4 119 105 158 61 1 162 110 42 44 46 135 137 139 1 141 1 147 55 9 150 3 112 46 86 121 110 12 3 125 17 161 100 108 113 39 125 44 123 177 147 136 106 87 1 19 10 14 1 231 39 147 81 178 44 240 28 78 191 193 1 195 45 197 123 199 150 202 204 8 28 9 125 146 28 148 61 278 73 75 133 14 2 28 139 81 54 53 127 191 133 49 50 184 123 9 178 52 210 41 43 1 124 28 327 327 3 102 244 105 246 174 44 125 51 183 42 52 86 8 2 12 81 147 221 82 53 3 150 373 75 71 42 124 124 31 61 63 1 388 51 251 69 71 78 93 86 19 8 282 104 52 9 123 19 303 305 85 113 89 258 154 187 282 252 64 98 17 240 108 10 74 176 209 112 12 124 134 3 109 41 326 233 235 155 332 51 81 40 109 52 144 47 227 229 147 112 34 93 1 125 310 121 2 61 163 265 128 39 133 123 3 28 1 248 250 321 274 50 155 348 107 195 10 264 125 39 286 1 367 369 354 39 4 576 282 50 124 245 332 110 10 286 312 53 28 332 143 71 226 228 230 148 242 51 359 85 305 100 628 171 64 396 130 141 263 1 1");
		System.out.println(d.getCadenaCodificada());
		System.out.println(d.descomprecion());
		l.escribirTexto(d.descomprecion(), "C:\\Users\\57318\\Documents\\ICESI\\prubas comunicaciones digitales\\descompresion.ascii");
//		for(int i = 0; i < d.getDiccionario().size();i++) {
//			System.out.println(d.getDiccionario().get(i).getSimbolo()+" = "+d.getDiccionario().get(i).getIndice());
//		}
	}
}
