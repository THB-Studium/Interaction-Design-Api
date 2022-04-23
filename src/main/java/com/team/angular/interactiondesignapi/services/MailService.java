package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.models.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.activation.DataSource;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.nio.charset.StandardCharsets;
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
    public ResponseEntity<?> sendHtmlMessage(Email email) {

        try {
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

            emailSender.send(message);
            templateEngine.clearTemplateCache();
            return new ResponseEntity<>("Email sent successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error during email sending : %s", e.getMessage());
            return new ResponseEntity<>("Email cannot be sent", HttpStatus.BAD_REQUEST);
        }
    }

    // Mail with multipartFile
    public ResponseEntity<?> sendHtmlMessageAttachment(Email email, List<MultipartFile> content) {
        try {
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

            emailSender.send(message);
            templateEngine.clearTemplateCache();
            return new ResponseEntity<>("Attachment mail sent successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error during email sending : %s", e.getMessage());
            return new ResponseEntity<>("Email cannot be sent", HttpStatus.BAD_REQUEST);
        }
    }
}