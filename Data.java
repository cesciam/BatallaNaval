import java.io.*;
public class Data
{
	private static int[][] matrizPC;
	private static int[][] matrizJugador;
	
	static Logica capaLogica = new Logica();
	
	public static void leerTXT(int tamano, String path)
	{
       	String matriz[][] = new String[tamano][tamano];
        File archivo = null;
        FileReader Fr = null;
        BufferedReader br = null;
        try {
            archivo = new File(path);
            Fr = new FileReader(archivo.toString());
            br = new BufferedReader(Fr);
            String linea;
            String delimiter = ";";
            
            //Contador para las lineas y cambio de fila
            int numlinea=0;
            
            //Validación si existe línea
            while (((linea = br.readLine()) != null)) {
                //Guardar datos de linea en Array
                String a[]=linea.split(delimiter);
               //Bucle para poder ingresar todas las columnas del Array "a" que existan. TODAS.
                for (int i = 0; i < a.length; i++) 
                {
                    //ingresamos los datos de cada columna de "a" a la matriz.
                    //"numlinea" hace de fila, "l" es el numero de la columna.
                    matriz[numlinea][i] = a[i];
                }
                //Incremento de numero de línea.
                numlinea++;
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        
        int[][] matrizInt = new int[tamano][tamano];
    	
    	for (int x=0; x < matrizInt.length; x++)
    	{
    		for (int y = 0; y < matrizInt[0].length; y++)
    		{
    			matrizInt[x][y] = Integer.parseInt(matriz[x][y]);
    		}
    	}
    	
    	matrizJugador = matrizInt;
    	
    }
    
    public static int[][] obtenerMatrizJugador()
    {
    	return matrizJugador;
    }

    public static int[][] obtenerMatrizPC()
    {
    	return matrizPC;
    }
    
    public static void RegistrarMatrizPC (int[][] paramMatrizPc)
    {
    	matrizPC = paramMatrizPc;	
    }
    
    public static void registrarAtaquePC(int[][] matrizAtacada)
    {
    	matrizJugador = matrizAtacada;
    }
}