import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class ListaTamObservable extends Observable {
   private List<Long> listaTamEntrada;

   public ListaTamObservable() {
      this.listaTamEntrada = new ArrayList<Long>();
   }

   public void addTamEntrada(long n) {
      listaTamEntrada.add(n);
      // Notifica a los observers de cambio
      Map<String,Object> datos = new HashMap<>();
      datos.put("TamEntrada",new Long(n));
      setChanged();
      notifyObservers(datos);
   }

   public List<Long> getLista() {
      return listaTamEntrada;
   }

}