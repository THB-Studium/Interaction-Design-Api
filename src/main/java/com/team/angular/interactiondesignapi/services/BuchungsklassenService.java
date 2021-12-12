package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Buchungsklassen;
import com.team.angular.interactiondesignapi.repositories.BuchungsklassenRepository;
import com.team.angular.interactiondesignapi.repositories.ReiseAngebotRepository;
import com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen.Buchungklasse2BuchungklasseReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen.Buchungsklassen2BuchungsklassenReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen.BuchungsklassenReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen.BuchungsklassenReadTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BuchungsklassenService {
    private static final Logger log = LoggerFactory.getLogger(BuchungsklassenService.class);
    @Autowired
    private BuchungsklassenRepository buchungsklassenRepository;
    @Autowired
    private ReiseAngebotRepository reiseAngebotRepository;

    public BuchungsklassenReadTO getBuchungsklassen(UUID id) {
        Buchungsklassen buchungsklassen = buchungsklassenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Buchungsklassen with id: " + id));
        return Buchungklasse2BuchungklasseReadTO.apply(buchungsklassen);
    }

    public List<BuchungsklassenReadListTO> getAll() {
        return Buchungsklassen2BuchungsklassenReadListTO.apply(buchungsklassenRepository.findAll());
    }

    public BuchungsklassenReadTO addBuchungsklassen(BuchungsklassenReadTO buchungsklassen) {
        Buchungsklassen _buchungsklassen = new Buchungsklassen();

        if (buchungsklassen.getType() != null)
            _buchungsklassen.setType(buchungsklassen.getType());
        if (buchungsklassen.getPreis() != 0)
            _buchungsklassen.setPreis(buchungsklassen.getPreis());
        if (buchungsklassen.getReiseAngebot_id() != null)
            _buchungsklassen.setReiseAngebot(reiseAngebotRepository.getById(buchungsklassen.getReiseAngebot_id()));

        return Buchungklasse2BuchungklasseReadTO.apply(buchungsklassenRepository.save(_buchungsklassen));
    }

    public ResponseEntity<?> deleteBuchungsklassen(UUID id) {
        Buchungsklassen actual = buchungsklassenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Buchungsklassen with id: " + id));

        buchungsklassenRepository.deleteById(actual.getId());
        log.info("Buchungsklassen successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

    public BuchungsklassenReadTO updateBuchungsklassen(BuchungsklassenReadListTO buchungsklassen) {

        Buchungsklassen _buchungsklassen = buchungsklassenRepository.findById(buchungsklassen.getId()).orElseThrow(()
                -> new ResourceNotFoundException("Cannot find Buchungsklassen with id: " + buchungsklassen.getId()));

        if (buchungsklassen.getType() != null)
            _buchungsklassen.setType(buchungsklassen.getType());
        if (buchungsklassen.getPreis() != 0)
            _buchungsklassen.setPreis(buchungsklassen.getPreis());

        buchungsklassenRepository.save(_buchungsklassen);

        return Buchungklasse2BuchungklasseReadTO.apply(_buchungsklassen);
    }
}
