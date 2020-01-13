package com.cogent.email.service;

/**
 * @author smriti on 2019-08-20
 */

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.sun.mail.smtp.SMTPTransport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import static com.cogent.email.constants.EmailTestConstants.*;
import static junit.framework.TestCase.assertTrue;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EmailServiceTest {

    private GreenMail mailServer;

    private JavaMailSender javaMailSender;

    private Map<String, String> initProperties;

    @Before
    public void setUp() {
        mailServer = new GreenMail(ServerSetupTest.SMTP);
        mailServer.start();
    }

    @After
    public void tearDown() {
        mailServer.stop();
    }

    @Test
    public void sendEmail() throws IOException, MessagingException {
        // SETUP USER ON THE MAIL SERVER
        mailServer.setUser(EMAIL_USER_ADDRESS, USER_NAME, USER_PASSWORD);

        Session session = Session.getInstance(getMailProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USER_ADDRESS, USER_PASSWORD);
            }
        });

        Message message = createMessage(session);

        SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
        t.connect(LOCALHOST, EMAIL_USER_ADDRESS, USER_PASSWORD);
        t.sendMessage(message, message.getAllRecipients());

        assertEquals("250 OK\n", t.getLastServerResponse());
        t.close();

        // fetch messages from server
        MimeMessage[] messages = mailServer.getReceivedMessages();
        assertNotNull(messages);
        assertEquals(1, messages.length);
        MimeMessage m = messages[0];
        assertEquals(EMAIL_SUBJECT, m.getSubject());

        System.out.println(String.valueOf(m.getContent()));
        System.out.println(m.getContent());

        assertTrue(String.valueOf(m.getContent()).contains(EMAIL_TEXT));
        assertEquals(EMAIL_USER_ADDRESS, m.getFrom()[0].toString());
    }

    private Properties getMailProperties() {
        //CREATE THE javax.mail STACK WITH SESSION, MANAGE AND TRANSPORT

        Properties props = System.getProperties();
        props.put("mail.smtp.host", LOCALHOST);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", ServerSetupTest.SMTP.getPort());

        return props;
    }

    public MimeMessage createMessage(Session session) {

        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(EMAIL_USER_ADDRESS));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EMAIL_TO, false));
            message.setSubject(EMAIL_SUBJECT);
            message.setText(EMAIL_TEXT);
            message.setSentDate(new Date());

            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return message;
    }

}
