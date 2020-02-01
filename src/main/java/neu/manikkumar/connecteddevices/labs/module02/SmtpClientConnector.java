package neu.manikkumar.connecteddevices.labs.module02;

import java.util.Properties;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import neu.manikkumar.connecteddevices.common.ConfigUtil;

public class SmtpClientConnector{
    
    private final static Logger LOGGER = Logger.getLogger("SMTPLogger");            //Retrieving a logging instance
    public ConfigUtil configReader;                                                 //ConfigRead object to read configuration data from
    private String toAddr;                                                          //TO Address
    private String fromAddr;                                                        //FROM Address
    private String token;                                                           //AuthToken        

    public SmtpClientConnector() {
        /*
         *Constructor
         */
        //Load config data in the configuration file
        this.configReader = new ConfigUtil();
    }

    public boolean sendMail(String topic, String data) {
        /*
         *Method which connects to my G Mail SMTP server using email.mime and sends out E-Mails
         *MIME stand for Multipurpose Internet Mail Extensions
         *Email subject and data are method inputs
         */
        String host = configReader.getValue("smtp.cloud", "host");
        String port = configReader.getValue("smtp.cloud", "port");
        fromAddr = configReader.getValue("smtp.cloud", "fromAddr");
        toAddr = configReader.getValue("smtp.cloud", "toAddr");
        token = configReader.getValue("smtp.cloud", "authToken");

        //Storing the read configuration in a properties instance for the SMTP mailer
        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.user", fromAddr);
        props.put("mail.smtp.password", token);
        props.put("mail.smtp.port", Integer.valueOf(port));
        props.put("mail.smtp.socketFactory.port", Integer.valueOf(port));
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");

        //Creating a new session instance so we can authenticate the mailer and send connect to the SMTP server
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            //Anonymous inner class for password authentication
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromAddr, token);
            }
        });
        try {
            //Creating the message in MIME format
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromAddr));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddr));
            message.setSubject(topic);
            message.setText(data);
            //Creating a transport instance and specifying smtp to it
            Transport transport = session.getTransport("smtp");
            //Connecting to the server using transport, host, port, our email address and the token
            transport.connect(host, Integer.valueOf(port), fromAddr, token);
            //sending out email using sendMessage
            transport.sendMessage(message, message.getAllRecipients());
        } catch (MessagingException mex) {
                //If something goes wrong, returning a false and logging the event
                LOGGER.info("Mail Error | SMTP could not connect");
                mex.printStackTrace();
                return false;
        }           
    //Returning a true if the program works as intended and logging the event    
    LOGGER.info("E-mail sent");
    return true;   
    }
}
