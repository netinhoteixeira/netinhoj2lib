package info.netinho.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    public static byte[] gerarBytes(String frase, String algoritmo) {
        try {
            MessageDigest md = MessageDigest.getInstance(algoritmo);
            md.update(frase.getBytes());
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static String gerarString(String frase, String algoritmo) {
        return toString(gerarBytes(frase, algoritmo));
    }

    private static String toString(byte[] bytes) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int parteAlta = (bytes[i] >> 4 & 0xF) << 4;
            int parteBaixa = bytes[i] & 0xF;
            if (parteAlta == 0) {
                s.append('0');
            }
            s.append(Integer.toHexString(parteAlta | parteBaixa));
        }
        return s.toString();
    }
}