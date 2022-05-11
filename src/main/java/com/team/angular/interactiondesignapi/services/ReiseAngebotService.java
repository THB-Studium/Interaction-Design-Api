package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ApiRequestException;
import com.team.angular.interactiondesignapi.models.Erwartungen;
import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import com.team.angular.interactiondesignapi.repositories.LandRepository;
import com.team.angular.interactiondesignapi.repositories.ReiseAngebotRepository;
import com.team.angular.interactiondesignapi.transfertobjects.erwartungen.ErwartungenWrite2ErwartungTO;
import com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.team.angular.interactiondesignapi.config.CompressImage.compressBild;

@Service
public class ReiseAngebotService {
    private static final Logger log = LoggerFactory.getLogger(ReiseAngebotService.class);

    @Autowired
    private ReiseAngebotRepository reiseAngebotRepository;
    @Autowired
    private LandRepository landRepository;

    public ReiseAngebotReadTO getReiseAngebot(UUID id) {
        ReiseAngebot reiseAngebot = reiseAngebotRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find ReiseAngebot with id: " + id));
        return ReiseAngebot2ReiseAngebotReadTO.apply(reiseAngebot);
    }

    public List<ReiseAngebotReadListTO> getAll(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<ReiseAngebot> pagedResult = reiseAngebotRepository.findAll(paging);

        return ReiseAngebot2ReiseAngebotReadListTO.apply(pagedResult.getContent());
    }

    //for the Homepage
    public List<ReiseAngebotHomeTO> getAllForHome() {
        return ReiseAngebot2ReiseAngebotHomeTO.apply(reiseAngebotRepository.findAll());
    }

    public ReiseAngebotReadTO addReiseAngebot(ReiseAngebotWriteTO reiseAngebot) {
        ReiseAngebot _reiseAngebot = new ReiseAngebot();

        if (!reiseAngebotRepository.existsReiseAngebotByTitel(reiseAngebot.getTitel())) {
            _reiseAngebot.setTitel(reiseAngebot.getTitel());
        } else {
            throw new ApiRequestException(reiseAngebot.getTitel() + " already exists");
        }

        if (reiseAngebot.getStartbild() != null)
            _reiseAngebot.setStartbild(compressBild(reiseAngebot.getStartbild()));
        if (reiseAngebot.getStartDatum() != null)
            _reiseAngebot.setStartDatum(reiseAngebot.getStartDatum());
        if (reiseAngebot.getEndDatum() != null)
            _reiseAngebot.setEndDatum(reiseAngebot.getEndDatum());

        // Plaetze and FreiPlaetze
        if (reiseAngebot.getPlaetze() != 0) {
            _reiseAngebot.setPlaetze(reiseAngebot.getPlaetze());
            _reiseAngebot.setFreiPlaetze(reiseAngebot.getPlaetze());
        }

        if (reiseAngebot.getInteressiert() != 0)
            _reiseAngebot.setInteressiert(reiseAngebot.getInteressiert());
        if (reiseAngebot.getAnmeldungsFrist() != null)
            _reiseAngebot.setAnmeldungsFrist(reiseAngebot.getAnmeldungsFrist());
        if (reiseAngebot.getLeistungen() != null)
            _reiseAngebot.setLeistungen(reiseAngebot.getLeistungen());
        if (reiseAngebot.getLeistungen() != null)
            _reiseAngebot.setHinweise(reiseAngebot.getHinweise());
        if (reiseAngebot.getLeistungen() != null)
            _reiseAngebot.setMitreiseberechtigt(reiseAngebot.getMitreiseberechtigt());
        if (reiseAngebot.getLeistungen() != null)
            _reiseAngebot.setSonstigeHinweise(reiseAngebot.getSonstigeHinweise());

        // erwartungen
        if (reiseAngebot.getErwartungen() != null) {
            _reiseAngebot.setErwartungen(ErwartungenWrite2ErwartungTO.apply(reiseAngebot.getErwartungen()));
        }

        // Land
        if (reiseAngebot.getLandId() != null) {
            Land land = landRepository.findById(reiseAngebot.getLandId()).orElseThrow(
                    () -> new ApiRequestException("Cannot find Land with id: " + reiseAngebot.getLandId()));
            _reiseAngebot.setLand(land);
        }

        // save
        return ReiseAngebot2ReiseAngebotReadTO.apply(reiseAngebotRepository.save(_reiseAngebot));
    }


    public ReiseAngebotReadTO updateReiseAngebot(ReiseAngebotWriteTO reiseAngebot) {

        ReiseAngebot _reiseAngebot = reiseAngebotRepository.findById(reiseAngebot.getId())
                .orElseThrow(() -> new ApiRequestException("Cannot find ReiseAngebot with id: " + reiseAngebot.getId()));

        if (reiseAngebot.getTitel() != null && !reiseAngebot.getTitel().equals(_reiseAngebot.getTitel())) {
            if (!reiseAngebotRepository.existsReiseAngebotByTitel(reiseAngebot.getTitel())) {
                _reiseAngebot.setTitel(reiseAngebot.getTitel());
            } else {
                throw new ApiRequestException(reiseAngebot.getTitel() + " already exists");
            }
        }

        if (reiseAngebot.getStartbild() != null)
            _reiseAngebot.setStartbild(compressBild(reiseAngebot.getStartbild()));
        if (reiseAngebot.getStartDatum() != null)
            _reiseAngebot.setStartDatum(reiseAngebot.getStartDatum());
        if (reiseAngebot.getEndDatum() != null)
            _reiseAngebot.setEndDatum(reiseAngebot.getEndDatum());
        if (reiseAngebot.getPlaetze() != 0)
            _reiseAngebot.setPlaetze(reiseAngebot.getPlaetze());
        if (reiseAngebot.getInteressiert() != 0) //todo need?
            _reiseAngebot.setInteressiert(reiseAngebot.getInteressiert());
        if (reiseAngebot.getAnmeldungsFrist() != null)
            _reiseAngebot.setAnmeldungsFrist(reiseAngebot.getAnmeldungsFrist());
        if (reiseAngebot.getLeistungen() != null)
            _reiseAngebot.setLeistungen(reiseAngebot.getLeistungen());
        if (reiseAngebot.getLeistungen() != null)
            _reiseAngebot.setHinweise(reiseAngebot.getHinweise());
        if (reiseAngebot.getLeistungen() != null)
            _reiseAngebot.setMitreiseberechtigt(reiseAngebot.getMitreiseberechtigt());
        if (reiseAngebot.getLeistungen() != null)
            _reiseAngebot.setSonstigeHinweise(reiseAngebot.getSonstigeHinweise());

        // Land
        if (reiseAngebot.getLandId() != null) {
            Land land = landRepository.findById(reiseAngebot.getLandId()).orElseThrow(
                    () -> new ApiRequestException("Cannot find Land with id: " + reiseAngebot.getLandId()));
            _reiseAngebot.setLand(land);
        }

        return ReiseAngebot2ReiseAngebotReadTO.apply(reiseAngebotRepository.save(_reiseAngebot));
    }

    public ResponseEntity<?> deleteReiseAngebot(UUID id) {
        ReiseAngebot actual = reiseAngebotRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find ReiseAngebot with id: " + id));

        reiseAngebotRepository.deleteById(actual.getId());
        log.info("ReiseAngebot successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

    public ResponseEntity<?> addInteressiert(UUID id) {
        ReiseAngebot reiseAngebot = reiseAngebotRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find ReiseAngebot with id: " + id));

        reiseAngebot.setInteressiert(reiseAngebot.getInteressiert() + 1);
        reiseAngebotRepository.save(reiseAngebot);
        return new ResponseEntity<>("Successfully added", HttpStatus.OK);
    }

    public ResponseEntity<?> resetInteressiert(UUID id) {
        ReiseAngebot reiseAngebot = reiseAngebotRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find ReiseAngebot with id: " + id));

        reiseAngebot.setInteressiert(0);
        reiseAngebotRepository.save(reiseAngebot);
        return new ResponseEntity<>("Successfully reset", HttpStatus.OK);
    }

    public ResponseEntity<?> uninteressiert(UUID id) {
        ReiseAngebot reiseAngebot = reiseAngebotRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find ReiseAngebot with id: " + id));

        if (reiseAngebot.getInteressiert() > 0) {
            reiseAngebot.setInteressiert(reiseAngebot.getInteressiert() - 1);
            reiseAngebotRepository.save(reiseAngebot);
        }
        return new ResponseEntity<>("Successfully updated", HttpStatus.OK);
    }

}
