/*
 *  Document   : Diretorio.java
 *  Created on : 28/03/2011, 14:52:40
 */
package info.netinho.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author Francisco Ernesto Teixeira <contato@netinho.info>
 */
public class Diretorio {

    private static void GetDirectory(String a_Path, Collection<String> a_files, Collection<String> a_folders) {
        File l_Directory = new File(a_Path);
        File[] l_files = l_Directory.listFiles();
        for (int c = 0; c < l_files.length; c++) {
            if (l_files[c].isDirectory()) {
                a_folders.add(l_files[c].getName());
            } else {
                a_files.add(l_files[c].getName());
            }
        }
    }

    public static Iterator<String> listar(String diretorio, String regex) {
        Collection<String> files = new ArrayList<String>();
        Collection<String> folders = new ArrayList<String>();
        Collection<String> output = new ArrayList<String>();
        GetDirectory(diretorio, files, folders);
        Iterator<String> i_Files = files.iterator();
        while (i_Files.hasNext()) {
            String file = i_Files.next();
            if (file.matches(regex)) { //"\\bmarker-[a-zA-Z0-9_-]*.[p][n][g]\\b"
                output.add(file);
            }
        }
        return output.iterator();
    }
}
