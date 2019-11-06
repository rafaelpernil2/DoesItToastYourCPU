public class NFListaObserver extends ListaTamObserver {

    @Override
    public long transformarValor(long valor) {
        return Util.factorial(valor);

    }

}