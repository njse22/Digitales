package model;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.PixelGrabber;
import java.io.PrintWriter;

public class ImageProcessor {
	 
	private Image image;
	private String codecImage; 
	private PixelGrabber grabber; 
	
	public ImageProcessor() {
		this.image = null; 
		this.codecImage = ""; 
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
	
	
	
	
	
}
