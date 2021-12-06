package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.config.Helper;
import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Buchungsklassen;
import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import com.team.angular.interactiondesignapi.repositories.ReiseAngebotRepository;
import com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen.BuchungsklassenReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot.ReiseAngebot2ReiseAngebotReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot.ReiseAngebot2ReiseAngebotReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot.ReiseAngebotReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot.ReiseAngebotReadTO;
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

    public ReiseAngebotReadTO getReiseAngebot(UUID id) {
        ReiseAngebot reiseAngebot = reiseAngebotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find ReiseAngebot with id: " + id));
        return ReiseAngebot2ReiseAngebotReadTO.apply(reiseAngebot);
    }

    public List<ReiseAngebotReadListTO> getAll() {
        return ReiseAngebot2ReiseAngebotReadListTO.apply(reiseAngebotRepository.findAll());
    }

    public ReiseAngebotReadTO addReiseAngebot(ReiseAngebotReadTO reiseAngebot, MultipartFile bild) {
        ReiseAngebot _reiseAngebot = new ReiseAngebot();
        if (reiseAngebot.getTitel() != null)
            _reiseAngebot.setTitel(reiseAngebot.getTitel());
        if (bild != null)
            _reiseAngebot.setStartbild(Helper.convertMultiPartFileToByte(bild));
        if (reiseAngebot.getStartDatum() != null)
            _reiseAngebot.setStartDatum(reiseAngebot.getStartDatum());
        if (reiseAngebot.getEndDatum() != null)
            _reiseAngebot.setEndDatum(reiseAngebot.getEndDatum());
        if (reiseAngebot.getPlaetze() != 0)
            _reiseAngebot.setPlaetze(reiseAngebot.getPlaetze());
        if (reiseAngebot.getFreiPlaetze() != 0)
            _reiseAngebot.setFreiPlaetze(reiseAngebot.getFreiPlaetze());
        if (reiseAngebot.getAnmeldungsFrist() != null)
            _reiseAngebot.setAnmeldungsFrist(reiseAngebot.getAnmeldungsFrist());
        if (reiseAngebot.getLeistungen() != null)
            _reiseAngebot.setLeistungen(reiseAngebot.getLeistungen());

        // save buchungsklassenReadListTO as Buchungsklassen
        if (reiseAngebot.getBuchungsklassenReadListTO() != null) {
            List<Buchungsklassen> Buchungsklassenlist = null;

            for (BuchungsklassenReadListTO element : reiseAngebot.getBuchungsklassenReadListTO()) {
                Buchungsklassen temp = new Buchungsklassen();
                temp.setType(element.getType());
                temp.setPreis(element.getPreis());
                Buchungsklassenlist.add(temp);
            }
            _reiseAngebot.setBuchungsklassen(Buchungsklassenlist);
        }

        //erwartungenReadListTO
        //Land

        return ReiseAngebot2ReiseAngebotReadTO.apply(reiseAngebotRepository.save(_reiseAngebot));
    }

    public ResponseEntity<?> deleteReiseAngebot(UUID id) {
        ReiseAngebot actual = reiseAngebotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find ReiseAngebot with id: " + id));

        reiseAngebotRepository.deleteById(actual.getId());
        log.info("ReiseAngebot successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

    public ReiseAngebotReadTO updateReiseAngebot(ReiseAngebotReadListTO reiseAngebot, MultipartFile bild) {
        ReiseAngebot _reiseAngebot = reiseAngebotRepository.findById(reiseAngebot.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find ReiseAngebot with id: " + reiseAngebot.getId()));

        if (reiseAngebot.getTitel() != null)
            _reiseAngebot.setTitel(reiseAngebot.getTitel());
        if (bild != null)
            _reiseAngebot.setStartbild(Helper.convertMultiPartFileToByte(bild));
        if (reiseAngebot.getStartDatum() != null)
            _reiseAngebot.setStartDatum(reiseAngebot.getStartDatum());
        if (reiseAngebot.getEndDatum() != null)
            _reiseAngebot.setEndDatum(reiseAngebot.getEndDatum());
        if (reiseAngebot.getPlaetze() != 0)
            _reiseAngebot.setPlaetze(reiseAngebot.getPlaetze());
        if (reiseAngebot.getFreiPlaetze() != 0)
            _reiseAngebot.setFreiPlaetze(reiseAngebot.getFreiPlaetze());
        if (reiseAngebot.getAnmeldungsFrist() != null)
            _reiseAngebot.setAnmeldungsFrist(reiseAngebot.getAnmeldungsFrist());
        if (reiseAngebot.getLeistungen() != null)
            _reiseAngebot.setLeistungen(reiseAngebot.getLeistungen());

        reiseAngebotRepository.save(_reiseAngebot);

        return ReiseAngebot2ReiseAngebotReadTO.apply(_reiseAngebot);
    }

}
