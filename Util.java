import java.util.Iterator;
import java.util.List;

public class Util {

    public static long varianza(List<Long> lista) {
        long acc = 0;
        long media = media(lista);

        Iterator<Long> it = lista.iterator();

        while (it.hasNext()) {
            long num = (long) it.next();
            acc += (long) Math.pow(Math.abs(num - media), 2);
        }

        return acc / lista.size();
    }

    public static long media(List<Long> lista) {
        long acc = 0;
        Iterator<Long> it = lista.iterator();

        while (it.hasNext()) {
            long num = (long) it.next();
            acc += num;
        }

        return acc / lista.size();
    }

    public static long factorial(long x) {
        // Bloquear overflow de long
        long factorial;
        if (x <= 20) {
            factorial = 1;
            while (x != 0) {
                factorial *= x;
                x--;
            }
        } else {
            factorial = Long.MAX_VALUE;
        }
        return factorial;
    }

    public static long log2(long x) {
        return (long) (Math.log(x) / Math.log(2));
    }
}