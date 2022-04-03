package com.team.angular.interactiondesignapi.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

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

import com.team.angular.interactiondesignapi.models.Email;
import com.team.angular.interactiondesignapi.models.Mail;

import lombok.extern.slf4j.Slf4j;

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

		helper.setFrom("keunne.baudoin@yahoo.fr");

		helper.setTo(mail.getRecipient());

		helper.setSubject(mail.getSubject());

		helper.setText(mail.getMessage() + "<br> <br> Find the attached image", true);

		// image must be in resources
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
    
    // Mail with multipartFile
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
        
        helper.addAttachment("booking_"+LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)+".pdf", ds);

        log.info("Sending email: {} with html body: {}", email, html);
        try {
        	emailSender.send(message);
        	templateEngine.clearTemplateCache();
		} catch (Exception e) {
			log.error("Error during email sending : %s", e.getMessage());			
		}
    }
}