package info.netinho.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import org.apache.commons.codec.DecoderException;

public class Katie {

    public static String get(int t, String k, String v) {
        String retorno = new String();
        try {
            DESKeySpec keySpec = new DESKeySpec(k.getBytes("UTF8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);
            Cipher cipher;
            switch (t) {
                case 1:
                    byte[] cleartext = v.getBytes("UTF8");

                    cipher = Cipher.getInstance("DES");
                    cipher.init(1, key);
                    retorno = new String(Base64.toByte(new String(cipher.doFinal(cleartext))));
                    break;
                case 2:
                    String decoded = new String();
                    try {
                        decoded = Base64.decode(v);
                    } catch (DecoderException ex) {
                        Logger.getLogger(Katie.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    byte[] encrypedPwdBytes = decoded.getBytes();

                    cipher = Cipher.getInstance("DES");
                    cipher.init(2, key);
                    byte[] plainTextPwdBytes = cipher.doFinal(encrypedPwdBytes);

                    retorno = new String(plainTextPwdBytes);
            }
        } catch (InvalidKeyException ikex) {
            System.out.println(ikex.getMessage());
        } catch (UnsupportedEncodingException uex) {
            System.out.println(uex.getMessage());
        } catch (NoSuchAlgorithmException nsaex) {
            System.out.println(nsaex.getMessage());
        } catch (InvalidKeySpecException iksex) {
            System.out.println(iksex.getMessage());
        } catch (NoSuchPaddingException nspex) {
            System.out.println(nspex.getMessage());
        } catch (IllegalBlockSizeException ibsex) {
            System.out.println(ibsex.getMessage());
        } catch (BadPaddingException bpex) {
            System.out.println(bpex.getMessage());
        } catch (IOException ioex) {
            System.out.println(ioex.getMessage());
        }

        return retorno;
    }
}