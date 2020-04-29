package model;

public class DecodificadorCanal {
	
	private int[][] matrizParidad;
	
	public DecodificadorCanal() {
		
	}
	
	public void setMatrizParidad(int[][] matrizParidad) {
		this.matrizParidad = matrizParidad;
	}
	
	
	/**
	 * deteccionError : Método que determina si hay un error en base al sindrome 
	 * true  : si hay un error 
	 * false : si no lo hay 
	 * */
	public boolean deteccionError(int[] sindrome) {
		if(binaryAdd(sindrome) == 0)
			return false; 
		else
			return true; 
	}
	
	/**
	 * liderDeCoclase : encuantre el lider de coclase
	 * retorna el número de la colunna la cual corresponde con el lider de coclase 
	 * retorna zero si no la encuentra 
	 * */
	public int liderDeCoclase(int[] sindrome) {
		int rows =  matrizParidad.length;
		for (int i = 0; i < rows; i++) {
			if (comparteVectors(matrizParidad[i], sindrome) ) {
				return i; 
			}   
		}
		return 0; 
	}
	
	/**
	 * comparteVectors : compara dos vectores y determina si son iguales
	 * true  : si son iguales 
	 * false : si no lo son 
	 * */
	public boolean comparteVectors(int[] vecM, int[] vec ) {
		for (int i = 0; i < vec.length; i++) {
			if( (vecM[i] ^ vec[i]) !=  0) {
				return false; 
			}
		}
		return true;
	}
	
	/**
	 * calcularSindrome : calcula el sindrome de la operación rH = s 
	 * retorna un vector de enteros que represneta el sindrome
	 * */
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
	
	/**
	 * binaryAdd : suma cada valor del vector del sindrome 
	 * retorna el valor de la suma de las posiciones del sindrome 
	 * */
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
		 
		
		
	}

}
