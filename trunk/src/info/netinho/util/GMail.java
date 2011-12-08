package info.netinho.util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GMail extends Email {

    private String mailSMTPServer;
    private String mailSMTPServerPort;

    public GMail() {
        this.mailSMTPServer = "smtp.gmail.com";
        this.mailSMTPServerPort = "465";
    }

    public GMail(String mailSMTPServer, String mailSMTPServerPort) {
        this.mailSMTPServer = mailSMTPServer;
        this.mailSMTPServerPort = mailSMTPServerPort;
    }

    public void sendMail(String from, String to, String subject, String message) {
        Properties props = new Properties();

        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", this.mailSMTPServer);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.user", from);

        props.put("mail.smtp.port", this.mailSMTPServerPort);
        props.put("mail.smtp.socketFactory.port", this.mailSMTPServerPort);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.mime.charset", "ISO-8859-1");

        SimpleAuth auth = null;

        auth = new SimpleAuth("no-reply@afrafepsaude.com.br", "kIxW71jh");

        Session session = Session.getInstance(props, auth);

        Message msg = new MimeMessage(session);
        try {
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

            msg.setFrom(new InternetAddress(from));

            msg.setSubject(subject);

            msg.setContent(message, "text/plain; charset=ISO-8859-1");
        } catch (Exception e) {
            System.out.println(">> Erro: Completar Mensagem");
        }

        try {
            Transport tr = session.getTransport("smtp");

            tr.connect(this.mailSMTPServer, "no-reply@afrafepsaude.com.br", "kIxW71jh");

            msg.saveChanges();

            tr.sendMessage(msg, msg.getAllRecipients());
            tr.close();
        } catch (Exception e) {
            System.out.println(">> Erro: Envio Mensagem");
        }
    }
}