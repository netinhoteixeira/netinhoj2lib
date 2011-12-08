package info.netinho.util;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

public class Password {

    private static final byte[] _chave = Base64.toByte("rO0ABXVyABNbTGphdmEubGFuZy5TdHJpbmc7rdJW5+kde0cCAAB4cAAAADJ0AAhKQ0NFSUdIRnQACElFRkZHQ0NBdAAIQUFKQkNKSEh0AAhESEpJREpDRHQACElCQkFEREZFdAAISkpDRUJESkN0AAhGQ0hHQUpKQnQACEVCSkdERkFEdAAIQkZGREJCSkJ0AAhDSUdFSEpGR3QACEFFQkJDR0lJdAAIREhJQkhKRkd0AAhJRENCR0JESHQACEZFSElDSkNDdAAIR0pKREZGSUJ0AAhCSkZFQkRHSXQACEpISEJKR0FCdAAISUhHREVHRUJ0AAhHQ0NGQkJCSXQACEZJREdHRUdKdAAIRURERkhFRkF0AAhFRkNGQUJDRnQACEVGR0VJQ0FEdAAISEhGREdDRUd0AAhJQkNHRkFGR3QACEFEQkdKSERIdAAIR0hIREFJSkl0AAhFSUNER0hIRnQACENISUZIRkFGdAAISEJCQklGSEF0AAhJRERFSERDQXQACEVCSkVIQ0pIdAAIRkVDSkZJQ0Z0AAhEQkZERkVJQ3QACEVIRUJHR0NFdAAIREFDQ0dCREN0AAhKSUdHRUNBSXQACEhCQklBQkpGdAAIR0hHREZJQkh0AAhFRkJJSEdISXQACEdGRUVKQUJEdAAIQUlIQURBSEN0AAhFR0pGQkFEQXQACEJIRUFERUJGdAAISEVIR0hKQ0d0AAhFREFESkFBSnQACEZHRkJHQUpEdAAIQ0FER0RCREJ0AAhHSURFSkFBQ3QACENBQ0RISkdI");
    private static String[] chave;

    public static String gerarSenha() {
        return RandomString.randomString(8, 8, '0', '9');
    }

    public static String gerarSemente() {
        String retorno = "";

        int verificador = -1;
        while ((verificador < 0) || (verificador >= chave.length)) {
            retorno = RandomString.randomString(2, 2, '0', '9');
            verificador = Integer.parseInt(retorno);
        }

        return retorno;
    }

    public static String codificar(String senha, int semente) {
        String retorno = "";
        for (int i = 0; (i < senha.length())
                && (i < 8); i++) {
            retorno = retorno + "" + (char) codificarCaractere(Integer.parseInt(senha.substring(i, i + 1)) * (i + 1), chave[semente].charAt(i), i);
        }

        return retorno;
    }

    public static String decodificar(String senha, int semente) {
        String retorno = "";
        for (int i = 0; (i < senha.length())
                && (i < 8); i++) {
            retorno = retorno + Integer.toString(decodificarCaractere(senha.charAt(i), chave[semente].charAt(i), i) / (i + 1));
        }

        return retorno;
    }

    private static int codificarCaractere(int numero, char caracter, int posicao) {
        numero /= (posicao + 1);
        numero++;
        switch (caracter) {
            case 'A':
                numero = numero * 3 + posicao + 5;
                break;
            case 'B':
                numero = numero * 5 + posicao + 3;
                break;
            case 'C':
                numero = numero * 4 - posicao + 9;
                break;
            case 'D':
                numero = numero * 2 + posicao + 7;
                break;
            case 'E':
                numero = numero * 6 + posicao - 1;
                break;
            case 'F':
                numero = numero * 3 - posicao + 11;
                break;
            case 'G':
                numero = numero * 2 - posicao + 19;
                break;
            case 'H':
                numero = numero * 5 + posicao + 2;
                break;
            case 'I':
                numero = numero * 5 + posicao + 4;
                break;
            case 'J':
                numero = numero * 4 + posicao + 7;
        }

        numero += 58;
        return numero;
    }

    private static int decodificarCaractere(int numero, char caracter, int posicao) {
        numero -= 58;
        switch (caracter) {
            case 'A':
                numero = (numero - 5 - posicao) / 3;
                break;
            case 'B':
                numero = (numero - 3 - posicao) / 5;
                break;
            case 'C':
                numero = (numero - 9 + posicao) / 4;
                break;
            case 'D':
                numero = (numero - 7 - posicao) / 2;
                break;
            case 'E':
                numero = (numero + 1 - posicao) / 6;
                break;
            case 'F':
                numero = (numero - 11 + posicao) / 3;
                break;
            case 'G':
                numero = (numero - 19 + posicao) / 2;
                break;
            case 'H':
                numero = (numero - 2 - posicao) / 5;
                break;
            case 'I':
                numero = (numero - 4 - posicao) / 5;
                break;
            case 'J':
                numero = (numero - 7 - posicao) / 4;
        }

        return (numero - 1) * (posicao + 1);
    }

    static {
        try {
            ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(_chave);
            ObjectInputStream objectinputstream = new ObjectInputStream(bytearrayinputstream);
            chave = (String[]) (String[]) objectinputstream.readObject();
            objectinputstream.close();
            bytearrayinputstream.close();
        } catch (Exception exception) {
            System.out.println("Erro na desserialização.");
        }
    }
}