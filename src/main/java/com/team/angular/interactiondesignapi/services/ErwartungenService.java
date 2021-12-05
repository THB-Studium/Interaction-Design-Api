package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Erwartungen;
import com.team.angular.interactiondesignapi.repositories.ErwartungenRepository;
import com.team.angular.interactiondesignapi.transfertobjects.erwartungen.Erwartungen2ErwartungenReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.erwartungen.Erwartungen2ErwartungenReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.erwartungen.ErwartungenReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.erwartungen.ErwartungenReadTO;
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

    public ErwartungenReadTO getErwartungen(UUID id) {
        Erwartungen erwartungen = erwartungenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Erwartungen with id: " + id));
        return Erwartungen2ErwartungenReadTO.apply(erwartungen);
    }

    public List<ErwartungenReadListTO> getAll() {
        return Erwartungen2ErwartungenReadListTO.apply(erwartungenRepository.findAll());
    }

    public Erwartungen addErwartungen(Erwartungen erwartungen) {
        return erwartungenRepository.save(erwartungen);
    }

    public ResponseEntity<?> deleteErwartungen(UUID id) {
        Erwartungen actual = erwartungenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Erwartungen with id: " + id));

        erwartungenRepository.deleteById(actual.getId());
        log.info("Erwartungen successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

    public Erwartungen updateErwartungen(Erwartungen erwartungen) {

        Erwartungen _erwartungen = erwartungenRepository.findById(erwartungen.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Update Error: Cannot find Erwartungen with id: " + erwartungen.getId()));

        if (erwartungen.getAbenteuer() != 0)
            _erwartungen.setAbenteuer(erwartungen.getAbenteuer());
        if (erwartungen.getEntschleunigung() != 0)
            _erwartungen.setEntschleunigung(erwartungen.getEntschleunigung());
        if (erwartungen.getKonfort() != 0)
            _erwartungen.setKonfort(erwartungen.getKonfort());
        if (erwartungen.getNachhaltigkeit() != 0)
            _erwartungen.setKonfort(erwartungen.getNachhaltigkeit());
        if (erwartungen.getSonne_strand() != 0)
            _erwartungen.setSonne_strand(erwartungen.getSonne_strand());
        if (erwartungen.getSicherheit() != 0)
            _erwartungen.setSicherheit(erwartungen.getSicherheit());
        if (erwartungen.getRoad() != 0)
            _erwartungen.setRoad(erwartungen.getRoad());

        /* ReiseAngebot soll nicht von hier aktualisiert werden */
        return _erwartungen;
    }
}
