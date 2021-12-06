package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Land_info;
import com.team.angular.interactiondesignapi.repositories.LandRepository;
import com.team.angular.interactiondesignapi.repositories.Land_infoRepository;
import com.team.angular.interactiondesignapi.transfertobjects.land_info.Land_info2Land_infoReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.land_info.Land_info2Land_infoReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.land_info.Land_infoReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.land_info.Land_infoReadTO;
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

    @Autowired
    private LandRepository landRepository;

    public Land_infoReadTO getLand_info(UUID id) {
        Land_info land_info = land_infoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Land_info with id: " + id));
        return Land_info2Land_infoReadTO.apply(land_info);
    }

    public List<Land_infoReadListTO> getAll() {
        return Land_info2Land_infoReadListTO.apply(land_infoRepository.findAll());
    }

    public Land_infoReadTO addLand_info(Land_infoReadTO land_info) {
        Land_info _land_info = new Land_info();
        if (land_info.getTitel() != null)
            _land_info.setTitel(land_info.getTitel());
        if (land_info.getDescription() != null)
            _land_info.setDescription(land_info.getDescription());
        if (land_info.getLand_id() != null)
            _land_info.setLand(landRepository.getById(land_info.getLand_id()));

        return Land_info2Land_infoReadTO.apply(land_infoRepository.save(_land_info));
    }

    public ResponseEntity<?> deleteLand_info(UUID id) {
        Land_info actual = land_infoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Land_info with id: " + id));

        land_infoRepository.deleteById(actual.getId());
        log.info("Infos_land successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

    public Land_infoReadTO updateLand_info(Land_infoReadListTO land_infoReadListTO) {
        Land_info _land_info = land_infoRepository.findById(land_infoReadListTO.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Update Error: Cannot find Erwartungen with id: " + land_infoReadListTO.getId()));

        if (land_infoReadListTO.getTitel() != null)
            _land_info.setTitel(land_infoReadListTO.getTitel());
        if (land_infoReadListTO.getDescription() != null)
            _land_info.setDescription(land_infoReadListTO.getDescription());

        land_infoRepository.save(_land_info);

        return Land_info2Land_infoReadTO.apply(_land_info);
    }
}
