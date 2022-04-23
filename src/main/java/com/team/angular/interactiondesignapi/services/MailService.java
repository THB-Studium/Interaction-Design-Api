package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.models.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.List;

@Service
@Slf4j
public class MailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${template.email.simple-email}")
    private String template_simple_email;

    @Value("${template.email.from}")
    private String from;

    // simple mail
    public void sendHtmlMessage(Email email) throws MessagingException, IOException {

        MimeMessage message = emailSender.createMimeMessage();

        message.reply(email.isReply());

        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        Context context = new Context();

        context.setVariables(email.getProperties());

        helper.setFrom(from);
        helper.setTo(email.getTo());
        helper.setSubject(email.getSubject());

        String html = templateEngine.
                process(email.getTemplate() != null ? email.getTemplate() : template_simple_email, context);

        helper.setText(html, true);

        log.info("Sending email: {} with html body: {}", email, html);
        try {
            emailSender.send(message);
            templateEngine.clearTemplateCache();
        } catch (Exception e) {
            log.error("Error during email sending : %s", e.getMessage());
        }
    }

    // Mail with multipartFile
    public void sendHtmlMessageAttachment(Email email, List<MultipartFile> content) throws MessagingException,
            IOException {

        MimeMessage message = emailSender.createMimeMessage();

        message.reply(email.isReply());

        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        Context context = new Context();

        context.setVariables(email.getProperties());
        helper.setFrom(from);
        helper.setTo(email.getTo());
        helper.setSubject(email.getSubject());

        String html = templateEngine
                .process(email.getTemplate() != null ? email.getTemplate() : template_simple_email, context);
        helper.setText(html, true);


        for (MultipartFile multipartFile : content) {
            DataSource ds = new ByteArrayDataSource(multipartFile.getBytes(), multipartFile.getContentType());

            helper.addAttachment(multipartFile.getOriginalFilename(), ds);
        }

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

        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        Context context = new Context();

        context.setVariables(email.getProperties());

        helper.setFrom(from);
        helper.setTo(email.getTo());
        helper.setSubject(email.getSubject());

        String html = templateEngine.
                process(email.getTemplate() != null ? email.getTemplate() : template_simple_email, context);
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