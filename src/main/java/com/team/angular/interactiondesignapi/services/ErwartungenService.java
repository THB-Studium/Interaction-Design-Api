package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Erwartungen;
import com.team.angular.interactiondesignapi.repositories.ErwartungenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public class ErwartungenService {
    private static final Logger log = LoggerFactory.getLogger(ErwartungenService.class);
    @Autowired
    private ErwartungenRepository erwartungenRepository;

    public List<Erwartungen> getAll() {
        return erwartungenRepository.findAll();
    }

    public Erwartungen addErwartungen(Erwartungen erwartungen) {
        return erwartungenRepository.save(erwartungen);
    }

    public Erwartungen getErwartungen(UUID id) {
        return erwartungenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Erwartungen with id: " + id));
    }

    public ResponseEntity<?> deleteErwartungen(UUID id) {
        Erwartungen actual = erwartungenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Erwartungen with id: " + id));

        erwartungenRepository.deleteById(actual.getId());
        log.info("Erwartungen successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }
}
