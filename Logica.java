public class Logica
{
	private static Data capaDatos;
	private static GUI capaGUI;
	
	static int[] arregloDestruidosPC = new int[3];
	static int[] arregloDestruidosJugador = new int[3];
	
	private static int Barco1PC = 0;
	private static int Barco2PC = 0;
	private static int Barco3PC = 0;
	
	private static int Barco1Jugador = 0;
	private static int Barco2Jugador = 0;
	private static int Barco3Jugador = 0;
	
	
	public static void Logica()
	{
		capaDatos = new Data();
		capaGUI = new GUI();
	}
	
	public static void MatrizLeida(int tamano, String path)
	{
		capaDatos.leerTXT(tamano, path);
	}
	
	
	public static int[][] llenarMatrizJugador(int parTamanno, int parCantidadDeBarcos)
	{
		//Barco Pequeño
		int tamannoPeq2 = 2;
		int printBarco1 = 3;
		
		//Barco Mediano
		int tamannoMed3 = 3;
		int printBarco2 = 2;
		
		//Barco Grande 
		int tamannoGra4 = 4;
		int printBarco3 = 1;
		
		int[][] matriz = new int [parTamanno][parTamanno];
		matriz = LlenarBarco(matriz, tamannoPeq2, printBarco1, parCantidadDeBarcos);
		matriz = LlenarBarco(matriz, tamannoMed3, printBarco2, parCantidadDeBarcos);
		matriz = LlenarBarco(matriz, tamannoGra4, printBarco3, parCantidadDeBarcos);
		
		return matriz;
		
	}
	
	
	public static int[][] LlenarBarco(int[][] matriz, int tamanoBarco, int numeroPrint, int cantidadDeBarcos)
	{
		for (int cont =0; cont<cantidadDeBarcos; cont++)
		{
			boolean orientacion = direccion();
			//Obtener las posiciones
			int posX = posXRandom(tamanoBarco,  matriz.length);
			int posY = posYRandom(tamanoBarco, matriz.length);

			// VERTICAL
			if (orientacion == true)
			{
				//Evaluar si el barco puede ser escrito hacia abajo
				if (BarcoPuedeSerEscrito(posX, posY, matriz, numeroPrint, true, tamanoBarco))
				{
					for (int i = posY; i < posY+tamanoBarco; i++)
					{
						matriz[posX][i] = numeroPrint;
					}
				}
				else
				{
					cont--;
				}
			}
			// HORIZONTAL
			else 
			{
				//Evaluar si el barco puede ser escrito hacia abajo
				if (BarcoPuedeSerEscrito(posX, posY, matriz, numeroPrint, false, tamanoBarco))
				{
					for (int i = posX; i < posX+tamanoBarco; i++)
					{
						matriz[i][posY] = numeroPrint;
					}
				}
				else
				{
					cont--;
				}
			}	
		}
		
		return matriz;
	}
	
	public static boolean BarcoPuedeSerEscrito(int posX, int posY, int[][] matriz, int tipo, boolean haciaAbajo, int tamanoBarco)
	{
		//Evaluar si la posición está libre y Evaluar los vecinos de la primera posicion
		if (posicionLibre(posX, posY, matriz) == true && vecinosDiferentes(posX, posY, matriz, tipo, true, haciaAbajo) == true)
		{	//Verifique que el barco hacia abajo con los 3 hermanos faltantes se puede hacer
			if (haciaAbajo == true)
			{
				for (int i = posY + 1; i < posY+tamanoBarco; i++)
				{
					//Evaluar si la posicion es válida
					if (posicionValida(posX, i, matriz) == false || posicionLibre(posX, i, matriz) == false || vecinosDiferentes(posX, i, matriz, tipo, false, haciaAbajo) == false)
					{
						return false;
					}				 				
				}
			}
			else
			{
				for (int i = posX + 1; i < posX+tamanoBarco; i++)
				{
					//Evaluar si la posicion es válida
					if (posicionValida(i, posY, matriz) == false || posicionLibre(i, posY, matriz) == false || vecinosDiferentes(i, posY, matriz, tipo, false, haciaAbajo) == false)
					{
						return false;
					}				 				
				}
			}
		}
		else //de posicion libre de la primera posicion
		{
			return false;
		}
		return true;
	}
	
	public static boolean posicionValida(int posX, int posY, int[][] matriz)
	{
		if ((posX < 0) || (posX >= matriz.length) || (posY < 0) || (posY >= matriz.length))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public static boolean posicionLibre(int posX, int posY, int[][] matriz)
	{
		if (matriz[posX][posY] == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean vecinosDiferentes(int posX, int posY, int[][] matriz, int tipo
		, boolean esPrimero, boolean haciaAbajo)
	{
		if (haciaAbajo == true)
		{
			//Arriba
			if (esPrimero == true)
			{
				if (posicionValida(posX - 1, posY, matriz) == true)
				{
					if (matriz[posX - 1][posY] == tipo)
					{
						return false;
					}
				}
			}
			//Izquierda
			if (posicionValida(posX, posY - 1, matriz) == true)
			{
				if (matriz[posX][posY - 1] == tipo)
				{
					return false;
				}
			}
			//Derecha
			if (posicionValida(posX, posY + 1, matriz) == true) 
			{
				if (matriz[posX][posY + 1] == tipo)
				{
					return false;
				}
			}
			//Abajo
			if (posicionValida(posX + 1, posY, matriz) == true)
			{
				if (matriz[posX + 1][posY] == tipo)
				{
					return false;
				}
			}
		}
		else // de construccion hacia la derecha
		{
			//Izquierda
			if (esPrimero == true)
			{
				if (posicionValida(posX, posY - 1, matriz) == true)
				{
					if (matriz[posX][posY - 1] == tipo)
					{
						return false;
					}
				}
			}
			//Arriba
			if (posicionValida(posX - 1, posY, matriz) == true)
			{
				if (matriz[posX - 1][posY] == tipo)
				{
					return false;
				}
			}
			//Derecha
			if (posicionValida(posX, posY + 1, matriz) == true) 
			{
				if (matriz[posX][posY + 1] == tipo)
				{
					return false;
				}
			}
			//Abajo
			if (posicionValida(posX + 1, posY, matriz) == true)
			{
				if (matriz[posX + 1][posY] == tipo)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	
	 public static int posXRandom(int tamanoBarco,  int tamannoMatriz)
	{
		int numero = (int) (Math.random() * (tamannoMatriz - tamanoBarco));
		
		return numero;
	}
	
	public static int posYRandom(int tamanoBarco, int tamannoMatriz)
	{
		int start = (int) (Math.random() * (tamannoMatriz - tamanoBarco));
		
		return start;
	}
	
	public static boolean direccion()
	{
		return Math.random() < 0.5;
	}
	
	
	public static int[][] obtenerMatrizPC()
	{
		return capaDatos.obtenerMatrizPC();
	}
	
	public static int[][] obtenerMatrizJugador()
	{
		return capaDatos.obtenerMatrizJugador();
	}
	
	public static  void OpcionUsuario (int tamanno, int cantidadDeBarcos)
	{
		capaDatos.RegistrarMatrizPC(llenarMatrizJugador(tamanno,cantidadDeBarcos));
	}
	
	// Si quienAtaca == true, ataca el jugador, sino, ataca la PC
	public static void VerificarAtaque(int x, int y, boolean quienAtaca, int[][] matrizRayar)
	{
		int[][] matrizAtacada;
		if (quienAtaca)
		{
			matrizAtacada = obtenerMatrizPC();
		}
		else 
		{
			matrizAtacada = obtenerMatrizJugador();
		}
		int valorEnTablero = matrizAtacada[x][y]; 
		int valorParaBuscar = 1;
		int largoBarco = 0;
		
		int xInicial = x;
		int yInicial = y; 
		
		switch(valorEnTablero)
		{
			case 1: 
				largoBarco = 4;
				break;
			case 2: 
				largoBarco = 3;
				break;
			case 3: 
				largoBarco = 2;
				break;
			case 9:  
				largoBarco = -1;
				break;
			case 0:
			{
				matrizRayar[x][y] = 8;
				largoBarco = -1;
				break;
			}
				
		}
		
		
		if (largoBarco != -1)
		{
			matrizAtacada[x][y] = 9;
					
			matrizAtacada = verificarY (matrizAtacada, x, y, yInicial, largoBarco, valorEnTablero, quienAtaca, matrizRayar);
			 
			matrizAtacada = verificarX (matrizAtacada, x, y, xInicial, largoBarco, valorEnTablero, quienAtaca, matrizRayar); 	
			
		}
			
		if (quienAtaca)
		{
			capaDatos.RegistrarMatrizPC(matrizAtacada);
		}
		else 
		{
			capaDatos.registrarAtaquePC(matrizAtacada);
		}	
		
	}
	
	public static int[][] verificarY ( int[][] matriz, int x, int y, int yInicial, int largoBarco, int valorEnTablero, boolean quienAtaca, int[][] matrizRayar)
	{
		
		boolean seguir = true;
		int cont = 0;
		boolean DireccionContador  = true;
		if (yInicial  == 0)
		{
			while (seguir == true && cont <= largoBarco)
			{
				if ((matriz[x][y] ==  valorEnTablero || matriz[x][y] == 9) && y <= 9 )
				{
					matriz[x][y] = 9;
					matrizRayar[x][y] = 9;
					cont++;
					y++;
				}
				else 
				{
					seguir = false;
				}
			}
		}
		
		
		if (yInicial  == 9)
		{
			seguir = true;
			y = yInicial;
			while (seguir == true && cont <= largoBarco)
			{
				if ((matriz[x][y] ==  valorEnTablero || matriz[x][y] == 9) && y >= 0)
				{
					matriz[x][y] = 9;
					matrizRayar[x][y] = 9;
					cont++;
					y--;
				}
				else 
				{
					seguir = false;
				}
			}
		}
		
		if (yInicial > 0 && yInicial <9)
		{
			int contCiclado = 0;
			seguir = true;
			y = yInicial;
			while (seguir == true && cont <= largoBarco)
			{
				contCiclado++;
				if ((matriz[x][y] ==  valorEnTablero || matriz[x][y] == 9) && DireccionContador == true)
				{
					if (y > 0 &&  y <=9)
					{
						matriz[x][y] = 9;
						matrizRayar[x][y] = 9;
						cont++;
						y--;
					}
					
					else 
					{
						matriz[x][y] = 9;
						matrizRayar[x][y] = 9;
						cont++;
						DireccionContador = false;
					}
				}
				else if ( DireccionContador == true)
				{
					DireccionContador = false;
					y = yInicial + 1;
				}
	
				if ((matriz[x][y] ==  valorEnTablero || matriz[x][y] == 9) && (y >= 0 && y <9) && DireccionContador == false)
				{
					matriz[x][y] = 9;
					matrizRayar[x][y] = 9;
					cont++;
					y++;
						
				}
				else if (contCiclado > 10) 
				{
					seguir = false;
				}
				
			}
		}
		
		if (cont >= largoBarco)
		{
			// Si quienAtaca == true, atacó el jugador, sino, atacó la PC
			SumarBarcosDestruidos(valorEnTablero, quienAtaca);
		}
		
		return matriz;
	}
	
	public static int[][] verificarX ( int[][] matriz, int x, int y, int xInicial, int largoBarco, int valorEnTablero, boolean quienAtaca, int[][] matrizRayar)
	{
		boolean DireccionContador  = true;
		boolean seguir = true;
		int cont = 0;
		if (xInicial  == 0)
		{
			while (seguir == true && cont <= largoBarco)
			{
				if ((matriz[x][y] ==  valorEnTablero || matriz[x][y] == 9) && x <= 9)
				{
					matriz[x][y] = 9;
					matrizRayar[x][y] = 9;
					cont++;
					x++;
				}
				else 
				{
					seguir = false;
				}
			}
		}
		
		
		if (xInicial  == 9)
		{
			seguir = true;
			x = xInicial;
			while (seguir == true && cont <= largoBarco)
			{
				if ((matriz[x][y] ==  valorEnTablero || matriz[x][y] == 9) && x >= 0)
				{
					matriz[x][y] = 9;
					matrizRayar[x][y] = 9;
					cont++;
					x--;
				}
				else 
				{
					seguir = false;
				}
			}
		}
		
		if (xInicial > 0 && xInicial <9)
		{
			int contCiclado = 0;
			seguir = true;
			x = xInicial;
			while (seguir == true && cont <= largoBarco)
			{
				contCiclado++;
				if ((matriz[x][y] ==  valorEnTablero || matriz[x][y] == 9)  && DireccionContador == true)
				{
					if (x > 0 && x <=9)
					{
						matriz[x][y] = 9;
						matrizRayar[x][y] = 9;
						cont++;
						x--;
					}
					else 
					{
						matriz[x][y] = 9;
						matrizRayar[x][y] = 9;
						cont++;
						DireccionContador = false;
					}
					

				}
				else if ( DireccionContador == true)
				{
					DireccionContador = false;
					x = xInicial + 1;
				}
				
				
				
				if ((matriz[x][y] ==  valorEnTablero || matriz[x][y] == 9) && (x >= 0 && x <9) && DireccionContador == false)
				{
					matriz[x][y] = 9;
					matrizRayar[x][y] = 9;
					cont++;
					x++;
				}
				else if ( contCiclado > 10) 
				{
					seguir = false;
				}
				
			}
		}
		
		if (cont >= largoBarco)
		{
			SumarBarcosDestruidos(valorEnTablero, quienAtaca);
		}
		
		
		return matriz;
	}
	
	
	public static void AtacarPC(boolean LargoMatriz)
	{
		int x = 0;
		int y = 0;
		//Esta matriz unicamente la envio para que el metodo de Verificar ataque, funcione
		int tamano = 0;
		
		if (LargoMatriz)
		{
			x = (int) (Math.random() * (10));
			y = (int) (Math.random() * (10));
			tamano = 10;
		}
		else
		{
			x = (int) (Math.random() * (20));
			y = (int) (Math.random() * (10));
			tamano = 20;
		}
		
		int[][] matrizParaRelleno = new int[tamano][tamano];
		
		VerificarAtaque(x, y, false, matrizParaRelleno);
	}
	
	//Retorna una matriz que contiene la cantidad de barcos destruidos y de tipo
	public static void SumarBarcosDestruidos(int tipoBarco, boolean quienAtaca)
	{
		if (quienAtaca)
		{
			barcosPC(tipoBarco);
		}
		else
		{
			barcosJugador(tipoBarco);
		}
		
	}
	
	public static int[] barcosPC(int tipoBarco)
	{
		switch(tipoBarco)
		{
			case 1:
			{
				Barco1PC++;
				arregloDestruidosPC[0] = Barco1PC;
				break;
			}
			case 2:
			{
				Barco2PC++;
				arregloDestruidosPC[1] = Barco2PC;
				break;
			}
			case 3:
			{
				Barco3PC++;
				arregloDestruidosPC[2] = Barco3PC;
				break;
			}
			case 0:
				break;
		}
		
		return arregloDestruidosPC;
	}
	public static int[] barcosJugador(int tipoBarco)
	{
		switch(tipoBarco)
		{
			case 1:
			{
				Barco1Jugador++;
				arregloDestruidosJugador[0] = Barco1Jugador;
				break;
			}
			case 2:
			{
				Barco2Jugador++;
				arregloDestruidosJugador[1] = Barco2Jugador;
				break;
			}
			case 3:
			{
				Barco3Jugador++;
				arregloDestruidosJugador[2] = Barco3Jugador;
				break;
			}
			case 0:
				break;
		}
		return arregloDestruidosJugador;
	}
	
	public static int quienVaGanando()
	{
		int sumaPc = 0;
		int sumaJugador = 0;
		
		int[] DestruidosJugador = barcosJugador(0);
		int[] DestruidosPC = barcosPC(0);
		
		
		for( int i = 0; i < arregloDestruidosPC.length; i++)
		{
			sumaJugador = DestruidosJugador[i] + sumaJugador;
			sumaPc = DestruidosPC[i] + sumaPc;
		}
		int QuienVaGanando = 0;
		//Si el jugador va ganando emvia un 1 
		if (sumaJugador == sumaPc)
		{
			QuienVaGanando = 0;
		}
		else if (sumaJugador > sumaPc)
		{
			QuienVaGanando = 1;
		}
		else
		{
			QuienVaGanando = 2;
		}
		
		return  QuienVaGanando;
	}	
}