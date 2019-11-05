import java.util.concurrent.Semaphore;

public class TemporizadorConcurrente {
    private Semaphore semReiniciar;
    private Semaphore semParar;
    private Temporizador temporizador;
    private boolean iniciado;

    public TemporizadorConcurrente() {
        this.semReiniciar = new Semaphore(1);
        this.semParar = new Semaphore(1);
        this.temporizador = new Temporizador();
        this.iniciado = false;
    }

    public void reiniciar() {
        try {
            if (!iniciado) {
                semReiniciar.acquire();
                temporizador.reiniciar();
                temporizador.iniciar();
                iniciado = true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semReiniciar.release();
        }

    }

    public void parar() {
        try {
            if (iniciado) {
                semParar.acquire();
                temporizador.parar();
                iniciado = false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semParar.release();
        }
    }

    public long tiempoPasado() {
        long res = 0;
        if (!iniciado)
            res = temporizador.tiempoPasado();
        return res;
    }
}