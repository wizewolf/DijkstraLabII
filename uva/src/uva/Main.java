package uva;
import java.util.*;

import java.io.*;


public class Main {
	static boolean [][] elevador;
	static int[] tiemp;
	static int numElevador;

	
	
	

	// Metodo Dijkstra
	// s = elevador k= pisos
	static int dijkstra(int s, int k) {
		//crea un objeto de la clase de cola de prioridad
		PriorityQueue <ColaPrioridad> miLista = new PriorityQueue<ColaPrioridad>();
	
		// matris de los pisos y elevadores del edificio
		// filas = pisos columnas = elevadores
    		int[][] dist = new int[100][numElevador];

    		//carga los pisos con fill (a todas las distancia coloca mayor valor)
    	for (int i = 0; i < 100; i++){
			Arrays.fill(dist[i], Integer.MAX_VALUE);
		}

		    //en la posicion 0 colocamos  "0"
		    dist[0][s] = 0;
		    //añade elemento a la cola de prioridad
		    miLista.offer(new ColaPrioridad(0, 0, s));
        
		    //mientras que la cola NO esta vacia
		while (!miLista.isEmpty()) {
			//sacamos el elemento de la cola
			ColaPrioridad q = miLista.poll();
			int d = q.dist;
			int f = q.pisos;
			int e = q.elevador;
			
			//si la distancia es igual q la distancia de la matriz
			if (d == dist[f][e]) {
				if (f == k)//si el piso de la cola es igual al piso donde keremos ir
					return d;
			    
				//recorremos las filas de la matriz
				for (int i = 0; i < 100; ++i) {
					if (i == f)//salta una vez el bucle si "i" es igual al piso de la cola
						continue;
					
					//a "w" le asignamos el tiempo del elevador(e) multiplicado por(la resta del piso de la cola menos "i")
					int w = tiemp[e] * Math.abs(f - i);
					
					//si el elevador visita ese piso y la distancia del piso es mayor a la distancia del piso que deseamos ir mas el tiempo
					if (elevador[i][e] && dist[i][e] > dist[f][e] + w) {
						dist[i][e] = dist[f][e] + w; 
						miLista.offer(new ColaPrioridad(dist[i][e], i, e));//se añade a la cola
					}
				}
				
				//ahora recorremos las columnas
				for (int j = 0; j < numElevador; ++j) {
					if (j == e)// salta una vez el bucle si "j" es igual al elevador
						continue;
				
					//si el elevador visita ese piso y la distancia del piso es mayor a la distancia del piso que deseamos ir mas el tiempo mas 60seg que es por el cambio de elevador
					if (elevador[f][j] && dist[f][j] > dist[f][e] + 60) {
						dist[f][j] = dist[f][e] + 60;
						miLista.offer(new ColaPrioridad(dist[f][j], f, j));//se añade a la cola
					}
				}
			}
		}
		return Integer.MAX_VALUE;//return cuando es imposible
	}

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer stk;
		String linea;
		
		
		//asignamos a "linea"la linea de string "numero de elevador" y "numero del piso que deseamos ir"
		//si es diferente a nulo
		while ((linea = in.readLine()) != null) {
			stk = new StringTokenizer(linea); // utilizamos el StringTokenizer para separar por el espacio
			
			//transformamos el string en entero (numero de elevador)
			numElevador = Integer.parseInt(stk.nextToken());
		    //transformamos el segundo string en entero(piso que deseamos ir)
			int k = Integer.parseInt(stk.nextToken());
			
			//creamos un arreglo tiempo con la cantidad de los elevadores
			tiemp = new int[numElevador];
			//creamos una matriz con los pisos en total y la cantidad de los elevadores
			elevador = new boolean[100][numElevador]; 
			stk = new StringTokenizer(in.readLine());
			
			//inicializa el arreglo tiempo con los valores que ingresamos
			for (int i = 0; i < numElevador; ++i)
				tiemp[i] = Integer.parseInt(stk.nextToken());//transformamos el string en entero
			
			
			for (int i = 0; i < numElevador; ++i){
				stk = new StringTokenizer(in.readLine());
				  while (stk.hasMoreTokens()){ // mientras hay string disponible en el StringTokenizer
					int pisos = Integer.parseInt(stk.nextToken());//transformamos el string en entero
					elevador[pisos][i] = true;// en los pisos que el elevador se detiene coloca true
				}
			}
			
			
			int ans = Integer.MAX_VALUE;
			for (int i = 0; i < numElevador; ++i)
				if (elevador[0][i])
					ans = Math.min(ans, dijkstra(i, k));//asignamos a "ans" el valor menor de la comparacion entre ans y dijkstra 
			if (ans == Integer.MAX_VALUE)// si "ans" es max_value significa q es imposible
				System.out.println("IMPOSSIBLE");
			else// sino mostramos ans
				System.out.println(ans);
		}
		in.close();//cierre el buffer
		System.exit(0);// cierre el sistema
	}
}
// hacer una clase para crear una lista de prioridad por distancia
class ColaPrioridad implements Comparable <ColaPrioridad> {
	int dist, pisos, elevador;

	//construcctor
	public ColaPrioridad(int dist, int pisos, int elevator) {
		this.dist = dist;
		this.pisos = pisos;
		this.elevador = elevator;
	 }
	    //compareTo para que ordene de menor a mayor la distancia
 		public int compareTo(ColaPrioridad q) {
		      return this.dist - q.dist;
	    }
 }