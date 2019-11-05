public class N3ListaObserver extends ListaObserver {

    @Override
    public long transformarValor(long valor) {
        return valor * valor * valor;

    }
    
}