public class NLogNListaObserver extends ListaTamObserver {

    @Override
    public long transformarValor(long valor) {
        return valor * Util.log2(valor);

    }

}