import java.util.Scanner;


class GUI
{
	static Scanner in = new Scanner (System.in);
	static Logica capaLogica = new Logica();
	static int contFacil = 0;
		
	public static void main (String[] args)
	{
		leerOpcion();
	}
	
	
	public static void ImprimirMenu()
	{
		System.out.println("**** M E N U ****");
		System.out.println("------------------");
		System.out.println("1.Facil");
		System.out.println("2.Dificil");
		System.out.println("3.Salir");
		System.out.print("Ingrese su opción: ");	
	}
	
	public static void leerOpcion()
	{
		int opcion = 0;
		do 
		{
		
			ImprimirMenu();
			
			opcion = in.nextInt();
			
				switch(opcion)
				{
					case 1:
					{
						JugarFacil();
						break;
					}
						
					case 2:
					{
						JugarDificil();
						break;
					}
					case 3:
						System.out.println ("Gracias por jugar!");
						break;
				}
		}while (opcion != 3);
	}
	
	
	public static void imprimirMatrices(int[][] matrizJugador, int[][] matrizPC, boolean tamanho )
	{
		int posicionesAImprimir =0;
		if (tamanho)
		{
			System.out.print("Tablero Jugador");
			System.out.print("          ");
			System.out.println("Tablero Computadora");
			System.out.print("                            ");
			System.out.println ("[0][1][2][3][4][5][6][7][8][9]");
		}
		else
		{
			System.out.print("Tablero Jugador");
			System.out.print("                              ");
			System.out.println("Tablero Computadora");
			System.out.print("                                                  ");
			System.out.println ("0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19");
			System.out.print("                                                 ");
			System.out.println (" ----------------------------------------------------------");
		}
		
		for (int x = 0; x< matrizJugador.length; x++)
		{
			for (int y = 0; y< matrizJugador[0].length; y++)
			{	
				
				System.out.print(matrizJugador[x][y]+" ");
			}
			
			System.out.print ("     ");
			System.out.print("["+posicionesAImprimir+"] ");
			if (!tamanho)
			{
				if (posicionesAImprimir < 10)
				{
					System.out.print (" ");
				}
			}
			
			posicionesAImprimir++;
			for (int y = 0; y< matrizPC[0].length; y++)
			{	
				
				System.out.print(matrizPC[x][y]+"  ");
			}
			System.out.println("");
		}
		
		
	}
	
	public static void menuTXT()
	{
		System.out.println("");
		System.out.println("");
		System.out.println ("1. Matriz de 10 x 10.");
		System.out.println ("2. Matriz de 20 x 20.");
		System.out.print("Ingrese su opcion: ");
	}
	
	
	//Lee y devuelve la matriz 10x10 para ser imprimida
	public static int[][] txtFacil()
	{
		if (contFacil< 1)
		{
			String path = "D:/Proyecto/10x10.txt";
			capaLogica.MatrizLeida(10, path);
		}
		
		int[][] MatrizLlena = capaLogica.obtenerMatrizJugador();		
		
		contFacil++;
		
		return MatrizLlena;
		
	}
	
	//Lee y devuelve la matriz 20x20 para ser imprimida
	public static int[][] txtDificil()
	{
		String path = "D:/Proyecto/20x20.txt";
		capaLogica.MatrizLeida(20, path);
		int[][] MatrizLlena = capaLogica.obtenerMatrizJugador();
		
		
		return MatrizLlena;
		
	}
	
	public static void JugarFacil()
	{
		
		capaLogica.OpcionUsuario(10, 2);
		int opcion;
		int [][] matrizEnBlanco = new int[10][10];
		do 
		{
			
			imprimirMatrices(txtFacil(), matrizEnBlanco, true );
			menuEnJuego();
			opcion = in.nextInt();
			
			switch(opcion)
			{
				case 1: 
				{
					opcionesAtaque(true, matrizEnBlanco);
					break;
				}
				case 2:
				{
					Estadisticas(false);
					break;
				}
				case 3:
				{
					Estadisticas(true);
					break;
				}
			}
			
			// pregunta la pc si ya ganó
			if (comprobarGane(6, true))
			{
				System.out.print("La Computadora ganó.");
				System.out.println("");
				opcion = 3;
			}
			//pregunta si el jugador ganó
			else if (comprobarGane(6, false))
			{
				System.out.print("El jugador ganó.");
				System.out.println("");
				opcion = 3;
			}
			
			
		} while(opcion != 3);
	}
	
	public static void menuEnJuego()
	{
		System.out.println (" ");
		System.out.println ("1. Atacar");
		System.out.println ("2. Estadisticas");
		System.out.println("3. Abandonar Juego.");
		System.out.print ("Ingrese su opcion: ");
	}
	
	public static void opcionesAtaque(boolean tamanno, int[][] matrizRayada)
	{
		int PosicionX = 0;
		int Posiciony = 0;
		if (tamanno)
		{
			System.out.println("Seleccione una opcion del 0 al 9 vertical: ");
			PosicionX = in.nextInt();
			System.out.println("Seleccione una opcion de 0 a 9 horizontal: ");
			Posiciony = in.nextInt();
		}
		else
		{
			System.out.println("Seleccione una opcion del 0 al 19 vertical: ");
			PosicionX = in.nextInt();
			System.out.println("Seleccione una opcion de 0 a 19 horizontal: ");
			Posiciony = in.nextInt();
		}
		
		
		
		capaLogica.VerificarAtaque(PosicionX, Posiciony, true, matrizRayada);
		
		capaLogica.AtacarPC(tamanno);
		
	}
	
	public static void Estadisticas(boolean rendirse)
	{
		if (rendirse)
		{
			if(capaLogica.quienVaGanando() == 1)
			{
				System.out.println(" * * * * * * * * * * * ");
				System.out.println("La computadora ganó.");
				System.out.println("Gracias por jugar!");
			}
			else if (capaLogica.quienVaGanando() == 2)
			{
				System.out.println(" * * * * * * * * * * * ");
				System.out.println("El jugador ganó.");
				System.out.println("Gracias por jugar!");
			}
			else if (capaLogica.quienVaGanando() == 0)
			{
				System.out.println(" * * * * * * * * * * * ");
				System.out.println("Empates.");
				System.out.println("Gracias por jugar!");
			}
		}
		else
		{
			int[] BarcosDestruidosDeLaPc = capaLogica.barcosPC(0);
			int[] BarcosDestruidosDeLJugador = capaLogica.barcosJugador(0);
			
			System.out.println("La computadora ha destruido "+BarcosDestruidosDeLJugador[0]+" barcos grandes y el jugador ha destruido "+BarcosDestruidosDeLaPc[0]+" barcos grandes." );
			System.out.println("La computadora ha destruido "+BarcosDestruidosDeLJugador[1]+" barcos medianos y el jugador ha destruido "+BarcosDestruidosDeLaPc[1]+" barcos medianos." );
			System.out.println("La computadora ha destruido "+BarcosDestruidosDeLJugador[2]+" barcos pequeños y el jugador ha destruido "+BarcosDestruidosDeLaPc[2]+" barcos pequeños." );
			
			
			if(capaLogica.quienVaGanando() == 1)
			{
				System.out.println(" * * * * * * * * * * * ");
				System.out.println("La computadora va ganando.");
			}
			else if (capaLogica.quienVaGanando() == 2)
			{
				System.out.println(" * * * * * * * * * * * ");
				System.out.println("El jugador va ganando.");
			}
			else if (capaLogica.quienVaGanando() == 0)
			{
				System.out.println(" * * * * * * * * * * * ");
				System.out.println("Empates.");
			}
			
			System.out.println(" ");
			System.out.println(" ");
			System.out.println(" ");
			
			
		}
		
		
	}
	
	
	public static void JugarDificil()
	{

		capaLogica.OpcionUsuario(20, 4);
		int opcion;
		int [][] matrizEnBlanco = new int[20][20];
		do 
		{
			imprimirMatrices(txtDificil(),matrizEnBlanco, false);
			menuEnJuego();
			opcion = in.nextInt();
			
			switch(opcion)
			{
				case 1: 
				{
					opcionesAtaque(false, matrizEnBlanco);
					break;
				}
				case 2:
				{
					Estadisticas(false);
					break;
				}
				case 3:
				{
					Estadisticas(true);
					break;
				}
			}
			
			// pregunta la pc si ya ganó
			if (comprobarGane(12, true))
			{
				System.out.print("La Computadora ganó.");
				System.out.println("");
				opcion = 3;
			}
			//pregunta si el jugador ganó
			else if (comprobarGane(12, false))
			{
				System.out.print("El jugador ganó.");
				System.out.println("");
				opcion = 3;
			}
			
			
			
		} while(opcion != 3);
	}
	
	
	
	// SI quienPregunta es true, compruebo si ya ganó la PC
	public static boolean comprobarGane(int tamano, boolean quienPregunta)
	{
		boolean ganó = false;
		int[] jugador= capaLogica.arregloDestruidosJugador;
		int[] pc = capaLogica.arregloDestruidosPC; 
		if(quienPregunta)
		{
			int suma = 0;
			for( int i = 0; i < jugador.length; i++)
			{
				suma = suma +jugador[i];
			}
			
			if (suma > tamano)
			{
				ganó = true;
					
			}
		}
		else
		{
			int suma = 0;
			for( int i = 0; i < pc.length; i++)
			{
				suma = suma +pc[i];
			}
			
			if (suma > tamano)
			{
				ganó = true;
					
			}
			
		}
			
		
		return ganó;
	}
}