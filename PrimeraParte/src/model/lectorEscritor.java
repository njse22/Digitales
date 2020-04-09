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
	
	public lectorEscritor() {
	}
	
	public String leerTexto(String direccion) {
		
		try {
			FileReader fr = new FileReader(new File(direccion));		
			BufferedReader bf = new BufferedReader(fr);
			String cadena = null;
			String linea = bf.readLine();
			while (linea != null) {
				if(cadena == null) {
					cadena = linea+"\n";
				}else {					
					cadena = cadena + linea+"\n";
				}
				linea = bf.readLine();
			}
			
			bf.close();
			fr.close();
			
			
			
			return cadena;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
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
			byte[] bytes = archivoLectura.readAllBytes();
			for(int i = 0; i < bytes.length; i++) {
				salida = salida + agregarCeros(valorBinario(bytes[i])+"");
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
	
}
