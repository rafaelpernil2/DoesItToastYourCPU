import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("unchecked")
public class RazonObserver implements Observer {

    private ListaObserver listaObs;
    private List<Long> listaRazones;
    private int indice;

    public RazonObserver(ListaObserver lista) {
        this.listaObs = lista;
        this.indice = 0;
        this.listaRazones = new ArrayList<>();
    }

    @Override
    public void update(Observable o, Object arg) {

        if (arg instanceof Map<?, ?>) {
            Map<?, ?> map = (Map<?, ?>) arg;

            if (map.containsKey("Media")) {
                Long media = (Long) map.get("Media");
                List<Long> listaRazones = getListaRazones();
                listaRazones.add(media / listaObs.getLista().get(indice));
                setListaRazones(listaRazones);
                indice++;
            }


        } else if (arg instanceof List<?>) {
            System.out.println("ENTRA AQUI");
            setListaRazones((List<Long>) arg);
        }

    }

    public List<Long> getListaRazones() {
        return listaRazones;
    }

    public void setListaRazones(List<Long> listaRazones) {
        this.listaRazones = listaRazones;
    }

    public ListaObserver getListaObs() {
        return listaObs;
    }

    public void setListaObs(ListaObserver listaObs) {
        this.listaObs = listaObs;
    }

}