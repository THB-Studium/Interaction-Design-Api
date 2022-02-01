package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Buchungsklassen;
import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import com.team.angular.interactiondesignapi.repositories.BuchungsklassenRepository;
import com.team.angular.interactiondesignapi.repositories.ErwartungenRepository;
import com.team.angular.interactiondesignapi.repositories.LandRepository;
import com.team.angular.interactiondesignapi.repositories.ReiseAngebotRepository;
import com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReiseAngebotService {
    private static final Logger log = LoggerFactory.getLogger(ReiseAngebotService.class);
    @Autowired
    private ReiseAngebotRepository reiseAngebotRepository;
    @Autowired
    private LandRepository landRepository;
    @Autowired
    private ErwartungenRepository erwartungenRepository;
    @Autowired
    private BuchungsklassenRepository buchungsklassenRepository;

    public ReiseAngebotReadTO getReiseAngebot(UUID id) {
        ReiseAngebot reiseAngebot = reiseAngebotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find ReiseAngebot with id: " + id));
        return ReiseAngebot2ReiseAngebotReadTO.apply(reiseAngebot);
    }

    public List<ReiseAngebotReadListTO> getAll() {
        return ReiseAngebot2ReiseAngebotReadListTO.apply(reiseAngebotRepository.findAll());
    }

    public List<ReiseAngebotHomeTO> getAllForHome() {
        return ReiseAngebot2ReiseAngebotHomeTO.apply(reiseAngebotRepository.findAll());
    }

    public ReiseAngebotReadTO addReiseAngebot(ReiseAngebotWriteTO reiseAngebot) throws Exception {
        ReiseAngebot _reiseAngebot = new ReiseAngebot();

        if (!reiseAngebotRepository.existsReiseAngebotByTitel(reiseAngebot.getTitel())) {
            _reiseAngebot.setTitel(reiseAngebot.getTitel());
        } else {
            throw new Exception(reiseAngebot.getTitel() + " already exists");
        }

        if (reiseAngebot.getStartbild() != null) {
            _reiseAngebot.setStartbild(Base64.decodeBase64(reiseAngebot.getStartbild().substring(22)));
        }

        if (reiseAngebot.getStartDatum() != null)
            _reiseAngebot.setStartDatum(reiseAngebot.getStartDatum());
        if (reiseAngebot.getEndDatum() != null)
            _reiseAngebot.setEndDatum(reiseAngebot.getEndDatum());
        if (reiseAngebot.getPlaetze() != 0)
            _reiseAngebot.setPlaetze(reiseAngebot.getPlaetze());
        if (reiseAngebot.getFreiPlaetze() != 0)
            _reiseAngebot.setFreiPlaetze(reiseAngebot.getFreiPlaetze());
        if (reiseAngebot.getInteressiert() != 0)
            _reiseAngebot.setInteressiert(reiseAngebot.getInteressiert());
        if (reiseAngebot.getAnmeldungsFrist() != null)
            _reiseAngebot.setAnmeldungsFrist(reiseAngebot.getAnmeldungsFrist());
        if (reiseAngebot.getLeistungen() != null)
            _reiseAngebot.setLeistungen(reiseAngebot.getLeistungen());
        if (reiseAngebot.getLeistungen() != null)
            _reiseAngebot.setHinweise(reiseAngebot.getHinweise());
        if (reiseAngebot.getLeistungen() != null)
            _reiseAngebot.setMitReiserBerechtigt(reiseAngebot.getMitReiserBerechtigt());
        if (reiseAngebot.getLeistungen() != null)
            _reiseAngebot.setSonstigeHinweise(reiseAngebot.getSonstigeHinweise());

        // Save Buchungsklassen
        if (reiseAngebot.getBuchungsklassen() != null) {
            List<Buchungsklassen> Buchungsklassenlist = reiseAngebot.getBuchungsklassen();
            buchungsklassenRepository.saveAll(Buchungsklassenlist);
            _reiseAngebot.setBuchungsklassen(Buchungsklassenlist);
        }

        // erwartungen
        if (reiseAngebot.getErwartungen() != null) {
            _reiseAngebot.setErwartungen(reiseAngebot.getErwartungen());
        }

        // Land
        if (reiseAngebot.getLandId() != null) {
            Land land = landRepository.findById(reiseAngebot.getLandId()).orElseThrow(
                    () -> new ResourceNotFoundException("Cannot find Land with id: " + reiseAngebot.getLandId()));
            _reiseAngebot.setLand(land);
        }
        // get saved ReiseAngebot
        ReiseAngebot savedReiseAngebot = reiseAngebotRepository.save(_reiseAngebot);

        // Buchungsklassen Update with ID of ReiseAngebot
        if (reiseAngebot.getBuchungsklassen() != null) {
            List<Buchungsklassen> Buchungsklassenlist = reiseAngebot.getBuchungsklassen();
            for (Buchungsklassen element : Buchungsklassenlist) {
                element.setReiseAngebot(savedReiseAngebot);
                buchungsklassenRepository.save(element);
            }
        }

        return ReiseAngebot2ReiseAngebotReadTO.apply(savedReiseAngebot);
    }

    public ResponseEntity<?> deleteReiseAngebot(UUID id) {
        ReiseAngebot actual = reiseAngebotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find ReiseAngebot with id: " + id));

        reiseAngebotRepository.deleteById(actual.getId());
        log.info("ReiseAngebot successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

    public ReiseAngebotReadTO updateReiseAngebot(ReiseAngebotWriteTO reiseAngebot) throws Exception {

        ReiseAngebot _reiseAngebot = reiseAngebotRepository.findById(reiseAngebot.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find ReiseAngebot with id: " + reiseAngebot.getId()));

        if (reiseAngebot.getTitel() != null && !reiseAngebot.getTitel().equals(_reiseAngebot.getTitel())) {
            if (!reiseAngebotRepository.existsReiseAngebotByTitel(reiseAngebot.getTitel())) {
                _reiseAngebot.setTitel(reiseAngebot.getTitel());
            } else {
                throw new Exception(reiseAngebot.getTitel() + " already exists");
            }
        }

        if (reiseAngebot.getStartbild() != null)
            _reiseAngebot.setStartbild(Base64.decodeBase64(reiseAngebot.getStartbild().substring(22)));
        if (reiseAngebot.getStartDatum() != null)
            _reiseAngebot.setStartDatum(reiseAngebot.getStartDatum());
        if (reiseAngebot.getEndDatum() != null)
            _reiseAngebot.setEndDatum(reiseAngebot.getEndDatum());
        if (reiseAngebot.getPlaetze() != 0)
            _reiseAngebot.setPlaetze(reiseAngebot.getPlaetze());
        if (reiseAngebot.getFreiPlaetze() != 0)
            _reiseAngebot.setFreiPlaetze(reiseAngebot.getFreiPlaetze());
        if (reiseAngebot.getInteressiert() != 0)
            _reiseAngebot.setInteressiert(reiseAngebot.getInteressiert());
        if (reiseAngebot.getAnmeldungsFrist() != null)
            _reiseAngebot.setAnmeldungsFrist(reiseAngebot.getAnmeldungsFrist());
        if (reiseAngebot.getLeistungen() != null)
            _reiseAngebot.setLeistungen(reiseAngebot.getLeistungen());
        if (reiseAngebot.getLeistungen() != null)
            _reiseAngebot.setHinweise(reiseAngebot.getHinweise());
        if (reiseAngebot.getLeistungen() != null)
            _reiseAngebot.setMitReiserBerechtigt(reiseAngebot.getMitReiserBerechtigt());
        if (reiseAngebot.getLeistungen() != null)
            _reiseAngebot.setSonstigeHinweise(reiseAngebot.getSonstigeHinweise());

        // erwartungen
        if (reiseAngebot.getErwartungen() != null) {
            _reiseAngebot.setErwartungen(reiseAngebot.getErwartungen());
        }

        // Land
        if (reiseAngebot.getLandId() != null) {
            Land land = landRepository.findById(reiseAngebot.getLandId()).orElseThrow(
                    () -> new ResourceNotFoundException("Cannot find Land with id: " + reiseAngebot.getLandId()));
            _reiseAngebot.setLand(land);
        }

        return ReiseAngebot2ReiseAngebotReadTO.apply(reiseAngebotRepository.save(_reiseAngebot));
    }

    public ResponseEntity<?> addInteressiert(UUID id) {
        ReiseAngebot reiseAngebot = reiseAngebotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find ReiseAngebot with id: " + id));

        reiseAngebot.setInteressiert(reiseAngebot.getInteressiert() + 1);
        reiseAngebotRepository.save(reiseAngebot);
        return new ResponseEntity<>("Successfully added", HttpStatus.OK);
    }

    public ResponseEntity<?> resetInteressiert(UUID id) {
        ReiseAngebot reiseAngebot = reiseAngebotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find ReiseAngebot with id: " + id));

        reiseAngebot.setInteressiert(0);
        reiseAngebotRepository.save(reiseAngebot);
        return new ResponseEntity<>("Successfully reset", HttpStatus.OK);
    }

    public ResponseEntity<?> uninteressiert(UUID id) {
        ReiseAngebot reiseAngebot = reiseAngebotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find ReiseAngebot with id: " + id));

        if (reiseAngebot.getInteressiert() >= 0) {
            reiseAngebot.setInteressiert(reiseAngebot.getInteressiert() - 1);
            reiseAngebotRepository.save(reiseAngebot);
        }
        return new ResponseEntity<>("Successfully updated", HttpStatus.OK);
    }

}
