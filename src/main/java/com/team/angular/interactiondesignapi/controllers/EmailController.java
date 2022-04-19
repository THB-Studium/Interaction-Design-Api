package com.team.angular.interactiondesignapi.controllers;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.team.angular.interactiondesignapi.models.Email;
import com.team.angular.interactiondesignapi.models.Mail;
import com.team.angular.interactiondesignapi.services.MailService;
import com.team.angular.interactiondesignapi.transfertobjects.reponse.MessageResponse;

@RestController
@RequestMapping("/mail")
@CrossOrigin(origins = "*")
public class EmailController {

	private final MailService mailService;

	public EmailController(MailService mailService) {
		this.mailService = mailService;
	}

	// since ResponseEntity just return a text, the produce and cosume fields was
	// added to specify that the response is as json

	@RequestMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = {
			RequestMethod.POST })
	public ResponseEntity<MessageResponse> sendMail(@RequestBody Mail mail) {

		mailService.sendMail(mail);
		MessageResponse resp = new MessageResponse("Email sent successfully");

		return new ResponseEntity<MessageResponse>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/simple-email", produces = MediaType.APPLICATION_JSON_VALUE, method = {
			RequestMethod.POST })
	public ResponseEntity<MessageResponse> sendAttachmentEmail(@RequestPart Email mail)
			throws MessagingException, IOException {
		mailService.sendHtmlMessage(mail);

		MessageResponse resp = new MessageResponse("Email sent successfully");

		return new ResponseEntity<MessageResponse>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/attachment", produces = MediaType.APPLICATION_JSON_VALUE, method = { RequestMethod.POST })
	public ResponseEntity<MessageResponse> sendAttachmentEmail(@RequestPart Email mail,
			@RequestPart MultipartFile content) throws MessagingException, IOException {
		mailService.sendHtmlMessageAttachment(mail, content);

		MessageResponse resp = new MessageResponse("Attachment mail sent successfully");

		return new ResponseEntity<MessageResponse>(resp, HttpStatus.OK);
	}
}
