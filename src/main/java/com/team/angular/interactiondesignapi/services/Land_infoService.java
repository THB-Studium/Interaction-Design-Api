package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Erwartungen;
import com.team.angular.interactiondesignapi.models.Land_info;
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

    public Land_infoReadTO getLand_info(UUID id) {
        Land_info land_info = land_infoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Land_info with id: " + id));
        return Land_info2Land_infoReadTO.apply(land_info);
    }

    public List<Land_infoReadListTO> getAll() {
        return Land_info2Land_infoReadListTO.apply(land_infoRepository.findAll());
    }

    public Land_info addLand_info(Land_info land_info) {
        return land_infoRepository.save(land_info);
    }

    public ResponseEntity<?> deleteLand_info(UUID id) {
        Land_info actual = land_infoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Land_info with id: " + id));

        land_infoRepository.deleteById(actual.getId());
        log.info("Infos_land successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

    public Land_infoReadListTO updateLand_info (Land_infoReadListTO land_infoReadListTO){
        Erwartungen _Land_info = land_infoRepository.findById(erwartungen.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Update Error: Cannot find Erwartungen with id: " + erwartungen.getId()));

        if (erwartungen.getAbenteuer() != 0)
            _erwartungen.setAbenteuer(erwartungen.getAbenteuer());
        return
    }
}
