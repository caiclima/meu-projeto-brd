package br.com.callink.bradesco.task.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.callink.bradesco.seguro.service.utils.Constantes;
import br.com.callink.bradesco.task.exception.EmailException;

public class SendEmail {

   public SendEmail(Properties properties) {
       this.props = properties;
       this.smtpAuthUser = properties.getProperty(Constantes.PARAMETRO_EMAIL_USUARIO);
       this.smtpAuthPw = properties.getProperty(Constantes.PARAMETRO_EMAIL_SENHA);
   }
   private final String smtpAuthUser;
   private final String smtpAuthPw;
   private final Properties props;

   public void send(String remetente, String destinatario, String assunto, String conteudo) throws EmailException {
       try {
           String contentType = "text/html; charset=\"utf-8\"";

           Session mailSession = null;

           Authenticator auth = new SMTPAuthenticator();
           mailSession = Session.getInstance(props, auth);

           Transport transport = mailSession.getTransport();

           MimeMessage message = new MimeMessage(mailSession);
           message.setSubject(assunto);

           message.setContent(conteudo, contentType);
           message.setFrom(new InternetAddress(remetente));
           String[] adresses = destinatario.split(",");
           for (int i = 0; i < adresses.length; i++) {
               message.addRecipient(Message.RecipientType.TO, new InternetAddress(adresses[i]));
           }
           message.saveChanges();

           transport.connect();
           transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
           transport.close();
       } catch (MessagingException ex) {
           throw new EmailException("Erro ao enviar email.", ex);
       }
   }


   private class SMTPAuthenticator extends javax.mail.Authenticator {

       @Override
       public PasswordAuthentication getPasswordAuthentication() {
           String username = smtpAuthUser;
           String password = smtpAuthPw;
           return new PasswordAuthentication(username, password);
       }
   }
}