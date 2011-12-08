package info.netinho.util;

import org.apache.commons.codec.DecoderException;

public class Base64 {

    private static byte[] MIME_DECODE_TABLE = new byte[''];

    public static byte[] toByte(String s) {
        byte[] retorno;
        if (s.charAt(s.length() - 2) == '=') {
            retorno = new byte[(s.length() / 4 - 1) * 3 + 1];
        } else {
            if (s.charAt(s.length() - 1) == '=') {
                retorno = new byte[(s.length() / 4 - 1) * 3 + 2];
            } else {
                retorno = new byte[s.length() / 4 * 3];
            }
        }
        int i = 0;

        for (int j = 0; i < s.length() - 4; j += 3) {
            byte byte0 = MIME_DECODE_TABLE[s.charAt(i)];
            byte byte4 = MIME_DECODE_TABLE[s.charAt(i + 1)];
            byte byte8 = MIME_DECODE_TABLE[s.charAt(i + 2)];
            byte byte11 = MIME_DECODE_TABLE[s.charAt(i + 3)];
            retorno[j] = (byte) (byte0 << 2 | byte4 >> 4);
            retorno[(j + 1)] = (byte) (byte4 << 4 | byte8 >> 2);
            retorno[(j + 2)] = (byte) (byte8 << 6 | byte11);
            i += 4;
        }

        if (s.charAt(s.length() - 2) == '=') {
            byte byte1 = MIME_DECODE_TABLE[s.charAt(s.length() - 4)];
            byte byte5 = MIME_DECODE_TABLE[s.charAt(s.length() - 3)];
            retorno[(retorno.length - 1)] = (byte) (byte1 << 2 | byte5 >> 4);
        } else if (s.charAt(s.length() - 1) == '=') {
            byte byte2 = MIME_DECODE_TABLE[s.charAt(s.length() - 4)];
            byte byte6 = MIME_DECODE_TABLE[s.charAt(s.length() - 3)];
            byte byte9 = MIME_DECODE_TABLE[s.charAt(s.length() - 2)];
            retorno[(retorno.length - 2)] = (byte) (byte2 << 2 | byte6 >> 4);
            retorno[(retorno.length - 1)] = (byte) (byte6 << 4 | byte9 >> 2);
        } else {
            byte byte3 = MIME_DECODE_TABLE[s.charAt(s.length() - 4)];
            byte byte7 = MIME_DECODE_TABLE[s.charAt(s.length() - 3)];
            byte byte10 = MIME_DECODE_TABLE[s.charAt(s.length() - 2)];
            byte byte12 = MIME_DECODE_TABLE[s.charAt(s.length() - 1)];
            retorno[(retorno.length - 3)] = (byte) (byte3 << 2 | byte7 >> 4);
            retorno[(retorno.length - 2)] = (byte) (byte7 << 4 | byte10 >> 2);
            retorno[(retorno.length - 1)] = (byte) (byte10 << 6 | byte12);
        }

        return retorno;
    }

    public static String decode(String encoded) throws DecoderException {
        org.apache.commons.codec.binary.Base64 base64 = new org.apache.commons.codec.binary.Base64();
        return new String(encoded != null ? base64.decode(encoded.getBytes()) : null);
    }

    static {
        for (int i = 65; i <= 90; i++) {
            MIME_DECODE_TABLE[i] = (byte) (i - 65);
        }

        for (int j = 97; j <= 122; j++) {
            MIME_DECODE_TABLE[j] = (byte) (j - 97 + 26);
        }

        for (int k = 48; k <= 57; k++) {
            MIME_DECODE_TABLE[k] = (byte) (k - 48 + 52);
        }

        MIME_DECODE_TABLE[43] = 62;
        MIME_DECODE_TABLE[47] = 63;
    }
}