package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import com.team.angular.interactiondesignapi.repositories.ReiseAngebotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public class ReiseAngebotService {
    private static final Logger log = LoggerFactory.getLogger(ReiseAngebotService.class);
    @Autowired
    private ReiseAngebotRepository reiseAngebotRepository;

    public List<ReiseAngebot> getAll() {
        return reiseAngebotRepository.findAll();
    }

    public ReiseAngebot addReiseAngebot(ReiseAngebot reiseAngebot) {
        return reiseAngebotRepository.save(reiseAngebot);
    }

    public ReiseAngebot getReiseAngebot(UUID id) {
        return reiseAngebotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find ReiseAngebot with id: " + id));
    }

    public ResponseEntity<?> deleteReiseAngebot(UUID id) {
        ReiseAngebot actual = reiseAngebotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find ReiseAngebot with id: " + id));

        reiseAngebotRepository.deleteById(actual.getId());
        log.info("ReiseAngebot successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

    // Update mit Bild
    public ReiseAngebot updateReiseAngebot(ReiseAngebot reiseAngebot, MultipartFile bild) {
        return reiseAngebot;
    }

}
