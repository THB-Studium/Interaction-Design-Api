package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ApiRequestException;
import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.repositories.LandRepository;
import com.team.angular.interactiondesignapi.repositories.ReiseAngebotRepository;
import com.team.angular.interactiondesignapi.transfertobjects.land.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.team.angular.interactiondesignapi.config.CompressImage.compressBild;

@Service
public class LandService {

    private static final Logger log = LoggerFactory.getLogger(LandService.class);
    @Autowired
    private LandRepository landRepository;
    @Autowired
    private ReiseAngebotRepository reiseAngebotRepository;

    public List<LandReadListTO> getAll() {
        return Land2LandReadListTO.apply(landRepository.findAll());
    }

    public LandReadTO addLand(LandWriteTO land) throws Exception {

        Land newLand = new Land();

        if (!landRepository.existsLandByName(land.getName())) {
            newLand.setName(land.getName());
        } else {
            throw new Exception(land.getName() + " already exists");
        }

        newLand.setFlughafen(land.getFlughafen());
        newLand.setUnterkunft_text(land.getUnterkunft_text());

        if (land.getHeaderFarbe() != null)
            newLand.setHeaderFarbe(land.getHeaderFarbe());
        if (land.getBodyFarbe() != null)
            newLand.setBodyFarbe(land.getBodyFarbe());

        if (land.getImage() != null)
            newLand.setKarte_bild(compressBild(land.getImage()));

        return Land2LandReadTO.apply(landRepository.save(newLand));
    }

    public LandReadTO getLand(UUID id) {
        Land land = landRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Land with id: " + id));
        return Land2LandReadTO.apply(land);
    }

    public LandReadTO updateLand(LandWriteTO land) throws Exception {
        Land newLand = landRepository.findById(land.getId())
                .orElseThrow(() -> new ApiRequestException("Cannot find Land with id: " + land.getId()));

        if (land.getName() != null && !land.getName().equals(newLand.getName()))
            if (!landRepository.existsLandByName(land.getName())) {
                newLand.setName(land.getName());
            } else {
                throw new Exception(land.getName() + " already exists");
            }

        if (land.getHeaderFarbe() != null)
            newLand.setHeaderFarbe(land.getHeaderFarbe());
        if (land.getBodyFarbe() != null)
            newLand.setBodyFarbe(land.getBodyFarbe());
        if (land.getFlughafen() != null)
            newLand.setFlughafen(land.getFlughafen());
        if (land.getUnterkunft_text() != null)
            newLand.setUnterkunft_text(land.getUnterkunft_text());
        if (land.getImage() != null)
            newLand.setKarte_bild(compressBild(land.getImage()));

        return Land2LandReadTO.apply(landRepository.save(newLand));
    }

    public ResponseEntity<?> deleteLand(UUID id) {
        Land actual = landRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Land with id: " + id));

        landRepository.deleteById(actual.getId());
        log.info("successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

}
