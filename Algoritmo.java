
public class Algoritmo {
    static long l = 0L;

    public synchronized static void f(long n) {

        // for (int k = 0; k < n; k++) {
            for (int j = 0; j < n; j++) {
                for (int i = 0; i < n; i++) {
                    l += 1L;
                }
            }
        // }

    }
}