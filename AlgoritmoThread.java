public class AlgoritmoThread implements Runnable {

    private long n;
    private ExecutionStatsObservable stats;
    private TemporizadorConcurrente t;

    public AlgoritmoThread(ExecutionStatsObservable stats, long n) {
        this.stats = stats;
        this.n = n;
        this.t = new TemporizadorConcurrente();
    }

    /**
     * This could be more sophisticated, saving the state of the provided data, n
     * and creating a thread-safe timer for making timeouts on the Algoritmo
     * executions
     */

    @Override
    public void run() {

        t.reiniciar();
        Algoritmo.f(n);
        t.parar();

        long tPasado = t.tiempoPasado();

        System.out.println("Ha terminado la ejecucion con tamaño " + n + " en " + tPasado / 1e9 + " s");
        stats.addTiempo(tPasado);
    }

}