package info.netinho.util;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.commons.collections.iterators.IteratorEnumeration;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class Email {

    private String server;
    private Integer port;
    private Boolean starttls;
    private String user;
    private String password;

    public Email() {
        this.server = "smtp.gmail.com";
        this.port = 465;
        this.starttls = true;
        this.user = "no-reply@afrafepsaude.com.br";//"myuser@gmail.com";
        this.password = "kIxW71jh";//"mypassword";
    }

    public Email(String server, Integer port, Boolean starttls, String user, String password) {
        this.server = server;
        this.port = port;
        this.starttls = starttls;
        this.user = user;
        this.password = password;
    }

    public void send(String from, String to, String subject, String message) throws MessagingException {
        send(from, to, subject, message, "text/plain; charset=UTF-8");//ISO-8859-1");
    }

    public void send(String from, String to, String subject, String message, String mime) throws MessagingException {
        java.util.Properties props = new java.util.Properties();

        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.starttls.enable", this.starttls ? "true" : "false");
        props.put("mail.smtp.host", this.server);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.user", this.user);
        props.put("mail.smtp.password", this.password);
        props.put("mail.smtp.port", Integer.toString(this.port));
        props.put("mail.smtp.socketFactory.port", Integer.toString(this.port));
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.mime.charset", "UTF-8");//"ISO-8859-1");

        SimpleAuth auth = new SimpleAuth(this.user, this.password);
        Session session = Session.getInstance(props, auth);

        Message msg = new MimeMessage(session);
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        msg.setFrom(new InternetAddress(from));
        msg.setSubject(subject);
        msg.setContent(message, mime);

        Transport tr = session.getTransport("smtp");
        tr.connect(this.server, this.port, this.user, this.password);
        msg.saveChanges();
        tr.sendMessage(msg, msg.getAllRecipients());
        tr.close();
    }

    public void sendSimpleMail(String destinatario, String assunto, String mensagem) throws EmailException {
        SimpleEmail email = new SimpleEmail();

        email.setDebug(true);
        email.setHostName(this.server);
        email.setSmtpPort(this.port);
        email.setAuthentication(this.user, this.password);
        email.setStartTLSEnabled(this.starttls);
        email.setFrom(this.user);
        email.addTo(destinatario);
        email.setSubject(assunto);
        email.setMsg(mensagem);
        email.send();
    }

    public static void sendSimpleMail(String mailSession, String destinatario, String assunto, String mensagem)
            throws NamingException, AddressException, MessagingException {
        InitialContext ic = new InitialContext();
        String snName = "java:comp/env/" + mailSession;
        Session session = (Session) ic.lookup(snName);

        String protocol = session.getProperty("mail.transport.protocol");
        String host = session.getProperty("mail.host");
        String _port = session.getProperty("mail.smtp.port");
        int port = _port != null ? Integer.valueOf(_port).intValue() : 25;
        String user = session.getProperty("mail.user");
        String password = session.getProperty("mail.password");

        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress(session.getProperty("mail.from")));

        System.out.println(session.getProperty("mail.transport.protocol"));
        System.out.println(session.getProperty("mail.host"));
        System.out.println(session.getProperty("mail.smtp.port"));
        System.out.println(session.getProperty("mail.user"));
        System.out.println(session.getProperty("mail.password"));
        System.out.println(session.getProperty("mail.from"));

        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario, false));

        message.setSubject(assunto);

        message.setSentDate(new Date());

        message.setContent(mensagem.toString(), "text/plain");

        Transport transport = session.getTransport(protocol);
        transport.connect(host, port, user, password);
        message.saveChanges();

        transport.sendMessage(message, message.getAllRecipients());
    }

    public static boolean sendSimpleMailUsingServlet(PrintWriter out, String mailSession, String destinatario, String assunto, String mensagem) {
        boolean retorno = false;
        try {
            sendSimpleMail(mailSession, destinatario, assunto, mensagem);
            retorno = true;
        } catch (NamingException ex) {
            out.print(ex.getMessage());
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AddressException ex) {
            out.print(ex.getMessage());
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AuthenticationFailedException ex) {
            out.print("Falha ao tentar autenticar no servidor de e-mail.\n");
            out.print(ex.getMessage());
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            out.print(ex.getMessage());
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }

    public static void sendMailList(String mailSession, HashMap destinatarios, String assunto, String mensagem)
            throws NamingException, AddressException, MessagingException {
        Enumeration _destinatarios = new IteratorEnumeration(destinatarios.keySet().iterator());
        while (_destinatarios.hasMoreElements()) {
            String destinatario = (String) _destinatarios.nextElement();
            sendSimpleMail(mailSession, assunto, destinatario, mensagem);
        }
    }

    public static boolean isValidEmailAddress(String aEmailAddress) {
        if (aEmailAddress == null) {
            return false;
        }
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(aEmailAddress);
            if (!hasNameAndDomain(aEmailAddress)) {
                result = false;
            }
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    private static boolean hasNameAndDomain(String aEmailAddress) {
        String[] tokens = aEmailAddress.split("@");
        return (tokens.length == 2) && (Text.hasContent(tokens[0])) && (Text.hasContent(tokens[1]));
    }

    public static void sendSecureMail(String mailSession, String destinatario, String assunto, String mensagem)
            throws NamingException, AddressException, MessagingException {
        InitialContext ic = new InitialContext();
        String snName = "java:comp/env/" + mailSession;
        Session session = (Session) ic.lookup(snName);

        String protocol = session.getProperty("mail.transport.protocol");
        String host = session.getProperty("mail.host");
        String _port = session.getProperty("mail.smtp.port");
        int port = _port != null ? Integer.valueOf(_port).intValue() : 25;
        String user = session.getProperty("mail.user");
        String password = session.getProperty("mail.password");

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(session.getProperty("mail.from")));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario, false));
        message.setSubject(assunto);
        message.setSentDate(new Date());
        message.setContent(mensagem.toString(), "text/plain");

        Transport transport = session.getTransport(protocol);
        transport.connect(host, port, user, password);
        message.saveChanges();

        transport.sendMessage(message, message.getAllRecipients());
    }
}