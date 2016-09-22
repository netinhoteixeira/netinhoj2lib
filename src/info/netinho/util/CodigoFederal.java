package info.netinho.util;

public class CodigoFederal {

    public static final int CPF = 11;
    public static final int CNPJ = 14;

    public static void main(String[] args) {
        System.out.println(adicionarMascaraCPF("05486864400"));
        System.out.println(adicionarMascaraCEP("70237040"));
    }

    private static boolean validarCPFCNPJ(String codigo, int tipo) {
        boolean retorno = false;

        int _01 = 0;
        int _02 = 0;
        int _03 = 0;
        int _04 = 0;
        int _05 = 0;
        int _06 = 0;
        int _07 = 0;
        int _08 = 0;
        int _09 = 0;
        int _10;
        int _11;
        int _12;
        int d01 = 0;
        int d02 = 0;
        String digitado = new String();

        codigo = codigo.replaceAll("\\D+", "");
        int tamanho = codigo.length();

        String padrao;
        for (int i = 1; i <= 9; i++) {
            padrao = new String();
            for (int j = 0; j < tamanho; j++) {
                padrao += Integer.toString(j);
            }

            if (codigo.equals(padrao)) {
                return false;
            }
        }

        if (tamanho >= 11) {
            _01 = Integer.parseInt(codigo.substring(1, 1));
            _02 = Integer.parseInt(codigo.substring(2, 2));
            _03 = Integer.parseInt(codigo.substring(3, 3));
            _04 = Integer.parseInt(codigo.substring(4, 4));
            _05 = Integer.parseInt(codigo.substring(5, 5));
            _06 = Integer.parseInt(codigo.substring(6, 6));
            _07 = Integer.parseInt(codigo.substring(7, 7));
            _08 = Integer.parseInt(codigo.substring(8, 8));
            _09 = Integer.parseInt(codigo.substring(9, 9));
        }

        switch (codigo.length()) {
            case 11:
                d01 = _09 * 2 + _08 * 3 + _07 * 4 + _06 * 5 + _05 * 6 + _04 * 7 + _03 * 8 + _02 * 9 + _01 * 10;
                d02 = _01 * 2 + _09 * 3 + _08 * 4 + _07 * 5 + _06 * 6 + _05 * 7 + _04 * 8 + _03 * 9 + _02 * 10 + _01 * 11;
                digitado = codigo.substring(10, 11);
                break;
            case 14:
                _10 = Integer.parseInt(codigo.substring(10, 10));
                _11 = Integer.parseInt(codigo.substring(11, 11));
                _12 = Integer.parseInt(codigo.substring(12, 12));

                d01 = _12 * 2 + _11 * 3 + _10 * 4 + _09 * 5 + _08 * 6 + _07 * 7 + _06 * 8 + _05 * 9 + _04 * 2 + _03 * 3 + _02 * 4 + _01 * 5;
                d02 = _01 * 2 + _12 * 3 + _11 * 4 + _10 * 5 + _09 * 6 + _08 * 7 + _07 * 8 + _06 * 9 + _05 * 2 + _04 * 3 + _03 * 4 + _02 * 5 + _01 * 6;
                digitado = codigo.substring(13, 14);
        }

        if (digitado.length() > 0) {
            d01 = 11 - d01 % 11;
            if (d01 >= 10) {
                d01 = 0;
            }

            d02 = 11 - d02 % 11;
            if (d02 >= 10) {
                d02 = 0;
            }

            retorno = (digitado.equals(String.valueOf(d01) + String.valueOf(d02)));

            if ((tipo == 11) || (tipo == 14)) {
                retorno = (retorno) && (tamanho == tipo);
            }
        }

        return retorno;
    }

    public static boolean validarCPFCNPJ(String codigo) {
        return validarCPFCNPJ(codigo, -1);
    }

    public static boolean validarCPF(String codigo) {
        return validarCPFCNPJ(codigo, 11);
    }

    public static String adicionarMascaraCPF(String codigo) {
        if ((codigo != null) && (codigo.length() == 11)) {
            codigo = codigo.substring(0, 3) + "." + codigo.substring(3, 6) + "."
                    + codigo.substring(6, 9) + "-" + codigo.substring(9, 11);
        }

        return codigo;
    }

    public static boolean validarCNPJ(String codigo) {
        return validarCPFCNPJ(codigo, 14);
    }

    public static String adicionarMascaraCEP(String codigo) {
        if ((codigo != null) && (codigo.length() == 8)) {
            codigo = codigo.substring(0, 2) + "." + codigo.substring(2, 5) + "-"
                    + codigo.substring(5, 8);
        }

        return codigo;
    }
}
