package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Erwartungen;
import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import com.team.angular.interactiondesignapi.repositories.ErwartungenRepository;
import com.team.angular.interactiondesignapi.repositories.ReiseAngebotRepository;
import com.team.angular.interactiondesignapi.transfertobjects.erwartungen.Erwartungen2ErwartungenReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.erwartungen.Erwartungen2ErwartungenReadWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.erwartungen.ErwartungenReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.erwartungen.ErwartungenReadWriteTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ErwartungenService {
    private static final Logger log = LoggerFactory.getLogger(ErwartungenService.class);
    @Autowired
    private ErwartungenRepository erwartungenRepository;
    @Autowired
    private ReiseAngebotRepository reiseAngebotRepository;

    public ErwartungenReadWriteTO getErwartungen(UUID id) {
        Erwartungen erwartungen = erwartungenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Erwartungen with id: " + id));
        return Erwartungen2ErwartungenReadWriteTO.apply(erwartungen);
    }

    public List<ErwartungenReadListTO> getAll() {
        return Erwartungen2ErwartungenReadListTO.apply(erwartungenRepository.findAll());
    }

    public ErwartungenReadWriteTO addErwartungen(ErwartungenReadWriteTO erwartungen) {
        Erwartungen _erwartungen = new Erwartungen();
        if (erwartungen.getAbenteuer() != 0)
            _erwartungen.setAbenteuer(erwartungen.getAbenteuer());
        if (erwartungen.getEntschleunigung() != 0)
            _erwartungen.setEntschleunigung(erwartungen.getEntschleunigung());
        if (erwartungen.getKonfort() != 0)
            _erwartungen.setKonfort(erwartungen.getKonfort());
        if (erwartungen.getNachhaltigkeit() != 0)
            _erwartungen.setNachhaltigkeit(erwartungen.getNachhaltigkeit());
        if (erwartungen.getSonne_strand() != 0)
            _erwartungen.setSonne_strand(erwartungen.getSonne_strand());
        if (erwartungen.getSicherheit() != 0)
            _erwartungen.setSicherheit(erwartungen.getSicherheit());
        if (erwartungen.getRoad() != 0)
            _erwartungen.setRoad(erwartungen.getRoad());
        if (erwartungen.getReiseAngebotId() != null) {
            ReiseAngebot reiseAngebot = reiseAngebotRepository.findById(erwartungen.getReiseAngebotId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cannot find ReiseAngebot with id: " + erwartungen.getReiseAngebotId()));

            _erwartungen.setReiseAngebot(reiseAngebot);
        }
        return Erwartungen2ErwartungenReadWriteTO.apply(erwartungenRepository.save(_erwartungen));
    }

    public ResponseEntity<?> deleteErwartungen(UUID id) {
        Erwartungen actual = erwartungenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Erwartungen with id: " + id));

        erwartungenRepository.deleteById(actual.getId());
        log.info("Erwartungen successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

    public ErwartungenReadListTO updateErwartungen(ErwartungenReadListTO erwartungen) {

        Erwartungen _erwartungen = erwartungenRepository.findById(erwartungen.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Update Error: Cannot find Erwartungen with id: " + erwartungen.getId()));

        if (erwartungen.getAbenteuer() != 0)
            _erwartungen.setAbenteuer(erwartungen.getAbenteuer());
        if (erwartungen.getEntschleunigung() != 0)
            _erwartungen.setEntschleunigung(erwartungen.getEntschleunigung());
        if (erwartungen.getKonfort() != 0)
            _erwartungen.setKonfort(erwartungen.getKonfort());
        if (erwartungen.getNachhaltigkeit() != 0)
            _erwartungen.setNachhaltigkeit(erwartungen.getNachhaltigkeit());
        if (erwartungen.getSonne_strand() != 0)
            _erwartungen.setSonne_strand(erwartungen.getSonne_strand());
        if (erwartungen.getSicherheit() != 0)
            _erwartungen.setSicherheit(erwartungen.getSicherheit());
        if (erwartungen.getRoad() != 0)
            _erwartungen.setRoad(erwartungen.getRoad());

        erwartungenRepository.save(_erwartungen);

        return Erwartungen2ErwartungenReadListTO.apply(_erwartungen);
    }
}
