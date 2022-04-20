package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ApiRequestException;
import com.team.angular.interactiondesignapi.models.Buchungsklassen;
import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import com.team.angular.interactiondesignapi.repositories.BuchungsklassenRepository;
import com.team.angular.interactiondesignapi.repositories.ReiseAngebotRepository;
import com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen.Buchungsklassen2BuchungsklassenReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen.Buchungsklassen2BuchungsklassenReadWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen.BuchungsklassenReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen.BuchungsklassenReadWriteTO;
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

    public BuchungsklassenReadWriteTO getBuchungsklassen(UUID id) {
        Buchungsklassen buchungsklassen = buchungsklassenRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Buchungsklassen with id: " + id));
        return Buchungsklassen2BuchungsklassenReadWriteTO.apply(buchungsklassen);
    }

    public List<BuchungsklassenReadListTO> getAll() {
        return Buchungsklassen2BuchungsklassenReadListTO.apply(buchungsklassenRepository.findAll());
    }

    public BuchungsklassenReadWriteTO addBuchungsklassen(BuchungsklassenReadWriteTO buchungsklassen) {
        Buchungsklassen _buchungsklassen = new Buchungsklassen();

        _buchungsklassen.setType(buchungsklassen.getType());

        if (buchungsklassen.getPreis() != 0)
            _buchungsklassen.setPreis(buchungsklassen.getPreis());
        if (!buchungsklassen.getDescription().isEmpty())
            _buchungsklassen.setDescription(buchungsklassen.getDescription());
        if (buchungsklassen.getReiseAngebotId() != null) {
            ReiseAngebot reiseAngebot = reiseAngebotRepository.findById(buchungsklassen.getReiseAngebotId())
                    .orElseThrow(() -> new ApiRequestException(
                            "Cannot find ReiseAngebot with id: " + buchungsklassen.getReiseAngebotId()));
            _buchungsklassen.setReiseAngebot(reiseAngebot);
        }

        return Buchungsklassen2BuchungsklassenReadWriteTO.apply(buchungsklassenRepository.save(_buchungsklassen));
    }

    public ResponseEntity<?> deleteBuchungsklassen(UUID id) {
        Buchungsklassen actual = buchungsklassenRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Buchungsklassen with id: " + id));

        buchungsklassenRepository.deleteById(actual.getId());
        log.info("Buchungsklassen successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

    public BuchungsklassenReadListTO updateBuchungsklassen(BuchungsklassenReadWriteTO buchungsklassen) {

        Buchungsklassen _buchungsklassen = buchungsklassenRepository.findById(buchungsklassen.getId()).orElseThrow(
                () -> new ApiRequestException("Cannot find Buchungsklassen with id: " + buchungsklassen.getId()));

        _buchungsklassen.setType(buchungsklassen.getType());

        if (buchungsklassen.getPreis() != 0)
            _buchungsklassen.setPreis(buchungsklassen.getPreis());
        if (!buchungsklassen.getDescription().isEmpty())
            _buchungsklassen.setDescription(buchungsklassen.getDescription());

        buchungsklassenRepository.save(_buchungsklassen);

        return Buchungsklassen2BuchungsklassenReadListTO.apply(_buchungsklassen);
    }
}
