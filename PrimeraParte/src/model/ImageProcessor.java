package model;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ColorModel;
import java.awt.image.ImageProducer;
import java.awt.image.IndexColorModel;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.PrintWriter;

public class ImageProcessor {
	
	private ImageProducer producer; 
	private Image image;
	private String codecImage; 
	private PixelGrabber grabber; 
	private int[] data; 
	
	public ImageProcessor() {
		this.image = null; 
		this.codecImage = "";
		this.producer = null;
		this.data = null;  
		
	}
	
	public String getCodecImage() {
		return codecImage;
	}
	
	public void processImage(String infile)  {
		infile = infile.toLowerCase();
		this.image = Toolkit.getDefaultToolkit().getImage(infile);

		try {
			
			this.grabber = new PixelGrabber(image, 0, 0, -1, -1, false);

			if (grabber.grabPixels()) {
				int width = grabber.getWidth();
				int heigth = grabber.getHeight();

				int[] data = (int[]) grabber.getPixels();
				int output;

				// RLE - 
				int n =  data.length;
				for (int i = 0; i < n; i++) {
					int count = 1; 
					while(i < n-1 && data[i] == data[i+1]) {
						count++; 
						i++; 
					}
					codecImage += count;
					codecImage += data[i];
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void convertBinary() {
		data = new int[codecImage.length()];
		int binary = 0;
		int digict = 0; 
		int number = 0; 
		int exp = 0; 
		for (int i = 0; i < codecImage.length(); i++) {
			number = codecImage.charAt(i); 
			while(codecImage.charAt(i) != 0) {
				digict = number%2;  
				binary = (int) (binary+ digict*Math.pow(10, exp));
				exp++; 
				number = number/2; 
			}
		data[i] = binary; 
		}
	}
	
	
	public void writeImage() {
		byte[] grey = new byte[256]; 
		for(int i = 0; i < 256; i++)
			grey[i] = (byte)i; 
		ColorModel greyModel = new IndexColorModel(8, 256, grey, grey,grey); 
		this.producer = new MemoryImageSource(320, 200, data, 0, 320); 	
		Image imageOut = Toolkit.getDefaultToolkit().createImage(this.producer);	
		
	} 
	
	public static String decode(final String st) {
	    final StringBuilder sb = new StringBuilder();

	    final char[] chars = st.toCharArray();

	    int i = 0;
	    while (i < chars.length) {
	        int repeat = 0;
	        while ((i < chars.length) && Character.isDigit(chars[i])) {
	            repeat = repeat * 10 + chars[i++] - '0';
	        }
	        final StringBuilder s = new StringBuilder();
	        while ((i < chars.length) && !Character.isDigit(chars[i])) {
	            s.append(chars[i++]);
	        }

	        if (repeat > 0) {
	            for (int j = 0; j < repeat; j++) {
	                sb.append(s.toString());
	            }
	        } else {
	            sb.append(s.toString());
	        }
	    }

	    return sb.toString();
	}
	
	
	
	
	
}
