package model;

public class NodoLZW {
	
	private String simbolo;
	private int indice;
	
	public NodoLZW(String simbolo, int indice) {
		this.simbolo = simbolo;
		this.indice = indice;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public int getIndice() {
		return indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

}
