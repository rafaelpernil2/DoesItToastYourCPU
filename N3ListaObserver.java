public class N3ListaObserver extends ListaTamObserver {

    @Override
    public long transformarValor(long valor) {
        return valor * valor * valor;

    }
    
}