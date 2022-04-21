package com.team.angular.interactiondesignapi.controllers;

import com.team.angular.interactiondesignapi.models.Newsletter;
import com.team.angular.interactiondesignapi.services.NewsletterService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/newsletters")
@CrossOrigin(origins = "*")
public class NewsletterController {
    @Autowired
    protected NewsletterService newsletterService;

    @ApiOperation("Get All Newsletter")
    @GetMapping("")
    public List<Newsletter> getAllNewsletters() {
        return newsletterService.getAll();
    }

    @ApiOperation("Subscribe")
    @PostMapping("subscribe")
    public Newsletter subscribe(
            @ApiParam(name = "Newsletter", value = "Newsletter to add") @RequestBody Newsletter newsletter) {
        return newsletterService.addNewsletter(newsletter);
    }

    @ApiOperation("Unsubscribe")
    @PutMapping("unsubscribe/{id}")
    public ResponseEntity<?> unsubscribe(
            @ApiParam(name = "NewsletterID", value = "ID of the Newsletter") @PathVariable UUID id) {
        return newsletterService.unsubscribed(id);
    }

    @ApiOperation("Update")
    @PutMapping("")
    public Newsletter updateNewsletter(
            @ApiParam(name = "Newsletter", value = "Newsletter to update") @RequestBody Newsletter newsletter) {
        return newsletterService.updateNewsletter(newsletter);
    }

    @ApiOperation("Delete Newsletter_obj")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNewsletter(@ApiParam(name = "NewsletterID", value = "ID of the Newsletter") @PathVariable UUID id) {
        return newsletterService.deleteNewsletter(id);
    }

    @ApiOperation("Get All abonniert")
    @GetMapping("/listabonniert")
    public List<String> getAllAbonniert() {
        return newsletterService.getAllAbonniert();
    }
    
}
