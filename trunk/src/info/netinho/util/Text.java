package info.netinho.util;

import java.util.ArrayList;
import java.util.List;

public class Text {

    public static boolean hasContent(String s) {
        return (s != null) && (s.trim().length() > 0);
    }

    public static String retirarAcentos(String s) {
        if (s == null) {
            return new String();
        }
        String s1 = "àáâãäèéêëìíîïòóôõöùúûüýÿçñÀÁÂÃÄÈÉÊËÌÍÎÒÓÔÕÖÙÚÛÜÝÇÑ";
        String s2 = "aaaaaeeeeiiiiooooouuuuyycnAAAAAEEEEIIIOOOOOUUUUYCN";
        char[] retorno = s.toCharArray();
        if (s.length() > 0) {
            for (int i = 0; i < s.length(); i++) {
                int j = 0;

                while (j < s1.length()) {
                    if (retorno[i] == s1.charAt(j)) {
                        retorno[i] = s2.charAt(j);
                        break;
                    }
                    j++;
                }
            }
        }

        return new String(retorno);
    }

    public static String replicate(char c, int size) {
        String r = "";
        while (r.length() < size) {
            r = r + c;
        }
        return r;
    }

    public static int countOccurrences(String haystack, char needle) {
        int count = 0;
        for (int i = 0; i < haystack.length(); i++) {
            if (haystack.charAt(i) == needle) {
                count++;
            }
        }
        return count;
    }

    public static String[] splitPreserveAllTokens(String str, String separatorChars) {
        return splitWorker(str, separatorChars, -1, true);
    }

    private static String[] splitWorker(String str, String separatorChars, int max, boolean preserveAllTokens) {
        if (str == null) {
            return null;
        }
        int len = str.length();
        if (len == 0) {
            return new String[0];
        }
        List list = new ArrayList();
        int sizePlus1 = 1;
        int i = 0;
        int start = 0;
        boolean match = false;
        boolean lastMatch = false;
        if (separatorChars == null) {
            while (i < len) {
                if (Character.isWhitespace(str.charAt(i))) {
                    if ((match) || (preserveAllTokens)) {
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    i++;
                    start = i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        }
        if (separatorChars.length() == 1) {
            char sep = separatorChars.charAt(0);
            while (i < len) {
                if (str.charAt(i) == sep) {
                    if ((match) || (preserveAllTokens)) {
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    i++;
                    start = i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        } else {
            while (i < len) {
                if (separatorChars.indexOf(str.charAt(i)) >= 0) {
                    if ((match) || (preserveAllTokens)) {
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    i++;
                    start = i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        }
        if ((match) || ((preserveAllTokens) && (lastMatch))) {
            list.add(str.substring(start, i));
        }
        return (String[]) (String[]) list.toArray(new String[list.size()]);
    }
}