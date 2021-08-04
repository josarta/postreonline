/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.postreonline.utilidades;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Josarta
 */
public abstract class Mail {
    
     public static void recuperarClaves(String correoIn, String nuevaClave) {
        final String usuario = "adsiwebjava@gmail.com";
        final String clave = "adsi2021";

        Properties props = new Properties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com"); // envia 
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", "25");
        props.setProperty("mail.smtp.starttls.required", "false");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, clave);
            }
        });

        try {
            MimeMessage mensage = new MimeMessage(session);
            mensage.setFrom(new InternetAddress(usuario));
            mensage.addRecipient(Message.RecipientType.TO, new InternetAddress(correoIn));
            mensage.setSubject("Recordatorio de claves");
            mensage.setContent("<center> "
                    + "<img src='https://cdn.pixabay.com/photo/2019/08/30/15/48/lock-4441691_960_720.png' width='100px' height='100px' >"
                    + "</center>"
                    + "<br/>"
                    + "<h1> Su nueva clave es </h1>"
                    + "<br/>"
                    + "Clave : " + nuevaClave,
                     "text/html");
            Transport.send(mensage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
}
