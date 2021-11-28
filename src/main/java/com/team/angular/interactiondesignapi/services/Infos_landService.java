package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Infos_land;
import com.team.angular.interactiondesignapi.repositories.Infos_landRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public class Infos_landService {
    private static final Logger log = LoggerFactory.getLogger(Infos_landService.class);
    @Autowired
    private Infos_landRepository infos_landRepository;

    public List<Infos_land> getAll() {
        return infos_landRepository.findAll();
    }

    public Infos_land addInfos_land(Infos_land infos_land) {
        return infos_landRepository.save(infos_land);
    }

    public Infos_land getInfos_land(UUID id) {
        return infos_landRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Infos_land with id: " + id));
    }

    public ResponseEntity<?> deleteInfos_land(UUID id) {
        Infos_land actual = infos_landRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Infos_land with id: " + id));

        infos_landRepository.deleteById(actual.getId());
        log.info("Infos_land successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }
}
