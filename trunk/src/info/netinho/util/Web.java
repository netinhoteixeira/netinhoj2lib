package info.netinho.util;

public class Web {

    public static int getParameter(String parameter) {
        int retorno = 0;

        if (parameter != null) {
            parameter = Number.getOnlyNumbers(parameter);
            if (parameter.length() > 0) {
                retorno = Integer.parseInt(parameter);
            }
        }

        return retorno;
    }

    public static String justFileName(String fileName) {
        String retorno = "";

        int index = fileName.lastIndexOf('/');
        if (index >= 0) {
            retorno = fileName.substring(index + 1);
        } else {
            index = fileName.lastIndexOf('\\');
            if (index >= 0) {
                retorno = fileName.substring(index + 1);
            } else {
                retorno = fileName;
            }
        }

        return retorno;
    }
}