package com.team.angular.interactiondesignapi.controllers;

import com.team.angular.interactiondesignapi.models.Email;
import com.team.angular.interactiondesignapi.services.MailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/mail")
@CrossOrigin(origins = "*")
public class EmailController {

    private final MailService mailService;

    public EmailController(MailService mailService) {
        this.mailService = mailService;
    }
    
    @PostMapping(value = "/simple-email")
    public ResponseEntity<?> sendAttachmentEmail(@RequestPart Email mail) {
        return mailService.sendHtmlMessage(mail);
    }

    @PostMapping(value = "/attachment")
    public ResponseEntity<?> sendAttachmentEmail(@RequestPart Email mail, @RequestPart List<MultipartFile> content) {
        return mailService.sendHtmlMessageAttachment(mail, content);
    }
}
