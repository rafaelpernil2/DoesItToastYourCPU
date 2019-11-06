import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class ListaRazonObserver implements Observer {

    private ListaTamObserver listaObs;
    private List<Long> listaRazones;
    private int indice;

    public ListaRazonObserver(ListaTamObserver lista) {
        this.listaObs = lista;
        this.indice = 0;
        this.listaRazones = new ArrayList<>();
    }

    @Override
    public void update(Observable o, Object arg) {

        if (arg instanceof Map<?, ?>) {
            Map<?, ?> map = (Map<?, ?>) arg;

            if (map.containsKey("Stat")) {
                Long stat = (Long) map.get("Stat");
                List<Long> listaRazones = getListaRazones();
                listaRazones.add(stat / listaObs.getLista().get(indice));
                setListaRazones(listaRazones);
                indice++;
            }
        }

    }

    public List<Long> getListaRazones() {
        return listaRazones;
    }

    public void setListaRazones(List<Long> listaRazones) {
        this.listaRazones = listaRazones;
    }

    public ListaTamObserver getListaObs() {
        return listaObs;
    }

    public void setListaObs(ListaTamObserver listaObs) {
        this.listaObs = listaObs;
    }

}