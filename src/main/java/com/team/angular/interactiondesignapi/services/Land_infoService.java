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
    private Land_infoRepository infos_landRepository;

    public List<Land_info> getAll() {
        return infos_landRepository.findAll();
    }

    public Land_info addInfos_land(Land_info infos_land) {
        return infos_landRepository.save(infos_land);
    }

    public Land_info getInfos_land(UUID id) {
        return infos_landRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Infos_land with id: " + id));
    }

    public ResponseEntity<?> deleteInfos_land(UUID id) {
        Land_info actual = infos_landRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Infos_land with id: " + id));

        infos_landRepository.deleteById(actual.getId());
        log.info("Infos_land successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }
}
