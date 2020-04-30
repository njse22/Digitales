package model;

public class CodificacionCanal {
	
	/**
	 * String con la direccion del archivo de texto plano que contine los datos de la matriz de paridad.
	 * */
	public static final String MATRIZ_DE_PARIDAD = "./data/resultados segunda entrega/MatrizDeParidad.txt";
	
	/**
	 * String con la direccion del archivo que contendra la cadena ya codificada.
	 * */
	public static final String ARCHIVO_CODIFICACION_CANAL = "./data/resultados segunda entrega/codificacionCanal.txt";
	
	/**
	 * String con la direccion del archivo que contendra el resumen de la codicficacion de canal.
	 * */
	public static final String ARCHIVO_RESUMEN_CODIFICACION_CANAL = "./data/resultados segunda entrega/resumen_codificacionCanal.txt";
	/**
	 * String con la direccion del archivo que contendra la cadena ya codificada.
	 * */
	public static final String ARCHIVO_CODIFICACION_FUENTE = "./data/resultados primera entrega/codificacion.txt";
	
	/**
	 * representa la matriz de paridad que sera necesaria para la codificacion de lo datos.
	 * */
	private int[][] matrizParidad;
	
	/**
	 * objeto que se encarga de leer y escribir archivos de texto plano.
	 * */
	private lectorEscritor lecEsc;
	
	/**
	 * cadena de bits que se desea codificar.
	 * */
	private String cadenaCruda;
	
	/**
	 * cadena de bits ya codificada
	 * */
	private String cadenaCodificada;
	
	
	public CodificacionCanal() {
		lecEsc = new lectorEscritor();
	}
	

	public int[][] getMatriz(){
		return matrizParidad; 
	}
	
	
	
	public int[][] getMatrizParidad() {
		return matrizParidad;
	}



	public void setMatrizParidad(int[][] matrizParidad) {
		this.matrizParidad = matrizParidad;
	}



	public String getCadenaCruda() {
		return cadenaCruda;
	}



	public void setCadenaCruda(String cadenaCruda) {
		this.cadenaCruda = cadenaCruda;
	}



	public String getCadenaCodificada() {
		return cadenaCodificada;
	}



	public void setCadenaCodificada(String cadenaCodificada) {
		this.cadenaCodificada = cadenaCodificada;
	}




	/**
	 * en este metodo se inicializa la matriz de paridad a partir de un archivo de texto plano que contiene su informacion
	 * */
	public void inicializarMatriz() {
		String[] datosMatriz = lecEsc.leerTexto(MATRIZ_DE_PARIDAD).split("\n");// se toman los datos del archivo de texto.
		
		String[] temporal = datosMatriz[0].split(" ");
		matrizParidad = new int[Integer.parseInt(temporal[0])][Integer.parseInt(temporal[1])];// se asigna el tama�o de la matriz.
		
		for(int i = 1;i<datosMatriz.length;i++) {                     //-----------------------------------------------+
			temporal = datosMatriz[i].split(" ");                     //dentro de esta seccion se lleva ---------------+
			for(int j = 0;j<temporal.length;j++) {                    //acabo la asignacion de cada uno de los---------+
				matrizParidad[i-1][j]= Integer.parseInt(temporal[j]); //datos de la matriz en sus correspondientes-----+
			}                                                         //posciciones------------------------------------+
		}                                                             //-----------------------------------------------+
		
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
	 * @return condicion: boolean que corresponde al resultado de la verificacion- true si  esta sistematizada o false de lo contrario.
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
	 * metodo encargado de codificar una cadena de caracteres empleando 
	 * una matriz de paridad para luego pasar la cadena ya codificada a un archivo de texto plano.
	 * */
	public void codificar() {
//		cadenaCruda = lecEsc.leerTexto("./data/resultados segunda entrega/prueba codificacionCanal.txt");
		cadenaCruda = lecEsc.leerTexto(ARCHIVO_CODIFICACION_FUENTE);//extrae la informacion a codeficar de un archivo de texto plano.
		int bitsMensaje = matrizParidad[0].length -matrizParidad.length;//la cantidad de bits del mensaje que se iran tomando para la codificacion.
		int bitsParidad = matrizParidad.length;// cantidad de bits para la paridad
		int contadorPoscicion = 0; // contador para ir moviendo de bit en bit por el mensaje a codificar.
		char[] mensajeCrudo = new char[matrizParidad[0].length];//grupo de bits del mensaje que iran siendo codificados;
		String mensajeCodificado = "";// prupo de bits ya codificados.
		int constante = matrizParidad[0].length-matrizParidad.length;
		while(contadorPoscicion<cadenaCruda.length()-1) {// ciclo dentro del cual se codifica el mensaje
			for (int i = 0; i <constante;i++ ) {//ciclo encargado de leer la cadena cruda bit por bit 
				mensajeCrudo[i] = cadenaCruda.charAt(contadorPoscicion);
				contadorPoscicion++;
				
			}
			int bitChequeo = constante;
			int contadorFila = 0;
			while (bitChequeo < matrizParidad[0].length) {//ciclo encargado de asignar los bits de chequeo
				int contadorChequeo = 0;
				
				for(int i = 0; i < constante;i++) {
					if(matrizParidad[contadorFila][i]==1 && mensajeCrudo[i] == '1') {
						contadorChequeo++;
					}
				}
				if(contadorChequeo == 0 ||(contadorChequeo%2)==0) {
					mensajeCrudo[bitChequeo] = '0';
				}else if((contadorChequeo%2)!=0) {
					mensajeCrudo[bitChequeo] = '1';
				}
				
				bitChequeo++;
				contadorFila++;
			}
			
			for(int i =0; i < mensajeCrudo.length;i++) {
				mensajeCodificado = mensajeCodificado+mensajeCrudo[i];
			}
			
		}
		
		cadenaCodificada = mensajeCodificado;
	}
	/**
	 * metodo encargado de guardar un resumen de el proceso de codificacion de canal en un archuvo de texto
	 * */
	public void resumen() {
		String cadenaVerificacion = "++++++++++++++++++++++++++++++++++++++++++++++ MATRIZ DE PARIDAD SISTEMATIZADA DE "+matrizParidad.length+"FILAS X "+matrizParidad[0].length+"COLUMNAS ++++++++++++++++++++++++++++++++++++++++++++++\n";
		for(int i= 0; i < matrizParidad.length;i++) {
			for(int j=0; j < matrizParidad[0].length;j++) {
				cadenaVerificacion = " "+cadenaVerificacion+matrizParidad[i][j];
			}
			cadenaVerificacion = cadenaVerificacion+"\n";
		}
		
		cadenaVerificacion = cadenaVerificacion+"\n"
				+ "++++++++++++++++++++++++++++++++++++++++++++++ RESUMEN CODIFICACION ++++++++++++++++++++++++++++++++++++++++++++++ \n\n"
				+ "cadena antes de ser codificada:\n"+cadenaCruda+"\n"
				+ "con una logitud de: "+ cadenaCruda.length()+" bits \n\n"
				+ "cadena despues de ser codificada:\n"+cadenaCodificada+"\n"
				+ "con una logitud de: "+ cadenaCodificada.length()+" bits \n\n";
		
		lecEsc.escribirTexto(cadenaVerificacion, ARCHIVO_RESUMEN_CODIFICACION_CANAL);
	}
	
	public void mainCodificacionCanal() {
		inicializarMatriz();
		if(!estaSistematizada()) {			
			sistematizar();
		}
		codificar();
		resumen();
	}

}
