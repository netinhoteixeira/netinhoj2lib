/**
 *  MultiLanguage.java
 *  Created on : 12/05/2011, 11:58:16
 */
package info.netinho.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco Ernesto Teixeira <contato@netinho.info>
 */
public class MultiLanguage {

    private String path;
    private String language_default = "en";
    private String language;
    private PropertyResourceBundle properties;

    public MultiLanguage() {
        this.path = new String();
        this.language = this.language_default;
        this.setPath(path);
    }

    public MultiLanguage(String path) {
        this.language = this.language_default;
        this.setPath(path);
    }

    public MultiLanguage(String path, String language) {
        this.language = language;
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
        this.setLanguage(this.language);
    }

    public final void setLanguage(String language) {
        String[] languages;
        String language_path = null;
        File language_file = null;
        this.properties = null;
        this.language = new String();

        try {
            languages = language.split(";");
            languages = languages[0].split(",");
            language = languages[0];
            String[] temp = language.split("-");
            language = temp[0];
            if (temp.length == 2) {
                language += "_" + temp[1].toUpperCase();
            }

            language_path = this.path + language + ".properties";
            language_file = new File(language_path);
            if (!language_file.exists()) {
                language = temp[0];
                language_path = this.path + language + ".properties";
                language_file = new File(language_path);
                if (!language_file.exists()) {
                    Logger.getLogger(MultiLanguage.class.getName()).log(Level.INFO, "Não existe: %s", language_path);
                    language = language_default;
                    language_path = this.path + language + ".properties";
                    language_file = new File(language_path);
                    if (!language_file.exists()) {
                        throw new NullPointerException("Not found: " + language_path);
                    }
                }
            }

            if (language_file.exists()) {
                FileInputStream fis = new FileInputStream(language_file);
                properties = new PropertyResourceBundle(fis);
                this.language = language;
            } else {
                throw new NullPointerException(language_path);
            }
        } catch (IOException ioex) {
            throw new NullPointerException("Not processed:" + language_path + "\n" + ioex.getMessage());
        }
    }

    public String getLanguage() {
        return this.language;
    }

    public String get(String label) {
        return properties.getString(label);
    }
}
