package com.team.angular.interactiondesignapi.mail;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.team.angular.interactiondesignapi.ItBase;
import com.team.angular.interactiondesignapi.models.Email;
import com.team.angular.interactiondesignapi.services.MailService;


public class EmailSenderServiceTest extends ItBase {

    @Autowired
    private MailService emailSenderService;

    @Test
    public void sendHtmlMessageTest() throws MessagingException {
    	
    	String to[] = {"keunnema@th-brandenburg.de", "vodog99846@sartess.com"};
    	
        Email email = new Email();
        email.setTo(to);
        email.setFrom("keunne.baudoin@yahoo.fr");
        email.setSubject("Welcome Email from CodingNConcepts");
        email.setTemplate("welcome-email.html");
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", "Ashish");
        properties.put("subscriptionDate", LocalDate.now().toString());
        properties.put("technologies", Arrays.asList("Python", "Go", "C#"));
        
        email.setProperties(properties);
        
        System.out.println(email);
        System.out.println(properties);

        //Assertions.assertDoesNotThrow(() -> emailSenderService.sendHtmlMessage(email));
    }
}