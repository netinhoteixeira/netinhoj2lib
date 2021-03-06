package info.netinho.util;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

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
        String retorno;

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

    public static String downloadContentStringURL(String url) {
        URL u;
        InputStream is = null;
        DataInputStream dis;
        StringBuilder sb = new StringBuilder();

        try {
            u = new URL(url);
            is = u.openStream();
            dis = new DataInputStream(new BufferedInputStream(is));
            while (true) {
                sb.append((char) dis.readByte());
            }
        } catch (EOFException eof) {
            // Yeah! - Normal program termination
            //Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, eof);
        } catch (MalformedURLException mue) {
            System.out.println("Ouch - a MalformedURLException happened.");
            Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, mue);
        } catch (IOException ioe) {
            System.out.println("Oops - an IOException happened.");
            Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ioe);
        } finally {
            try {
                is.close();
            } catch (IOException ioe) {
                Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ioe);
            }
        }

        return sb.toString();
    }

    public static BufferedImage downloadImageURL(String url) {
        URL u;
        BufferedInputStream is = null;
        BufferedImage bi = null;

        try {
            u = new URL(url);
            URLConnection hu = (HttpURLConnection) u.openConnection();
            hu.setRequestProperty("User-agent", "Mozilla/4.0");
            is = new BufferedInputStream(hu.getInputStream());
            bi = ImageIO.read(is);
            is.close();
        } catch (MalformedURLException mue) {
            System.out.println("Ouch - a MalformedURLException happened.");
            Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, mue);
        } catch (IOException ioe) {
            System.out.println("Oops - an IOException happened.");
            Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ioe);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException ioe) {
                Logger.getLogger(Web.class.getName()).log(Level.SEVERE, null, ioe);
            }
        }

        return bi;
    }

    /**
     *
     * @param targetURL
     * @param urlParameters
     * @return
     */
    public static String executePost(String targetURL, String urlParameters) {
        URL url;
        HttpURLConnection connection = null;

        try {
            // Create connection
            url = new URL(targetURL);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length", ""
                    + Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            // get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception ex) {
            Logger.getLogger(Web.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);

            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}