package com.team.angular.interactiondesignapi.controllers;

import com.team.angular.interactiondesignapi.models.Email;
import com.team.angular.interactiondesignapi.services.MailService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    @ApiOperation("Send simple email without attachment")
    @PostMapping(value = "/simple-email")
    public ResponseEntity<?> sendAttachmentEmail(
            @ApiParam(name = "Email", value = "Email to send") @RequestPart Email mail) {
        return mailService.sendHtmlMessage(mail);
    }

    @ApiOperation("Send email with attachment")
    @PostMapping(value = "/attachment")
    public ResponseEntity<?> sendAttachmentEmail(@ApiParam(name = "Email", value = "Email to send") @RequestPart Email mail,
                                                 @ApiParam(name = "Files", value = "Liste of attachment") @RequestPart List<MultipartFile> files) {
        return mailService.sendHtmlMessageAttachment(mail, files);
    }
}
