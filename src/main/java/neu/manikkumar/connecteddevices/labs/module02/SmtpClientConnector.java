package neu.manikkumar.connecteddevices.labs.module02;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import neu.manikkumar.connecteddevices.common.ConfigUtil;

public class SmtpClientConnector{
    
    public ConfigUtil configReader;
    private String toAddr;
    private String fromAddr;
    private String token;

    public SmtpClientConnector() {
        this.configReader = new ConfigUtil();
    }

    public boolean sendMail(String topic, String data) {

        String host = configReader.getValue("smtp.cloud", "host");
        String port = configReader.getValue("smtp.cloud", "port");
        fromAddr = configReader.getValue("smtp.cloud", "fromAddr");
        toAddr = configReader.getValue("smtp.cloud", "toAddr");
        token = configReader.getValue("smtp.cloud", "authToken");

        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.user", fromAddr);
        props.put("mail.smtp.password", token);
        props.put("mail.smtp.port", Integer.valueOf(port));
        props.put("mail.smtp.socketFactory.port", Integer.valueOf(port));
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromAddr, token);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromAddr));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddr));
            message.setSubject(topic);
            message.setText(data);


            Transport transport = session.getTransport("smtp");

            transport.connect(host, Integer.valueOf(port), fromAddr, token);

            transport.sendMessage(message, message.getAllRecipients());
        } catch (MessagingException mex) {
                mex.printStackTrace();
                return false;
        }           

    return true;   
    }
}
