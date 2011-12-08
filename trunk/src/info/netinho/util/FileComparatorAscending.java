/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.netinho.util;

import java.io.File;
import java.text.Collator;
import java.util.Comparator;

/**
 *
 * @author francisco.teixeira
 */
public class FileComparatorAscending<T> implements Comparator {

    private Collator c = Collator.getInstance();

    @Override
    public int compare(Object o1, Object o2) {
        if (o1 == o2) {
            return 0;
        }

        File f1 = (File) o1;
        File f2 = (File) o2;

        if (f1.isDirectory() && f2.isFile()) {
            return -1;
        }
        if (f1.isFile() && f2.isDirectory()) {
            return 1;
        }

        return c.compare(f1.getName(), f2.getName());
    }
}
