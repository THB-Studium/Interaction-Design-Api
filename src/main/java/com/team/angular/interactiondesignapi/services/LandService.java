package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.config.Helper;
import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.repositories.LandRepository;
import com.team.angular.interactiondesignapi.repositories.ReiseAngebotRepository;
import com.team.angular.interactiondesignapi.transfertobjects.land.Land2LandReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.land.LandReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.land.LandWriteTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class LandService {

    private static final Logger log = LoggerFactory.getLogger(LandService.class);
    @Autowired
    private LandRepository landRepository;
    @Autowired
    private ReiseAngebotRepository reiseAngebotRepository;

    public List<LandReadTO> getAll() {
        return Land2LandReadTO.apply(landRepository.findAll());
    }

    public LandReadTO addLand(LandWriteTO land, MultipartFile bild) {

        Land newLand = new Land();
        newLand.setName(land.getName());
        newLand.setFlughafen(land.getFlughafen());
        newLand.setUnterkunft_text(land.getUnterkunft_text());
        newLand.setKarte_bild(Helper.convertMultiPartFileToByte(bild));

        return Land2LandReadTO.apply(landRepository.save(newLand));

    }

    public LandReadTO getLand(UUID id) {
        Land land = landRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Land with id: " + id));
        return Land2LandReadTO.apply(land);
    }

    public LandReadTO updateLand(LandWriteTO land, MultipartFile bild) {

        Land newLand = landRepository.findById(land.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Land with id: " + land.getId()));

        if (land.getName() != null)
            newLand.setName(land.getName());
        if (land.getFlughafen() != null)
            newLand.setFlughafen(land.getFlughafen());
        if (land.getUnterkunft_text() != null)
            newLand.setUnterkunft_text(land.getUnterkunft_text());
        if (bild != null)
            newLand.setKarte_bild(Helper.convertMultiPartFileToByte(bild));

        return Land2LandReadTO.apply(landRepository.save(newLand));
    }

    public ResponseEntity<?> deleteLand(UUID id) {
        Land actual = landRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Land with id: " + id));

        landRepository.deleteById(actual.getId());
        log.info("successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

}
