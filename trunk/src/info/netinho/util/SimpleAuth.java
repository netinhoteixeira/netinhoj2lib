package info.netinho.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

class SimpleAuth extends Authenticator {

    public String username = null;
    public String password = null;

    public SimpleAuth(String user, String pwd) {
        this.username = user;
        this.password = pwd;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(this.username, this.password);
    }
}