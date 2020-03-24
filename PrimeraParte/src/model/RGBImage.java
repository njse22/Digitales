package model;

public class RGBImage {
	
	private int width; 
	private int height; 
	private byte[] red; 
	private byte[] green; 
	private byte[] blue;
	
	public RGBImage(int width, int height) {
		this.width = width; 
		this.height = height; 
		
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
}
