package model;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ImageProcessor {

	private String pathImage; 
	private Image image; 
	private String outFile; 
	private File binaryfile; 
	
	public ImageProcessor(String pathFile) {
		this.pathImage = pathFile.toLowerCase(); 
		this.image = Toolkit.getDefaultToolkit().getImage(pathImage);
		this.outFile = pathImage.replace(".jpg", ".txt"); 
		convertToBinary(outFile);
	}
	
	
	/**
	 * pre: pathImage es la dirección a un archivo .jpg  
	 * 
	 * */
	private void convertToBinary(String outFile) {
		
		try {
			
			PrintWriter out = new PrintWriter(outFile);
			PixelGrabber grabber = new PixelGrabber(image, 0, 0, -1, -1, false);
			
			if(grabber.grabPixels()) {
				int width = grabber.getWidth(); 
				int heigth = grabber.getHeight(); 
				
				int[] data = (int[])grabber.getPixels();
				int loopflag = 1; 
				int output; 
				
				int threshold = 12500000; 
				
				for(int i = 0; i < width*heigth; i++) {
					// para el blanco
					if(data[i] == 16777215)
						output = 1; 
					//para el negro 
					else if(data[i] == 0)
						output = 0; 
					//para otro color diferente de blanco o negro
					else if(data[i] < threshold)
						output = 0; 
					else {
						output = 1; 
					}
					
					out.print(output);
					
					if(width == (i+1)/loopflag) {
						out.println();
						loopflag++;
					}
				}	
			}
			out.close();
			RLE();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	

	private String readFile() {
		String outStr = ""; 
		try {
			FileReader reader = new FileReader(outFile);
			int i; 
			while ( (i = reader.read()) != -1) {
				outStr += (char) i; 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return outStr; 
	}

	
	private void RLE() {
		String str = readFile();
		int n =  str.length(); 
		for (int i = 0; i < n; i++) {
			int count = 1; 
			while(i < n-1 && str.charAt(i) == str.charAt(i+1) ) {
				count ++; 
				i++; 
			}
            System.out.print(count); 
            System.out.print(str.charAt(i));
		}
	}
		
	
	
}
