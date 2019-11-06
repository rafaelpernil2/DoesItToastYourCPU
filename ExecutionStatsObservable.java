import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class ExecutionStatsObservable extends Observable {
    private long sum;
    private List<Long> listaTiempos;
    private Object sumLock;
    private Object listaTiemposLock;

    public ExecutionStatsObservable() {
        // Inicializacion de objetos de sincronizacion
        this.sumLock = new Object();
        this.listaTiemposLock = new Object();
        resetStats();
    }

    public void calcularMedia() {
        synchronized (listaTiemposLock) {
            synchronized (sumLock) {
                long media = getMedia();
                Map<String, Object> datos = new HashMap<>();
                datos.put("Stat", new Long(media));
                setChanged();
                notifyObservers(datos);
            }
        }
    }

    public void calcularMinimo() {
        synchronized (listaTiemposLock) {
            synchronized (sumLock) {
                if (!listaTiempos.isEmpty()) {
                    long min = getMinimo();
                    Map<String, Object> datos = new HashMap<>();
                    datos.put("Stat", new Long(min));
                    setChanged();
                    notifyObservers(datos);
                }
            }
        }
    }

    public void addTiempo(long tiempo) {
        synchronized (sumLock) {
            this.sum += tiempo;
        }
        synchronized (listaTiemposLock) {
            this.listaTiempos.add(new Long(tiempo));
        }
    }

    public List<Long> getListaTiempo() {
        synchronized (listaTiemposLock) {
            return listaTiempos;
        }
    }

    public void resetStats() {
        synchronized (sumLock) {
            this.sum = 0;
        }
        synchronized (listaTiemposLock) {
            this.listaTiempos = new ArrayList<>();
        }
    }

    public long getMinimo() {
        return Collections.min(listaTiempos);
    }

    public long getMedia() {
        long media = 0;
        if (listaTiempos.size() > 0) {
            media = sum / listaTiempos.size();
        }
        return media;
    }
}