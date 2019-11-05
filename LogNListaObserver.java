public class LogNListaObserver extends ListaObserver {

    // Utils
    public static long log2(long x) {
        return (long) (Math.log(x) / Math.log(2));
    }

    @Override
    public long transformarValor(long valor) {
        return log2(valor);

    }

}