package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Email;
import com.team.angular.interactiondesignapi.models.Newsletter;
import com.team.angular.interactiondesignapi.repositories.NewsletterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NewsletterService {
    private static final Logger log = LoggerFactory.getLogger(NewsletterService.class);

    @Autowired
    private NewsletterRepository newsletterRepository;

    @Autowired
    private MailService mailService;

    public List<Newsletter> getAll() {
        return newsletterRepository.findAll();
    }

    public Newsletter getNewsletter(UUID id) {
        return newsletterRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cannot find Newsletter with id: " + id));
    }

    public Newsletter addNewsletter(Newsletter newsletter) throws Exception {

        if (!newsletterRepository.existsNewsletterByEmail(newsletter.getEmail())) {
            newsletter.setStatus(true);
            return newsletterRepository.save(newsletter);
        } else {
            throw new Exception("This email is already subscribed");
        }
    }

    public ResponseEntity<?> deleteNewsletter(UUID id) {

        Newsletter newsletter = getNewsletter(id);
        newsletterRepository.deleteById(newsletter.getId());
        log.info("successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

    public ResponseEntity<?> unsubscribed(UUID id) {

        Newsletter _newsletter = getNewsletter(id);
        _newsletter.setStatus(false);
        newsletterRepository.save(_newsletter);

        return new ResponseEntity<>("Successfully unsubscribed", HttpStatus.OK);
    }

    public Newsletter updateNewsletter(Newsletter newsletter) throws Exception {
        Newsletter _newsletter = getNewsletter(newsletter.getId());

        if (newsletter.getEmail() != null && !newsletter.getEmail().equalsIgnoreCase(_newsletter.getEmail())) {
            if (newsletterRepository.existsNewsletterByEmailAndIdIsNot(newsletter.getEmail(), newsletter.getId())) {
                throw new Exception("This email is already subscribed");
            } else {
                _newsletter.setEmail(newsletter.getEmail());
            }
        }

        if (newsletter.isStatus() != _newsletter.isStatus())
            _newsletter.setStatus(newsletter.isStatus());

        return newsletterRepository.save(_newsletter);
    }

    public List<String> getAllAbonniert() {
        return newsletterRepository.findAllByStatusIsTrue();
    }

    public ResponseEntity<?> mailToAbonniert(Email email) {

        List<String> listAbonniert = getAllAbonniert();


        email.setRecipient(listAbonniert.toArray(new String[0]));
        email.setSubject("ee");
        email.setMessage("ww");

        mailService.sendMail(email);

        return new ResponseEntity<>("Successfully sent", HttpStatus.OK);
    }
}
