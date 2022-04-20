package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.models.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@Slf4j
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${template.email.simple-email}")
    private String template_simple_email;

    public void sendMail(Email email) {

        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo(email.getRecipient());
        msg.setSubject(email.getSubject());
        msg.setText(email.getMessage());

        javaMailSender.send(msg);
    }

    public void sendMailWithAttachments(Email email) throws MessagingException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setFrom("keunne.baudoin@yahoo.fr");

        helper.setTo(email.getRecipient());

        helper.setSubject(email.getSubject());

        helper.setText(email.getMessage() + "<br> <br> Find the attached image", true);

        // Attachment must be in resources -> for the pdf
        helper.addAttachment("entete.jpg", new ClassPathResource("entete.jpg"));

        javaMailSender.send(msg);
    }

    // simple mail
    public void sendHtmlMessage(Email email) throws MessagingException, IOException {

        MimeMessage message = emailSender.createMimeMessage();

        message.reply(email.isReply());

        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Context context = new Context();

        context.setVariables(email.getProperties());

        helper.setFrom(email.getFrom());
        helper.setTo(email.getTo());
        helper.setSubject(email.getSubject());

        String html = templateEngine.process(email.getTemplate(), context);
        helper.setText(html, true);

        log.info("Sending email: {} with html body: {}", email, html);
        try {
            emailSender.send(message);
            templateEngine.clearTemplateCache();
        } catch (Exception e) {
            log.error("Error during email sending : %s", e.getMessage());
        }
    }

    // Email with multipartFile
    public void sendHtmlMessageAttachment(Email email, MultipartFile content) throws MessagingException, IOException {

        MimeMessage message = emailSender.createMimeMessage();

        message.reply(email.isReply());

        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Context context = new Context();

        context.setVariables(email.getProperties());

        helper.setFrom(email.getFrom());
        helper.setTo(email.getTo());
        helper.setSubject(email.getSubject());

        String html = templateEngine.process(email.getTemplate(), context);
        helper.setText(html, true);

        DataSource ds = new ByteArrayDataSource(content.getBytes(), content.getContentType());

        helper.addAttachment(content.getOriginalFilename(), ds);

        log.info("Sending email: {} with html body: {}", email, html);
        try {
            emailSender.send(message);
            templateEngine.clearTemplateCache();
        } catch (Exception e) {
            log.error("Error during email sending : %s", e.getMessage());
        }
    }

    // mail with datasource
    public void sendHtmlMessageAttachment(Email email, DataSource ds) throws MessagingException, IOException {

        MimeMessage message = emailSender.createMimeMessage();

        message.reply(email.isReply());

        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Context context = new Context();

        context.setVariables(email.getProperties());

        helper.setFrom(email.getFrom());
        helper.setTo(email.getTo());
        helper.setSubject(email.getSubject());

        String html = templateEngine.process(email.getTemplate(), context);
        helper.setText(html, true);

        helper.addAttachment("booking_" + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + ".pdf", ds);

        log.info("Sending email: {} with html body: {}", email, html);
        try {
            emailSender.send(message);
            templateEngine.clearTemplateCache();
        } catch (Exception e) {
            log.error("Error during email sending : %s", e.getMessage());
        }
    }
}