
// Implemented by Rafael Pernil Bronchalo
// I hope it works fine :)

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Analizador {

	// public static long factorial(long x) {
	// long factorial = 1;
	// while (x != 0) {
	// factorial *= x;
	// x--;
	// }
	// return factorial;
	// }

	public static void main(String arg[]) {

		ListaObservable nObs = new ListaObservable();
		ListaObserver uno = new ListaObserver() {

			@Override
			public long transformarValor(long valor) {
				return 1;
			}
		};
		ListaObserver n = new ListaObserver() {

			@Override
			public long transformarValor(long valor) {
				return valor;
			}
		};
		ListaObserver logn = new LogNListaObserver();
		ListaObserver nlogn = new NLogNListaObserver();
		ListaObserver n2 = new N2ListaObserver();
		ListaObserver n3 = new N3ListaObserver();
		ListaObserver exp2n = new Exp2NListaObserver();
		ListaObserver nf = new NFListaObserver();

		nObs.addObserver(n);
		nObs.addObserver(uno);
		nObs.addObserver(logn);
		nObs.addObserver(nlogn);
		nObs.addObserver(n2);
		nObs.addObserver(n3);
		nObs.addObserver(exp2n);
		nObs.addObserver(nf);

		Map<String, ListaObserver> observerMap = new HashMap<>();

		observerMap.put("N", n);
		observerMap.put("LogN", logn);
		observerMap.put("NLogN", nlogn);
		observerMap.put("N2", n2);
		observerMap.put("N3", n3);
		observerMap.put("2N", exp2n);
		observerMap.put("NF", nf);

		nObs.addTamEntrada(1000);
		nObs.addTamEntrada(2000);
		nObs.addTamEntrada(10000);
		nObs.addTamEntrada(20000);
		nObs.addTamEntrada(30000);
		nObs.addTamEntrada(40000);
		nObs.addTamEntrada(50000);
		nObs.addTamEntrada(60000);
		nObs.addTamEntrada(70000);
		nObs.addTamEntrada(80000);
		nObs.addTamEntrada(90000);
		nObs.addTamEntrada(100000);
		nObs.addTamEntrada(500000);
		nObs.addTamEntrada(Long.MAX_VALUE);

		// Numero de iteraciones
		int nIter = 5;

		// Limite temporal
		String limite = "";

		// Variables auxiliares
		List<List<Long>> tiempos = new ArrayList<List<Long>>();

		// //
		// MEDICIÓN //
		// //

		int i = 0;

		/**
		 * Estadisticas de ejecución por cada nIter de cada tamaño de entrada
		 */
		ExecutionStatsObservable stats = new ExecutionStatsObservable();

		// Lista de razones
		RazonObserver razon1 = new RazonObserver(uno);
		RazonObserver razonLogN = new RazonObserver(logn);
		RazonObserver razonNLogN = new RazonObserver(nlogn);
		RazonObserver razonN = new RazonObserver(n);
		RazonObserver razonN2 = new RazonObserver(n2);
		RazonObserver razonN3 = new RazonObserver(n3);
		RazonObserver razon2N = new RazonObserver(exp2n);
		RazonObserver razonNF = new RazonObserver(nf);

		stats.addObserver(razon1);
		stats.addObserver(razonLogN);
		stats.addObserver(razonNLogN);
		stats.addObserver(razonN);
		stats.addObserver(razonN2);
		stats.addObserver(razonN3);
		stats.addObserver(razon2N);
		stats.addObserver(razonNF);

		Thread[] hilos = new Thread[nIter];
		while (i < nObs.getLista().size()) {
			nObs.addTamEntrada(2000);

			for (int j = 0; j < nIter; j++) {
				hilos[j] = new Thread(new AlgoritmoThread(stats, nObs.getLista().get(i)));

				/**
				 * Important note:
				 * 
				 * start() and join() are executed sequentally because the executed Algoritmo
				 * class is synchronized and time measurements are not calculated properly. The
				 * code is ready for thread-safe concurrent measurements
				 */
				hilos[j].start();

				try {
					hilos[j].join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			/**
			 * Alternate case with concurrency:
			 */

			// for (Thread hilo : hilos) {
			// hilo.start();
			// }
			// try {
			// for (Thread hilo : hilos) {
			// hilo.join();
			// }
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }

			// Calcular media entre las nIter interaciones por cada tamano de entrada
			stats.calcularMedia();

			System.out.println("Razones respecto a LogN " + razonLogN.getListaRazones().toString());
			System.out.println("Razones respecto a NLogN " + razonNLogN.getListaRazones().toString());
			System.out.println("Razones respecto a N " + razonN.getListaRazones().toString());
			System.out.println("Razones respecto a N2 " + razonN2.getListaRazones().toString());
			System.out.println("Razones respecto a N3 " + razonN3.getListaRazones().toString());
			System.out.println("Razones respecto a 2N " + razon2N.getListaRazones().toString());
			System.out.println("Razones respecto a NF " + razonNF.getListaRazones().toString());
			if (razon1.getListaRazones().get(i) <= 1000) {
				limite = "1";
				// nObs.addTamEntrada(Long.MAX_VALUE);
			} else if (razonLogN.getListaRazones().get(i) == 0) {
				limite = "LogN";
			} else if (razonN.getListaRazones().get(i) == 0) {
				limite = "N";
			} else if (razonNLogN.getListaRazones().get(i) == 0) {
				limite = "NLogN";
			} else if (razonN2.getListaRazones().get(i) == 0) {
				limite = "N2";
				// nObs.addTamEntrada(10000);
			} else if (razonN3.getListaRazones().get(i) == 0) {
				limite = "N3";
				// nObs.addTamEntrada(2000);
			} else if (razon2N.getListaRazones().get(i) == 0) {
				limite = "2N";
			} else if (razonNF.getListaRazones().get(i) == 0) {
				limite = "NF";
			}

			tiempos.add(stats.getListaTiempo());
			stats.resetStats();
			i++;
			System.out.println(limite);
		}
	}
}
