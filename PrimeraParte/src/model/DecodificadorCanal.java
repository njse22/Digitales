package model;

public class DecodificadorCanal {
	
	private int[][] matrizParidad;
	
	public DecodificadorCanal() {
		
	}
	
	public void setMatrizParidad(int[][] matrizParidad) {
		this.matrizParidad = matrizParidad;
	}
	
	public boolean deteccionError(int[] sindrome) {
		if(binaryAdd(sindrome) == 0)
			return false; 
		else
			return true; 
	}
	
	public int liderDeCoclase(int[] sindrome) {
		int rows =  matrizParidad.length;
		for (int i = 0; i < rows; i++) {
			if (comparteVectors(matrizParidad[i], sindrome) ) {
				return i; 
			}   
		}
		return 0; 
	}
	
	public boolean comparteVectors(int[] vecM, int[] vec ) {
		for (int i = 0; i < vec.length; i++) {
			if( (vecM[i] ^ vec[i]) !=  0) {
				return false; 
			}
		}
		return true;
	}
	
	public int[] calcularSindrome(int[] r) {
		int rows =  matrizParidad.length; 
		int columns = matrizParidad[0].length; 
		int[] sindrome = new int[rows];  
			
		for (int i = 0; i < rows; i++) {
			int sum = 0;  
			for (int j = 0; j < columns; j++) {
				sum &= matrizParidad[i][j]*r[j]; 
			}
			sindrome[i] = sum;  
		}
		return sindrome; 
	}
	
	private int binaryAdd(int[] r) {
		int s = 0; 
		for (int i = 0; i < r.length-1; i++) {
			s &= r[i];  
		}
		return s;
	}
	
	
	public static void main(String[] args) {
		DecodificadorCanal dc = new DecodificadorCanal();
		CodificacionCanal c = new CodificacionCanal();
		c.inicializarMatriz();
		c.sistematizar();
		dc.setMatrizParidad(c.getMatriz());
		int[] r = {1,1,0,1,0,1,0}; 
		System.out.print("[ ");
		for (int i = 0; i < dc.calcularSindrome(r).length; i++) {
			System.out.print(dc.calcularSindrome(r)[i]);
			System.out.print(" , ");
		}
		System.out.print(" ]");
		
		int[] a = {1,1,1}; 
		int[] b = {1,1,1}; 
		
		System.out.println("\n"+   dc.comparteVectors(a, b) );
		
		
		
		
	}

}
