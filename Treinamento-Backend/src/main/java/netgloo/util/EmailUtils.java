package netgloo.util;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by estagiocit on 22/02/2017.
 */
public class EmailUtils {
    final String username = "treinamentojava2@gmail.com";
    final String password = "Suporte;123";
    Session session;
    Properties properties;
    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;


    public EmailUtils(){
    }

    public void sendMail(String para, String conteudo){
        try {
            mailServerProperties = System.getProperties();
            mailServerProperties.put("mail.smtp.port", "587");
            mailServerProperties.put("mail.smtp.auth", "true");
            mailServerProperties.put("mail.smtp.starttls.enable", "true");

            getMailSession = Session.getDefaultInstance(mailServerProperties, null);
            generateMailMessage = new MimeMessage(getMailSession);
            generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(para));
            generateMailMessage.setSubject("Notas");
            String emailBody = conteudo;
            generateMailMessage.setContent(emailBody, "text/html");

            Transport transport = getMailSession.getTransport("smtp");

            // Enter your correct gmail UserID and Password
            // if you have 2FA enabled then provide App Specific Password
            transport.connect("smtp.gmail.com", "treinamentojava2", password);
            transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
            transport.close();

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


}
