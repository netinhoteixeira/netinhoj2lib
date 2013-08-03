package info.netinho.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco Ernesto Teixeira <fco.ernesto@gmail.com>
 */
public class PropertiesStream {

    private String streamName;

    public PropertiesStream(String streamName) {
        this.streamName = streamName;
    }

    protected InputStream getPropertiesFileStream() {
        return this.getClass().getClassLoader().getResourceAsStream(streamName + ".properties");
    }

    public String getString(String string) {

        java.util.Properties prop = new java.util.Properties();
        try {
            prop.load(this.getPropertiesFileStream());

            return prop.getProperty(string);
        } catch (IOException ex) {
            Logger.getLogger(PropertiesStream.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}