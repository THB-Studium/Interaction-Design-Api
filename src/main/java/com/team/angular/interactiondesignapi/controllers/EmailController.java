package com.team.angular.interactiondesignapi.controllers;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.team.angular.interactiondesignapi.models.Email;
import com.team.angular.interactiondesignapi.models.Mail;
import com.team.angular.interactiondesignapi.services.MailService;

@RestController
@RequestMapping("/mail")
@CrossOrigin(origins = "*")
public class EmailController {

    private final MailService mailService;

    public EmailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMail(@RequestBody Email email) {
        mailService.sendMail(email);
        return new ResponseEntity<>("Email Sent successfully", HttpStatus.OK);
    }

    @PostMapping("/simple-email")
    public ResponseEntity<String> sendAttachmentEmail(@RequestPart Email mail) throws MessagingException, IOException {
        mailService.sendHtmlMessage(mail);
        return new ResponseEntity<>("Email sent successfully", HttpStatus.OK);
    }
    
    @PostMapping("/attachment")
    public ResponseEntity<String> sendAttachmentEmail(@RequestPart Email mail, @RequestPart MultipartFile content) throws MessagingException, IOException {
        mailService.sendHtmlMessageAttachment(mail, content);
        return new ResponseEntity<>("Attachment mail sent successfully", HttpStatus.OK);
    }
}
