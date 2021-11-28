package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Highlight;
import com.team.angular.interactiondesignapi.repositories.HighlightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public class HighlightService {
    private static final Logger log = LoggerFactory.getLogger(HighlightService.class);
    @Autowired
    private HighlightRepository highlightRepository;

    public List<Highlight> getAll() {
        return highlightRepository.findAll();
    }

    public Highlight addHighlight(Highlight highlight) {
        return highlightRepository.save(highlight);
    }

    public Highlight getHighlight(UUID id) {
        return highlightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Highlight with id: " + id));
    }

    public ResponseEntity<?> deleteHighlight(UUID id) {
        Highlight actual = highlightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Highlight with id: " + id));

        highlightRepository.deleteById(actual.getId());
        log.info("Highlight successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }
}
