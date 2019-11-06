public class LogNListaObserver extends ListaTamObserver {
   

    @Override
    public long transformarValor(long valor) {
        return Util.log2(valor);

    }

}