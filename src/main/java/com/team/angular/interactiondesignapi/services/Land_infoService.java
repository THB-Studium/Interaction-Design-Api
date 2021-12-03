package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Land_info;
import com.team.angular.interactiondesignapi.repositories.Land_infoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public class Land_infoService {
    private static final Logger log = LoggerFactory.getLogger(Land_infoService.class);
    @Autowired
    private Land_infoRepository land_infoRepository;

    public List<Land_info> getAll() {
        return land_infoRepository.findAll();
    }

    public Land_info addLand_info(Land_info land_info) {
        return land_infoRepository.save(land_info);
    }

    public Land_info getLand_info(UUID id) {
        return land_infoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Land_info with id: " + id));
    }

    public ResponseEntity<?> deleteLand_info(UUID id) {
        Land_info actual = land_infoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Land_info with id: " + id));

        land_infoRepository.deleteById(actual.getId());
        log.info("Infos_land successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }
}
