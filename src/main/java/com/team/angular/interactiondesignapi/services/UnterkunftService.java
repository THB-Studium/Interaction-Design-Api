package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ApiRequestException;
import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.models.Unterkunft;
import com.team.angular.interactiondesignapi.repositories.LandRepository;
import com.team.angular.interactiondesignapi.repositories.UnterkunftRepository;
import com.team.angular.interactiondesignapi.transfertobjects.unterkunft.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.team.angular.interactiondesignapi.config.CompressImage.compressBild;

@Service
public class UnterkunftService {

    private static final Logger log = LoggerFactory.getLogger(UnterkunftService.class);
    @Autowired
    private UnterkunftRepository unterkunftRepository;
    @Autowired
    private LandRepository landRepository;

    public UnterkunftReadTO getUnterkunft(UUID id) {
        Unterkunft unterkunft = unterkunftRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Feedback with id: " + id));

        return Unterkunft2UnterkunftReadTO.apply(unterkunftRepository.save(unterkunft));
    }

    public List<UnterkunftReadListTO> getAll() {
        return Unterkunft2UnterkunftReadListTO.apply(unterkunftRepository.findAll());
    }

    public UnterkunftReadTO addUnterkunft(UnterkunftWriteTO unterkunft) {

        Unterkunft newUnterkunft = new Unterkunft();

        if (!unterkunftRepository.existsUnterkunftByName(unterkunft.getName())) {
            newUnterkunft.setName(unterkunft.getName());
        } else {
            throw new ApiRequestException(unterkunft.getName() + " already exists");
        }

        if (unterkunft.getBilder() != null) {
            List<byte[]> bilder = new ArrayList<>();
            for (String file : unterkunft.getBilder()) {
                bilder.add(compressBild(file));
            }
            newUnterkunft.setBilder(bilder);
        }

        newUnterkunft.setLink(unterkunft.getLink());
        newUnterkunft.setAdresse(unterkunft.getAdresse());
        newUnterkunft.setBeschreibung(unterkunft.getBeschreibung());

        if (unterkunft.getLandId() != null) {
            Land land = landRepository.findById(unterkunft.getLandId()).orElseThrow(
                    () -> new ApiRequestException("Cannot find Land with id: " + unterkunft.getLandId()));
            newUnterkunft.setLand(land);
        }

        return Unterkunft2UnterkunftReadTO.apply(unterkunftRepository.save(newUnterkunft));
    }

    public UnterkunftReadTO updateUnterkunft(UnterkunftWriteTO unterkunft) {

        Unterkunft actual_unterkunft = unterkunftRepository.findById(unterkunft.getId()).orElseThrow(
                () -> new ApiRequestException("Cannot find UpdateUnterkunft with id: " + unterkunft.getId()));

        if (unterkunft.getName() != null && !unterkunft.getName().equals(actual_unterkunft.getName())) {
            if (!unterkunftRepository.existsUnterkunftByName(unterkunft.getName())) {
                actual_unterkunft.setName(unterkunft.getName());
            } else {
                throw new ApiRequestException(unterkunft.getName() + " already exists");
            }
        }

        if (unterkunft.getBilder() != null && unterkunft.getBilder().size() > 0) {
            List<byte[]> bilder = new ArrayList<>();
            for (String file : unterkunft.getBilder()) {
                bilder.add(compressBild(file));
            }
            actual_unterkunft.setBilder(bilder);
        }

        if (unterkunft.getLink() != null)
            actual_unterkunft.setLink(unterkunft.getLink());
        if (unterkunft.getAdresse() != null)
            actual_unterkunft.setAdresse(unterkunft.getAdresse());
        if (unterkunft.getBeschreibung() != null)
            actual_unterkunft.setBeschreibung(unterkunft.getBeschreibung());

        if (unterkunft.getLandId() != null) {
            Land land = landRepository.findById(unterkunft.getLandId()).orElseThrow(
                    () -> new ApiRequestException("Cannot find Land with id: " + unterkunft.getLandId()));
            actual_unterkunft.setLand(land);
        }

        return Unterkunft2UnterkunftReadTO.apply(unterkunftRepository.save(actual_unterkunft));
    }

    public ResponseEntity<?> deleteUnterkunft(UUID id) {
        Unterkunft unterkunft = unterkunftRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Unterkunft with id: " + id));

        unterkunftRepository.delete(unterkunft);
        log.info("successfully deleted");
        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

}
