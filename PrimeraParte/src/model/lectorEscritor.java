package model;

import java.io.BufferedReader;

import java.io.InputStream; 
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class lectorEscritor {
	
	
	private CodificacionLZW codificacionTexto;
	
	public lectorEscritor() {
	}
	
	public boolean leerTexto(String direccion) {
		
		try {
			FileReader fr = new FileReader(new File(direccion));		
			BufferedReader bf = new BufferedReader(fr);
			String cadena = null;
			String linea = bf.readLine();
			while (linea != null) {
				if(cadena == null) {
					cadena = linea;
				}else {					
					cadena = cadena +" "+ linea;
				}
				System.out.println(linea);
				linea = bf.readLine();
			}
			
			bf.close();
			fr.close();
			System.out.println(">>"+cadena);
			codificacionTexto = new CodificacionLZW(cadena);
			System.out.println(codificacionTexto.getCadenaCodificada());
			System.out.println(codificacionTexto.salidaBinario());
			System.out.println(codificacionTexto.diccionarioBinario());
			System.out.println(codificacionTexto.getCaracteresDiccionario());
			System.out.println(codificacionTexto.getCadenaBinariaCompleta());
			
			return true;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	public boolean escribirTexto(String cadena, String direccion) {
		try {
			FileWriter fw = new FileWriter(new File(direccion));
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(cadena);
			
			bw.close();
			fw.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return false;
	}
	
	
public String leerBytes(String direccion) {
		
		try {
			FileInputStream archivoLectura = new FileInputStream(new File(direccion));
			String salida = "";
			byte[] bytes = new byte[1024];
			int lector = archivoLectura.read(bytes); 
			int i = 0; 
			while(lector != -1 ) {
				salida = salida + agregarCeros(valorBinario(lector)+"");
				i++; 
			}		
			return salida;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
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
	
	private String agregarCeros(String cadena) {
		String cadenaCorregida = cadena;
		
		while(cadenaCorregida.toCharArray().length < 8) {
			cadenaCorregida = "0"+cadenaCorregida;
		}
		
		return cadenaCorregida;
	}
	
	
	
	
	public static void main(String[] args) {
		lectorEscritor l = new lectorEscritor();
//		System.out.println(l.leerTexto("C:\\Users\\57318\\Documents\\ICESI\\prubas comunicaciones digitales\\cossitas.ascii"));
		System.out.println(l.leerBytes("/home/slayer-nation/ICESI/Septimo_Semestre/Comunicaciones_Digitales/cossitas.ascii"));
	}

}
