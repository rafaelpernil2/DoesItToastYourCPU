public class Exp2NListaObserver extends ListaTamObserver {

    @Override
    public long transformarValor(long valor) {
        return (long) Math.pow(2, valor);

    }

}