package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.models.Mail;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    private final JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(Mail mail) {

        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo(mail.getRecipient());
        msg.setSubject(mail.getSubject());
        msg.setText(mail.getMessage());

        javaMailSender.send(msg);
    }

    public void sendMailWithAttachments(Mail mail) throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo(mail.getRecipient());

        helper.setSubject(mail.getSubject());

        helper.setText("Find the attached image", true);

        //image must be in resources
        helper.addAttachment("entete.jpg", new ClassPathResource("entete.jpg"));

        javaMailSender.send(msg);
    }
}