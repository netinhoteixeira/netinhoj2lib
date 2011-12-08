package info.netinho.util;

import java.nio.charset.Charset;
import java.util.Random;

public class RandomString {

    public static String randomString(int lo, int hi, char start, char end) {
        int n = rand(lo, hi);
        byte[] b = new byte[n];
        for (int i = 0; i < n; i++) {
            b[i] = (byte) rand(start, end);
        }

        return new String(b, Charset.defaultCharset());
    }

    public static String randomString() {
        return randomString(5, 25, 'a', 'z');
    }

    private static int rand(int lo, int hi) {
        Random rn = new Random();
        int n = hi - lo + 1;
        int i = rn.nextInt() % n;
        if (i < 0) {
            i = -i;
        }
        return lo + i;
    }
}