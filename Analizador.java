
// Implemented by Rafael Pernil Bronchalo
// I hope it works fine :)

import java.util.ArrayList;
import java.util.List;

public class Analizador {

	public static void main(String arg[]) {

		ListaTamObservable nObs = new ListaTamObservable();
		ListaTamObserver uno = new UnoListaObserver();
		ListaTamObserver n = new NListaObserver();
		ListaTamObserver logn = new LogNListaObserver();
		ListaTamObserver nlogn = new NLogNListaObserver();
		ListaTamObserver n2 = new N2ListaObserver();
		ListaTamObserver n3 = new N3ListaObserver();
		ListaTamObserver exp2n = new Exp2NListaObserver();
		ListaTamObserver nf = new NFListaObserver();

		nObs.addObserver(n);
		nObs.addObserver(uno);
		nObs.addObserver(logn);
		nObs.addObserver(nlogn);
		nObs.addObserver(n2);
		nObs.addObserver(n3);
		nObs.addObserver(exp2n);
		nObs.addObserver(nf);

		// Entradas significantes para NF
		nObs.addTamEntrada(8);
		nObs.addTamEntrada(9);
		nObs.addTamEntrada(10);
		nObs.addTamEntrada(11);

		// Entradas significativas para 2N
		nObs.addTamEntrada(20);
		nObs.addTamEntrada(30);
		nObs.addTamEntrada(40);

		// Entradas significativas para N3
		nObs.addTamEntrada(50);
		nObs.addTamEntrada(100);
		nObs.addTamEntrada(150);
		nObs.addTamEntrada(200);
		nObs.addTamEntrada(500);
		nObs.addTamEntrada(1000);

		// Entradas significativas para N2
		nObs.addTamEntrada(2000);
		nObs.addTamEntrada(3000);
		nObs.addTamEntrada(5000);
		nObs.addTamEntrada(10000);

		// Entradas significativas para NLogN
		nObs.addTamEntrada(20000);
		nObs.addTamEntrada(500000);
		nObs.addTamEntrada(1000000);
		nObs.addTamEntrada(2000000);
		nObs.addTamEntrada(5000000);

		// Entradas significativas para N
		nObs.addTamEntrada(10000000);
		nObs.addTamEntrada(20000000);
		nObs.addTamEntrada(50000000);
		nObs.addTamEntrada(100000000);
		nObs.addTamEntrada(1000000000);

		// Entrada significativa para LogN y 1
		nObs.addTamEntrada(Long.MAX_VALUE);
		nObs.addTamEntrada(Long.MAX_VALUE);
		nObs.addTamEntrada(Long.MAX_VALUE);
		nObs.addTamEntrada(Long.MAX_VALUE);
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

		boolean acabado = false;

		/**
		 * Estadisticas de ejecución por cada nIter de cada tamaño de entrada
		 */
		ExecutionStatsObservable stats = new ExecutionStatsObservable();

		// Lista de razones
		ListaRazonObserver razon1 = new ListaRazonObserver(uno);
		ListaRazonObserver razonLogN = new ListaRazonObserver(logn);
		ListaRazonObserver razonNLogN = new ListaRazonObserver(nlogn);
		ListaRazonObserver razonN = new ListaRazonObserver(n);
		ListaRazonObserver razonN2 = new ListaRazonObserver(n2);
		ListaRazonObserver razonN3 = new ListaRazonObserver(n3);
		ListaRazonObserver razon2N = new ListaRazonObserver(exp2n);
		ListaRazonObserver razonNF = new ListaRazonObserver(nf);

		stats.addObserver(razon1);
		stats.addObserver(razonLogN);
		stats.addObserver(razonNLogN);
		stats.addObserver(razonN);
		stats.addObserver(razonN2);
		stats.addObserver(razonN3);
		stats.addObserver(razon2N);
		stats.addObserver(razonNF);

		Thread[] hilos = new Thread[nIter];
		while (!acabado && i < nObs.getLista().size()) {

			for (int j = 0; j < nIter; j++) {
				hilos[j] = new Thread(new AlgoritmoThread(stats, nObs.getLista().get(i)));

				/**
				 * Nota importante:
				 * 
				 * start() y join() se ejecutan secuencial mente porque la clase Algoritmo es
				 * synchronized y no se miden bien los tiempos. El codigo esta listo para
				 * mediciones thread-safe concurrentes
				 * 
				 */
				hilos[j].start();

				try {
					hilos[j].join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			/**
			 * Caso alternativo con concurrencia:
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
			stats.calcularMinimo();

			if (razon1.getListaRazones().size() > 1 && Util.varianza(razon1.getListaRazones()) <= 3e5) {
				limite = "1";
			} else if (razonLogN.getListaRazones().size() > 1 && Util.varianza(razonLogN.getListaRazones()) <= 1e6) {
				limite = "LogN";
			} else if (razonN.getListaRazones().size() > 1 && Util.varianza(razonN.getListaRazones()) <= 5e4) {
				limite = "N";
			} else if (razonNLogN.getListaRazones().size() > 1 && Util.varianza(razonNLogN.getListaRazones()) <= 3e4) {
				limite = "NLogN";
			} else if (razonN2.getListaRazones().size() > 1 && Util.varianza(razonN2.getListaRazones()) <= 1e4) {
				limite = "N2";
			} else if (razonN3.getListaRazones().size() > 1 && Util.varianza(razonN3.getListaRazones()) <= 1e3) {
				limite = "N3";
			} else if (razon2N.getListaRazones().size() > 1 && Util.varianza(razon2N.getListaRazones()) <= 1e4) {
				limite = "2N";
			} else if (razonNF.getListaRazones().size() > 1 && Util.varianza(razonNF.getListaRazones()) <= 1e2) {
				limite = "NF";
			}

			if (stats.getMedia() * nIter >= 9e8) {
				acabado = true;
			}
			tiempos.add(stats.getListaTiempo());
			stats.resetStats();
			i++;

		}

		System.out.println(limite);

	}
}
