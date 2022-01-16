package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Buchung;
import com.team.angular.interactiondesignapi.models.Buchungsklassen;
import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import com.team.angular.interactiondesignapi.models.Reiser;
import com.team.angular.interactiondesignapi.repositories.BuchungRepository;
import com.team.angular.interactiondesignapi.repositories.BuchungsklassenRepository;
import com.team.angular.interactiondesignapi.repositories.ReiseAngebotRepository;
import com.team.angular.interactiondesignapi.repositories.ReiserRepository;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.Buchung2BuchungReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungUpdateTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.reiser.ReiserRead2ReiserTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BuchungService {

    private static final Logger log = LoggerFactory.getLogger(BuchungService.class);
    @Autowired
    private BuchungRepository buchungRepository;
    @Autowired
    private BuchungsklassenRepository buchungsklassenRepository;
    @Autowired
    private ReiserRepository reiserRepository;
    @Autowired
    private ReiseAngebotRepository reiseAngebotRepository;
    @Autowired
    private ReiserService reiserService;

    public List<BuchungReadTO> getAll() {
        return Buchung2BuchungReadTO.apply(buchungRepository.findAll());
    }

    public BuchungReadTO addBuchung(BuchungWriteTO buchung) throws Exception {

        Buchungsklassen tarif = buchungsklassenRepository.findById(buchung.getBuchungsklasseId()).orElseThrow
                (() -> new ResourceNotFoundException("Cannot find buchungsklasse with id: " + buchung.getBuchungsklasseId()));

        // todo: pourquoi convertir d'abord?
        Reiser reiser = ReiserRead2ReiserTO.apply(reiserService.addReiser(buchung.getReiser()));
        //Reiser reiser = buchung.getReiser();

        //Reiser mitReiser = ReiserRead2ReiserTO.apply(reiserService.addReiser(buchung.getMitReiser()));

        ReiseAngebot ra = reiseAngebotRepository.findById(buchung.getReiseAngebotId()).orElseThrow(
                () -> new ResourceNotFoundException("Cannot find ReiseAngebot with id" + buchung.getReiseAngebotId()));

        Buchung newBuchung = new Buchung();
        newBuchung.setDatum(buchung.getDatum());
        if (buchung.getMitReiser() != null) {
            newBuchung.setMitReiserId(buchung.getMitReiser().getId());
        }
        newBuchung.setBuchungsklasseId(tarif.getId());
        newBuchung.setFlugHafen(buchung.getFlugHafen());
        newBuchung.setHandGepaeck(buchung.getHandGepaeck());
        newBuchung.setKoffer(buchung.getKoffer());
        newBuchung.setZahlungMethod(buchung.getZahlungMethod());
        newBuchung.setReiser(reiser);
        newBuchung.setReiseAngebot(ra);

        //update freiPlaetze after a new Buchung
        if(ra.getFreiPlaetze() >0){
            ra.setFreiPlaetze(ra.getFreiPlaetze() - 1);
            reiseAngebotRepository.save(ra);
        }else{
            throw new Exception("The trip is fully booked");
        }


        return Buchung2BuchungReadTO.apply(buchungRepository.save(newBuchung));

    }

    public BuchungReadTO getBuchung(UUID id) {

        Buchung buchung = buchungRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Buchung with id: " + id));

        return Buchung2BuchungReadTO.apply(buchung);
    }

    public BuchungReadTO updateBuchung(BuchungUpdateTO buchung) {

        Buchungsklassen tarif = null;
        Reiser reiser = null;
        Reiser mitReiser = null;
        ReiseAngebot ra = null;

        Buchung actual = buchungRepository.findById(buchung.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Buchung with id: " + buchung.getId()));

        if (buchung.getBuchungsklasseId() != null) {
            tarif = buchungsklassenRepository.findById(buchung.getBuchungsklasseId()).orElseThrow
                    (() -> new ResourceNotFoundException("Cannot find buchungsklasse with id: " + buchung.getBuchungsklasseId()));
            actual.setBuchungsklasseId(tarif.getId());
        }

        if (buchung.getReiserId() != null) {
            reiser = reiserRepository.findById(buchung.getReiserId()).orElseThrow(
                    () -> new ResourceNotFoundException("Cannot find Reiser with id: " + buchung.getReiserId()));
            actual.setReiser(reiser);
        }

        if (buchung.getMitReiserId() != null) {
            mitReiser = reiserRepository.findById(buchung.getMitReiserId()).orElseThrow(
                    () -> new ResourceNotFoundException("Cannot find MitReiser with id: " + buchung.getMitReiserId()));
            actual.setMitReiserId(mitReiser.getId());
        }

        if (buchung.getDatum() != null)
            actual.setDatum(buchung.getDatum());
        if (buchung.getFlugHafen() != null)
            actual.setFlugHafen(buchung.getFlugHafen());
        if (buchung.getHandGepaeck() != null)
            actual.setHandGepaeck(buchung.getHandGepaeck());
        if (buchung.getKoffer() != null)
            actual.setKoffer(buchung.getKoffer());
        if (buchung.getZahlungMethod() != null)
            actual.setZahlungMethod(buchung.getZahlungMethod());
        if (buchung.getReiseAngebotId() != null) {
            ra = reiseAngebotRepository.findById(buchung.getReiseAngebotId()).orElseThrow(
                    () -> new ResourceNotFoundException("Cannot find ReiseAngebot with id" + buchung.getReiseAngebotId()));
            actual.setReiseAngebot(ra);
        }

        return Buchung2BuchungReadTO.apply(buchungRepository.save(actual));
    }

    //todo update increment freiplatze
    public ResponseEntity<?> deleteBuchung(UUID id) {
        Buchung actual = buchungRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Buchung with id: " + id));

        buchungRepository.deleteById(actual.getId());
        log.info("successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

}
