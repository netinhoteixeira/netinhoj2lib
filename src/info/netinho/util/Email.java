package info.netinho.util;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
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

public class Email {

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
        } catch (NamingException ne) {
            out.print(ne.getMessage());
        } catch (AddressException ae) {
            out.print(ae.getMessage());
        } catch (AuthenticationFailedException afe) {
            out.print("Falha ao tentar autenticar no servidor de e-mail.\n");
            out.print(afe.getMessage());
        } catch (MessagingException me) {
            out.print(me.getMessage());
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