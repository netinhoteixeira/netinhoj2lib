
package info.netinho.util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SMTP extends Email {

    private String server;
    private String port;
    private Boolean starttls;
    private String user;
    private String password;

    public SMTP() {
        this.server = "smtp.gmail.com";
        this.port = "465";
        this.user = "no-reply@afrafepsaude.com.br";//"myuser@gmail.com";
        this.password = "kIxW71jh";//"mypassword";
    }

    public SMTP(String server, String port, Boolean starttls, String user, String password) {
        this.server = server;
        this.port = port;
        this.starttls = starttls;
        this.user = user;
        this.password = password;
    }

    public void send(String from, String to, String subject, String message) throws MessagingException {
        Properties props = new Properties();

        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.starttls.enable", this.starttls ? "true" : "false");
        props.put("mail.smtp.host", this.server);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.user", from);

        props.put("mail.smtp.port", this.port);
        props.put("mail.smtp.socketFactory.port", this.port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.mime.charset", "ISO-8859-1");

        SimpleAuth auth = new SimpleAuth(this.user, this.port);
        Session session = Session.getInstance(props, auth);

        Message msg = new MimeMessage(session);
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        msg.setFrom(new InternetAddress(from));
        msg.setSubject(subject);
        msg.setContent(message, "text/plain; charset=ISO-8859-1");

        Transport tr = session.getTransport("smtp");
        tr.connect(this.server, this.user, this.port);
        msg.saveChanges();
        tr.sendMessage(msg, msg.getAllRecipients());
        tr.close();
    }
}