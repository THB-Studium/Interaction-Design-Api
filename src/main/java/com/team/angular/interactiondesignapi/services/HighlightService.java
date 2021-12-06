package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.config.Helper;
import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Highlight;
import com.team.angular.interactiondesignapi.repositories.HighlightRepository;
import com.team.angular.interactiondesignapi.transfertobjects.hightlights.Highlight2HighlightReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.hightlights.Highlight2HighlightReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.hightlights.HighlightReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.hightlights.HighlightReadTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public class HighlightService {
    private static final Logger log = LoggerFactory.getLogger(HighlightService.class);
    @Autowired
    private HighlightRepository highlightRepository;

    public HighlightReadTO getHighlight(UUID id) {
        Highlight highlight = highlightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Highlight with id: " + id));
        return Highlight2HighlightReadTO.apply(highlight);
    }

    public List<HighlightReadListTO> getAll() {
        return Highlight2HighlightReadListTO.apply(highlightRepository.findAll());
    }

    public HighlightReadTO addHighlight(HighlightReadTO highlight, MultipartFile bild) {
        Highlight _highlight = new Highlight();
        if (highlight.getName() != null)
            _highlight.setName(highlight.getName());
        if (highlight.getDescription() != null)
            _highlight.setDescription(highlight.getDescription());
        if (bild != null)
        _highlight.setBild(Helper.convertMultiPartFileToByte(bild));
        return  Highlight2HighlightReadTO.apply(highlightRepository.save(_highlight));
    }

    public ResponseEntity<?> deleteHighlight(UUID id) {
        Highlight actual = highlightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Highlight with id: " + id));

        highlightRepository.deleteById(actual.getId());
        log.info("Highlight successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

    public HighlightReadTO updateHighlight(HighlightReadListTO highlight, MultipartFile bild) {
        Highlight _highlight = highlightRepository.findById(highlight.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Update Error: Cannot find Highlight with id: " + highlight.getId()));

        if (highlight.getName() != null)
            _highlight.setName(highlight.getName());
        if (highlight.getDescription() != null)
            _highlight.setDescription(highlight.getDescription());
        if (bild != null)
            _highlight.setBild(Helper.convertMultiPartFileToByte(bild));

        return Highlight2HighlightReadTO.apply(highlightRepository.save(_highlight));
    }
}
