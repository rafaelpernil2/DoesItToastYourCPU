import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public abstract class ListaTamObserver implements Observer {

    private List<Long> listaTamEntrada;

    public abstract long transformarValor(long valor);

    public ListaTamObserver() {
        this.setLista(new ArrayList<Long>());
    }

    public List<Long> getLista() {
        return listaTamEntrada;
    }

    public void setLista(List<Long> listaTamEntrada) {
        this.listaTamEntrada = listaTamEntrada;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Map<?, ?>) {
            Map<?, ?> map = (Map<?, ?>) arg;

            if (map.containsKey("TamEntrada")) {

                long aux = (Long) map.get("TamEntrada");
                // Actualizar lista

                // Variables auxiliares
                List<Long> auxList = getLista();

                // Modificar valor
                long auxElem = transformarValor(aux);
                auxList.add(new Long(auxElem));
                // setChanged();
                // notifyObservers(auxElem);

                // Map<String, Object> datos = new HashMap<>();
                // datos.put("TamEntrada", new Long(auxElem));
                // setChanged();
                // notifyObservers(datos);

                // Reasignar lista
                setLista(auxList);
                // System.out.println("INNER" + getLista());
            }

        }

    }

}
