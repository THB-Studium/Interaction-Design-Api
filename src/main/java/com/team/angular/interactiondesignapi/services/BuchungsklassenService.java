package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Buchungsklassen;
import com.team.angular.interactiondesignapi.repositories.BuchungsklassenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public class BuchungsklassenService {
    private static final Logger log = LoggerFactory.getLogger(BuchungsklassenService.class);
    @Autowired
    private BuchungsklassenRepository buchungsklassenRepository;

    public List<Buchungsklassen> getAll() {
        return buchungsklassenRepository.findAll();
    }

    public Buchungsklassen addBuchungsklassen(Buchungsklassen buchungsklassen) {
        return buchungsklassenRepository.save(buchungsklassen);
    }

    public Buchungsklassen getBuchungsklassen(UUID id) {
        return buchungsklassenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Buchungsklassen with id: " + id));
    }

    public ResponseEntity<?> deleteBuchungsklassen(UUID id) {
        Buchungsklassen actual = buchungsklassenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Buchungsklassen with id: " + id));

        buchungsklassenRepository.deleteById(actual.getId());
        log.info("Buchungsklassen successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }
}
