package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.models.LandInfo;
import com.team.angular.interactiondesignapi.repositories.LandInfoRepository;
import com.team.angular.interactiondesignapi.repositories.LandRepository;
import com.team.angular.interactiondesignapi.transfertobjects.landInfo.LandInfo2LandInfoReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.landInfo.LandInfo2LandInfoReadWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.landInfo.LandInfoReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.landInfo.LandInfoReadWriteTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LandInfoService {
    private static final Logger log = LoggerFactory.getLogger(LandInfoService.class);
    @Autowired
    private LandInfoRepository landInfoRepository;

    @Autowired
    private LandRepository landRepository;

    public LandInfoReadWriteTO getLandInfo(UUID id) {
        LandInfo landInfo = landInfoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find LandInfo with id: " + id));
        return LandInfo2LandInfoReadWriteTO.apply(landInfo);
    }

    public List<LandInfoReadListTO> getAll() {
        return LandInfo2LandInfoReadListTO.apply(landInfoRepository.findAll());
    }

    public LandInfoReadWriteTO addLandInfo(LandInfoReadWriteTO landInfo) throws Exception {
        LandInfo _landInfo = new LandInfo();

        if (!landInfoRepository.existsLandInfoByTitel(landInfo.getTitel())) {
            _landInfo.setTitel(landInfo.getTitel());
        } else {
            throw new Exception(landInfo.getTitel() + " already exists");
        }

        if (landInfo.getDescription() != null)
            _landInfo.setDescription(landInfo.getDescription());
        if (landInfo.getLandId() != null)
            _landInfo.setLand(landRepository.getById(landInfo.getLandId()));
        if (landInfo.getLandId() != null) {
            Land land = landRepository.findById(landInfo.getLandId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cannot find Land with id: " + landInfo.getLandId()));
            _landInfo.setLand(land);
        }

        return LandInfo2LandInfoReadWriteTO.apply(landInfoRepository.save(_landInfo));
    }

    public ResponseEntity<?> deleteLandInfo(UUID id) {
        LandInfo actual = landInfoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find LandInfo with id: " + id));

        landInfoRepository.deleteById(actual.getId());
        log.info("LandInfo successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

    public LandInfoReadListTO updateLandInfo(LandInfoReadListTO landInfo) throws Exception {
        LandInfo _landInfo = landInfoRepository.findById(landInfo.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Update Error: Cannot find LandInfo with id: " + landInfo.getId()));

        if (landInfo.getTitel() != null) {
            if (!landInfoRepository.existsLandInfoByTitel(landInfo.getTitel())) {
                _landInfo.setTitel(landInfo.getTitel());
            } else {
                throw new Exception(landInfo.getTitel() + " already exists");
            }
        }
        if (landInfo.getDescription() != null)
            _landInfo.setDescription(landInfo.getDescription());

        landInfoRepository.save(_landInfo);

        return LandInfo2LandInfoReadListTO.apply(_landInfo);
    }
}
