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
		d.inicializar("00110000000010100100100100111101000000000000101100001000000011110100000000010010110001101000001111010000000010001011000110000100111101000000001100101100011001000011110100000001000010110001100111001111010000001000001011000111001000111101000000100100101100011001010011110100000010100010110001110100001111010000001100001011000111011100111101000000111000101100011010110011110100000100010010110001101110001111010000010011001011000010111000111101000001010100101100010011110011110100000101110010110001000110001111010000011010001011000110100100111101000001110000101100011110010011110100000111110010110001100110001111010000100010001011000110111100111101000010011100101100001011000011110100001010100010110001110011001111010000110010001011000011010100111101000011111100101100010100000011110100010000010010110001001101001111010001000010001011000110110100111101000100111000101100010101000011110100011001000010110001100011001111010001111001001011000111010100111101000111101100101100011100000011110100011111000010110001101100001111010001111101001011000110001000111101001001001100101100000010100011110100101000010010110001010111001111010010100101001011000110101000111101001110110100101100010010100011110100111101100010110001000001001111010100011100001011000111101000111101010100110100101100011101100011110101011110110010110001010011001111010110001101001011000101011000111101101000001000101100011110000011110110100110100010110000-1111000111101101100110100101100-1000000000111101101100111000101100-1100111001111011011001111001011000010100000111101214748364700101100001010010011110121474836470010110001001100001111012147483647001011000100111000111101214748364700101110000000000000000000010000000010000000001100000001000000000001000000001100000000010000001000000000100100000010100000000011000000110000000000010000001110000000101000000010100000010001000000101000000100110000000100000001010100000000010000010111000001001100000000010000011010000000100100000111000000000100000000001100000111110000110101000010001000000011000000001010000000100100000100110000100111000010011100000100110000101010000000000100001100000000100010000001110000000100110000011100000011001000000000100000001010000011010000000011100000100111000000100100000100010000110101000011110000001111110000000001000100000100010000100001000101000011000000001111100000010011000011110000000000100000100111000100111000000010100000110101000100001100000000010000001100000101011000011001110000110110000110000000001001110000111110000000100100010001010001100100000110000100010010000001101111000100001000001111000001111010000000000100001100100000111111000011010100000000010001111001000010011100011110110001111100000111110100011101100000100111000010001000000000010001001110000100111100001000100001001011000100001000000001000000110010000110100000100011010010010011000000001100000010010000000001000000010000011111110001110001001010011000010001010010100001000000000100101010100010100101000111011000001100100000110100000011011000100011110010010001001001001100000000010010010101000000000100100100110000111111000000100100100111100000000011000111100000001101100001011110000111100100011101100000001100000000001100011111010000010001001010100100101010110000000001000110010000011101000001111001000010011100011111010000110100000111101100101110110010010011001001000000011100100001011111000000000100000100110000001010000000111000000000010011101101000010011100100100110001011001001011110000001101000011110110000001110000010011100011001011001100110100000000010011001111000011010100110100010001111011001101001100100111100011010110001101100000000010000000011100000000100100011111010010011010000001110000100111000001000101010001110000010100010001010011001000110100000011100000000010000001110000100100110001011001000011111000001111010010000111001100101100100011010000111001000011001000110000100001111011000000100100101111000000111100001101111000001100010000110011000000000100011111000000011100010100110101010011010000000011000110111001000000000001110001010000001000101110000000110100000111110100001110110011000001000011001000001111000001011110000000100000000000100000001100000101100100100100110011101001000101101000001111010000000011001001111001011110110001010011000100111100001100100001111100000111110000000111110000010101001010101000101010100001000111000000000101100011010000111011010000011100010011010001001111000100111000011001010001011110000001001100000010000100100110000111000000001111000000001001000111101100000100110100111101010011111100010111010001111001000110000101000011100010100010001100010101001001100100001000000100100000011010100000010001001111110000011101000000001010000101001000101110100011011101000111100000000011000010000100001000111000000000110001110101000011000101010101000100110001001100011100111101100101010110010100100100001110110001011001000011000000011101010000111100001001100000001101110011101111001111000100100110110001111000000010001000011001010000000001000111110101010001000001111001000000001000010001010010101101010001010100100010000000100111001000110100100000110000000011000001110000000000010100000100010000011001010011110100011110000011101000101000110101101011000111001100110011110000001010010001010000011111010000100111010010101000110010000000110010011000000001011100010000100111000000010001001100100100100110000011001000011111000100000001010101101000011101100000001010010010101001010001100000111101000001110001010110100010010111000100111100111011100011110000001111001000100111000011111110000011101101011101100001011101010011111100011001001010000010001011010100010010000110011011001000101000100101010100010011011001011000100000110001110110001101011010100110100000111100010011100101011001110000110111000010011100001111000010010001000111011100001101000001111001000000001100011110110100100000001000100001010010100000001100010000100001010010000010001001010110101100011111000010100010001000011100001100101011001101101100111010110011110011001011000000111000000100110100010100001100111100011110110011000011000111110101101001000101111100001000100100100010110001110110011100111001110100000001110001001001111000010101110100110101010100010001001110010000110010001101110100001111000010010011010010100000100010000000001110000001110000110000000010001101010011101100011100010101110111011001011000010000100100111010001100101001110110100111011100010011101000000000110001000001001011101101100000010000100111011110111101010110000001100011000111011000000010000101111100010111000100101000110000110110000000111000110000110001100111011100110001010010100111001111000110011000101101010010100011100001000000011101100010110010000111000100000010010001001111000000001100111101110010100011010111010000000100110101110110010110000100101010100010000011001101010000001111100001000000001001110001001001111000000001001001001101101000010010100100000001110000001000100000100010000101001100100001110001011001010101101000100100000100100000000100010101111110010101001011000011100101111001010011001011000010011100011001110101011110001101001000000000011000001010001000110100101111110001110110100001111101011101100100100011000000101001110001000110001010000000000110001011001000001101001001111001011010111000101011000000100101010110100100111001001111010110000111010101110011010111010100000000100011111110001100000100011110110011111001100000111000000000012147483647010010100000111001010000110010011101101100011111012147483647011100001001010010010001000010010010011010010010100111000111100110100000011110010111001011000100100000101000010001111001000110011100100010012147483647000101011100101001010111011000011001011021474836470001111111100100110000000011000111010011101100111100100111100001000010100111011000010011101001100111000000000010011111100011100010100111000100011111000000111100000110010000011001100100100011010010010110100001010111010100011000111110000010000000111100100010101101001111110010010010001011110000100111101001000010011011100110100010100000111111000100000100010000110010100001");
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