package info.netinho.util;

public class Number {

    public static String getOnlyNumbers(String s) {
        String retorno = "";

        if (Text.hasContent(s)) {
            for (int i = 0; i < s.length(); i++) {
                if ((s.charAt(i) >= '0') && (s.charAt(i) <= '9')) {
                    retorno = retorno + "" + s.charAt(i);
                }
            }
        }

        return retorno;
    }
}
