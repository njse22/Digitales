package model;

import java.awt.Toolkit;
import java.awt.image.PixelGrabber;
import java.io.PrintWriter;

public class ImageProcessor {
	
	private static ImageProcessor instance = null; 
	
	private ImageProcessor() {}
	
	public synchronized static ImageProcessor getInstance() {
		if(instance == null)
			instance = new ImageProcessor(); 
		return instance; 
	}

	public void processImage(String infile) {
		infile = infile.toLowerCase();
		java.awt.Image image = Toolkit.getDefaultToolkit().getImage(infile);

		try {
			
			String outImage = ""; 
			String outfile = infile.replace(".jpg", ".txt");
			PrintWriter out = new PrintWriter(outfile);
			PixelGrabber grabber = new PixelGrabber(image, 0, 0, -1, -1, false);

			if (grabber.grabPixels()) {
				int width = grabber.getWidth();
				int heigth = grabber.getHeight();

				int[] data = (int[]) grabber.getPixels();
				int loopstatus = 1;
				int output;

				// default = 12500000.threshold value = 0 -> 99999999 (**currently not sure about the highest value...). **tips: adjust for every million first((+-)10000000)
				int threshold = 12500000;

				for (int i = 0; i < width * heigth; i++) {
					// white
					if (data[i] == 16777215) {
						output = 1;
					}
					// black
					else if (data[i] == 0) {
						output = 0;
					}
					// value that are not white/black.
					else if (data[i] < threshold) {
						output = 0;
					} else {
						output = 1;
					}
					
					outImage += output; 
				}
				// RLE - 
				int n =  outImage.length();
				for (int i = 0; i < n; i++) {
					int count = 1; 
					while(i < n-1 && outImage.charAt(i) == outImage.charAt(i+1)) {
						count++; 
						i++; 
					}
					out.print(count);
					out.print(outImage.charAt(i));
				}
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	
	
}
