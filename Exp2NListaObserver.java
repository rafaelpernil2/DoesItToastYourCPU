public class Exp2NListaObserver extends ListaObserver {

    @Override
    public long transformarValor(long valor) {
        return (long) Math.pow(2, valor);

    }

}