package cn.zjw.study.service;

import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * EmailService
 *
 * @author zjw
 * @createTime 2021/2/6 11:26
 */
@Component
public class EmailService {

    public void sendMail(String sendMail, String authorizationCode, String takeMail) throws Exception {
        Properties prop = new Properties();
        prop.setProperty("mail.host", "smtp.qq.com");
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);
        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sendMail, authorizationCode);
            }
        });
        Transport transport = session.getTransport();
        transport.connect("smtp.qq.com", sendMail, authorizationCode);
        MimeMessage mimeMessage = complexEmail(session, sendMail, takeMail);
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
    }

    public static MimeMessage complexEmail(Session session, String sendMail, String takeMail) throws MessagingException {
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(sendMail));
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(takeMail));
        mimeMessage.setSubject("测试");
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("<span style='color: red;'>Hello, world!</span>", "text/html;charset=utf-8");
        MimeMultipart allFile = new MimeMultipart();
        allFile.addBodyPart(text);
        mimeMessage.setContent(allFile);
        mimeMessage.saveChanges();
        return mimeMessage;
    }

}
