/**
 *  MultiLanguage.java
 *  Created on : 12/05/2011, 11:58:16
 */
package info.netinho.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.PropertyResourceBundle;

/**
 *
 * @author Francisco Ernesto Teixeira <contato@netinho.info>
 */
public class Properties {

    private String path;
    private String filename_default = "options";
    private String filename;
    private PropertyResourceBundle properties;

    public Properties() {
        this.path = new String();
        this.filename = this.filename_default;
        this.setPath(path);
    }

    public Properties(String path) {
        this.filename = this.filename_default;
        this.setPath(path);
    }

    public Properties(String path, String filename) {
        this.filename = filename;
        this.setPath(path);
    }

    public final void setPath(String path) {
        if (path == null) {
            path = new String();
        } else {
            if (!path.isEmpty()) {
                path = path.replace('\\', '/');
                if (path.charAt(path.length() - 1) != '/') {
                    path += '/';
                }
            }
        }
        this.path = path;
        this.setFilename(this.filename);
    }

    public final void setFilename(String filename) {
        File file = new File(this.path + filename + ".properties");
        this.properties = null;

        try {
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                properties = new PropertyResourceBundle(fis);
                this.filename = filename;
            } else {
                throw new NullPointerException("Not found: " + file.getAbsolutePath());
            }
        } catch (IOException ioex) {
            throw new NullPointerException("Not processed:" + file.getAbsolutePath() + "\n" + ioex.getMessage());
        }
    }

    public String getFilename() {
        return this.filename;
    }

    public String get(String label) {
        return properties.getString(label);
    }
}
