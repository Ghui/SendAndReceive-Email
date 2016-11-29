package com.example.melab.myapplication;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.sql.DataSource;

public class GMailSender {

    final String emailPort = "587";// gmail's smtp port
    final String smtpAuth = "true";
    final String starttls = "true";
    final String emailHost = "smtp.gmail.com";


    String fromEmail;
    String fromPassword;
    List<String> toEmailList;
    String emailSubject;
    String emailBody;

    Properties emailProperties;
    Session mailSession;
    MimeMessage emailMessage;

    public GMailSender() {

    }

    public GMailSender(String fromEmail, String fromPassword,
                 List<String> toEmailList, String emailSubject, String emailBody) {
        this.fromEmail = fromEmail;
        this.fromPassword = fromPassword;
        this.toEmailList = toEmailList;
        this.emailSubject = emailSubject;
        this.emailBody = emailBody;

        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", emailPort);
        emailProperties.put("mail.smtp.auth", smtpAuth);
        emailProperties.put("mail.smtp.starttls.enable", starttls);
        Log.i("GMail", "Mail server properties set.");
    }

    public MimeMessage createEmailMessage() throws AddressException,
            MessagingException, UnsupportedEncodingException {

        mailSession = Session.getDefaultInstance(emailProperties, null);
        //Create New message
        emailMessage = new MimeMessage(mailSession);

        //sets where the message is from
        emailMessage.setFrom(new InternetAddress(fromEmail, fromEmail));

        //sets the recipent of the message
        for (String toEmail : toEmailList) {
            Log.i("GMail", "toEmail: " + toEmail);
            emailMessage.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(toEmail));
        }

        //sets the subject of the message
        emailMessage.setSubject(emailSubject);

        //sets the body of the message
        emailMessage.setContent(emailBody, "text/html");// for a html email

        //sets the text email
//***********************************************************************************************************************
        /*
        //needs to be edits to implement attachments for email;

        //create message part
        BodyPart messageBodyPart =new MimeBodyPart();

        //set the actual message
        messageBodyPart.setText("This is the message body");

        //create a multipar message
        Multipart multipart = new MimeMultipart();

        //set a text message part
        multipart.addBodyPart(messageBodyPart);

        //puts in the attachment
        File filename = new File(Environment.getExternalStorageDirectory(),"test");
        //String filename = "test.txt";
        messageBodyPart = new MimeBodyPart();
        FileDataSource source = new FileDataSource(filename.getAbsolutePath());
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("test");
        multipart.addBodyPart(messageBodyPart);

        //sends the complese message
        emailMessage.setContent(multipart);
        */
        //*************************************************************************************
        // emailMessage.setText(emailBody);// for a text email
        Log.i("GMail", "Email Message created.");
        return emailMessage;
    }

    public void sendEmail() throws AddressException, MessagingException {

        Transport transport = mailSession.getTransport("smtp");
        transport.connect(emailHost, fromEmail, fromPassword);
        Log.i("GMail", "all recipients: " + emailMessage.getAllRecipients());
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
        Log.i("GMail", "Email sent successfully.");
    }

}
