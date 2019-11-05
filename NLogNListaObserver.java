public class NLogNListaObserver extends ListaObserver {

    // Utils
    public static long log2(long x) {
        return (long) (Math.log(x) / Math.log(2));
    }

    @Override
    public long transformarValor(long valor) {
        return valor * log2(valor);

    }

}