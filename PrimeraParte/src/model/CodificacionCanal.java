package model;

public class CodificacionCanal {
	
	/**
	 * String con la direccion del archivo de texto plano que contine los datos de la matriz de paridad.
	 * */
	public static final String MATRIZ_DE_PARIDAD = "./data/resultados segunda entrega/MatrizDeParidad.txt";
	
	/**
	 * representa la matriz de paridad que sera necesaria para la codificacion de lo datos.
	 * */
	private int[][] matrizParidad;
	
	/**
	 * objeto que se encarga de leer y escribir archivos de texto plano.
	 * */
	private lectorEscritor lecEsc;
	
	public CodificacionCanal() {
		lecEsc = new lectorEscritor();
	}
	
	public int[][] getMatriz(){
		return matrizParidad; 
	}
	
	/**
	 * en este metodo se inicializa la matriz de paridad a partir de un archivo de texto plano que contiene su informacion
	 * */
	public void inicializarMatriz() {
		String[] datosMatriz = lecEsc.leerTexto(MATRIZ_DE_PARIDAD).split("\n");// se toman los datos del archivo de texto.
//		for(int i=0;i<datosMatriz.length;i++) {
//			System.out.println(datosMatriz[i]);
//		}
		
		String[] temporal = datosMatriz[0].split(" ");
		matrizParidad = new int[Integer.parseInt(temporal[0])][Integer.parseInt(temporal[1])];// se asigna el tama�o de la matriz.
		
		for(int i = 1;i<datosMatriz.length;i++) {                     //-----------------------------------------------+
			temporal = datosMatriz[i].split(" ");                     //dentro de esta seccion se lleva ---------------+
			for(int j = 0;j<temporal.length;j++) {                    //acabo la asignacion de cada uno de los---------+
				matrizParidad[i-1][j]= Integer.parseInt(temporal[j]); //datos de la matriz en sus correspondientes-----+
			}                                                         //posciciones------------------------------------+
		}                                                             //-----------------------------------------------+
		
//		String cadenaVerificacion = "";
//		for(int i= 0; i < matrizParidad.length;i++) {
//			for(int j=0; j < matrizParidad[0].length;j++) {
//				cadenaVerificacion = cadenaVerificacion+matrizParidad[i][j];
//			}
//			System.out.println(cadenaVerificacion);
//			cadenaVerificacion ="";
//		}
	}
	
	/**
	 * este metodo cumple la funcion de verificar si la matris de n filas por m columnas esta sistematizada, verificando la seccion que
	 * corresponde a la identidad que va desde la fila 0 hasta la fina n y desde la columna m-n hasta m.
	 *          +++++++
	 * |0 0 0 1 +1 1 1+|
	 * |0 1 1 0 +0 1 1+| matriz de 3 x 7 no sistematizada. condiccion = false
	 * |1 0 1 0 +1 0 1+|
	 *          +++++++
	 *          
	 *          +++++++
	 * |0 1 1 1 +1 0 0+|
	 * |1 0 1 1 +0 1 0+| matriz de 3 x 7 sistematizada. condicion = true
	 * |1 1 0 1 +0 0 1+|
	 *          +++++++       
	 *          
	 * @return condicion: boolean que corresponde al resultado de la verificacion- true si si es sistematizada o false de lo contrario.
	 * */
	public boolean estaSistematizada() {
		boolean condicion = true;
		int veriFila= 0; // contador que se mueve a travez de la posicion fila de cada pivote de la sub matriz de identidad.
		int veriColum = matrizParidad[0].length-matrizParidad.length;// contador que se mueve a travez de la posicion columna de cada pivote de la sub matriz de identidad.
		for(int i = 0;condicion && i<matrizParidad.length;i++) {// se inicia desde la fila 0 hasta la fila n
			for (int j = matrizParidad[0].length-matrizParidad.length;condicion && j<matrizParidad[0].length;j++) {// se inicia desde la columna m - n hasta la columna m
				if(i == veriFila && j == veriColum && matrizParidad[i][j] == 1) {// verifica que los pivotes sean uno 
					veriFila++; // incremente los contadores de fila y de columna--+ 
					veriColum++;// para avanzar al siguiente pivote----------------+ 
				}else if(i != veriFila && j != veriColum &&matrizParidad[i][j] != 0){// verifica si las demas posiciones de la matriz son 0
					condicion = false;// de no serlo la matriz no es sistematica 
				}else if(i == veriFila && j == veriColum && matrizParidad[i][j] != 1) {
					condicion = false;//en cualquier otro caso la matriz no es sistematica
				}
			}
		}
		
		return condicion;
	}
	
	
	/**
	 * este metodo se encarga de sistematizar la matriz de paridad elaborando operaciones entre filas. 
	 * */
	public void sistematizar() {
		int[][] cambio = matrizParidad;//copia los valores de la matriz de paridad
		
		int pivoteI = 0;//contador de la filadel pivote actual.
		int pivoteJ = cambio[0].length - cambio.length;//contador de la columna del pivote actual.
		
		for (int i = 0; i < cambio.length; i++) {// dentro de este for se desarrollaran las operaciones de filas necesarias para sistematizar la matriz.
			 if(pivoteI == 0 && pivoteJ == cambio[0].length - cambio.length) {       //----------------------------------------------+        
				 for (int n = pivoteI+1;n<cambio.length;n++) {                       //dentro de esta seccion se ponen ceros---------+
					 if(cambio[n][pivoteJ]== 1) {                                    //debajo del primer pibote utilizando-----------+
						 for(int m = 0; m < cambio[0].length;m++) {                  //operaciones entre filas-----------------------+
							cambio[n][m] = Math.abs(cambio[pivoteI][m]-cambio[n][m]);//----------------------------------------------+
						 }                                                           //----------------------------------------------+
					 }                                                               //----------------------------------------------+
				 }                                                                   //----------------------------------------------+
				 
				 pivoteI++;// avanza al siguiente
				 pivoteJ++;//pivote
			 }else if(pivoteI > 0 && pivoteI < cambio.length-1 && pivoteJ > cambio[0].length - cambio.length && pivoteJ < cambio[0].length-1) {//----------------------------------------------+
				 for (int n = pivoteI+1;n<cambio.length;n++) {                                                                                 //en esta seccion se ponen ceros en las filas---+
					 if(cambio[n][pivoteJ]== 1) {                                                                                              //superiores al los pivotes centreles por medio-+
						 for(int m = 0; m < cambio[0].length;m++) {                                                                            //de operaciones entre filas--------------------+
							cambio[n][m] = Math.abs(cambio[pivoteI][m]-cambio[n][m]);                                                          //----------------------------------------------+
						 }                                                                                                                     //----------------------------------------------+
					 }                                                                                                                         //----------------------------------------------+  
				 }                                                                                                                             //----------------------------------------------+ 
				                                                                                                                               //+++++++++++++++++++++++++++++++++++++++++++++++
				 for (int n = pivoteI-1;n>=0;n--) {                                                                                            //en esta seccion se ponen ceros en las filas---+
					 if(cambio[n][pivoteJ]== 1) {                                                                                              //inferiores a los pivotes centrales por medio -+
						 for(int m = 0; m < cambio[0].length;m++) {                                                                            //de operaciones entre filas--------------------+
							cambio[n][m] = Math.abs(cambio[pivoteI][m]-cambio[n][m]);                                                          //----------------------------------------------+
						 }                                                                                                                     //----------------------------------------------+
					 }                                                                                                                         //----------------------------------------------+
				 }                                                                                                                             //----------------------------------------------+
				 
				 pivoteI++;//avanza al 
				 pivoteJ++;//siguiente pivote
			 }else if(pivoteI == cambio.length-1 && pivoteJ == cambio[0].length-1) { //----------------------------------------------+
				 for (int n = pivoteI-1;n>=0;n--) {                                  //en esta seccion se ponen ceros en las filas---+  
					 if(cambio[n][pivoteJ]== 1) {                                    //superiores el ultimo pivote de la matriz------+
						 for(int m = 0; m < cambio[0].length;m++) {                  //por medio de operaciones entre filas ---------+ 
							cambio[n][m] = Math.abs(cambio[pivoteI][m]-cambio[n][m]);//----------------------------------------------+ 
						 }                                                           //----------------------------------------------+   
					 }                                                               //----------------------------------------------+
				 }                                                                   //----------------------------------------------+
				 
				 pivoteI++;//avansa al
				 pivoteJ++;//siguiente pivote
			 }
		}
		
		
		matrizParidad = cambio;// intercambia la antigua matris de pivotes por la nueva matriz sistematizada.
		
	}
	
	public static void main(String[] args) {
		CodificacionCanal c = new CodificacionCanal();
		c.inicializarMatriz();
		System.out.println(c.estaSistematizada());
		c.sistematizar();
	}

}
