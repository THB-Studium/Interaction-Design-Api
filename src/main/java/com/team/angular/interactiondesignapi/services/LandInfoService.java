package com.team.angular.interactiondesignapi.services;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.team.angular.interactiondesignapi.exception.ApiRequestException;
import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.models.LandInfo;
import com.team.angular.interactiondesignapi.repositories.LandInfoRepository;
import com.team.angular.interactiondesignapi.repositories.LandRepository;
import com.team.angular.interactiondesignapi.transfertobjects.landInfo.LandInfo2LandInfoReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.landInfo.LandInfo2LandInfoReadWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.landInfo.LandInfoReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.landInfo.LandInfoReadWriteTO;

@Service
public class LandInfoService {
	private static final Logger log = LoggerFactory.getLogger(LandInfoService.class);
	@Autowired
	private LandInfoRepository landInfoRepository;

	@Autowired
	private LandRepository landRepository;

	public LandInfoReadWriteTO getLandInfo(UUID id) {
		LandInfo landInfo = findLandInfo(id);
		return LandInfo2LandInfoReadWriteTO.apply(landInfo);
	}

	public List<LandInfoReadListTO> getAll(Integer pageNo, Integer pageSize, String sortBy) {

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<LandInfo> pagedResult = landInfoRepository.findAll(paging);

		return LandInfo2LandInfoReadListTO.apply(pagedResult.getContent());
	}

	public LandInfoReadWriteTO addLandInfo(LandInfoReadWriteTO landInfo) {
		LandInfo _landInfo = new LandInfo();

		if (!landInfoRepository.existsLandInfoByTitel(landInfo.getTitel())) {
			_landInfo.setTitel(landInfo.getTitel());
		} else {
			throw new ApiRequestException(landInfo.getTitel() + " already exists");
		}

		if (landInfo.getDescription() != null)
			_landInfo.setDescription(landInfo.getDescription());
		if (landInfo.getLandId() != null)
			_landInfo.setLand(landRepository.getById(landInfo.getLandId()));
		if (landInfo.getLandId() != null) {
			Land land = landRepository.findById(landInfo.getLandId())
					.orElseThrow(() -> new ApiRequestException("Cannot find Land with id: " + landInfo.getLandId()));
			_landInfo.setLand(land);
		}

		return LandInfo2LandInfoReadWriteTO.apply(landInfoRepository.save(_landInfo));
	}

	public ResponseEntity<?> deleteLandInfo(UUID id) {
		LandInfo actual = findLandInfo(id);

		landInfoRepository.deleteById(actual.getId());
		log.info("LandInfo successfully deleted");

		return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
	}

	public LandInfoReadListTO updateLandInfo(LandInfoReadListTO landInfo) {
		LandInfo _landInfo = findLandInfo(landInfo.getId());

		if (landInfo.getTitel() != null && !_landInfo.getTitel().equals(landInfo.getTitel())) {
			if (!landInfoRepository.existsLandInfoByTitel(landInfo.getTitel())) {
				_landInfo.setTitel(landInfo.getTitel());
			} else {
				throw new ApiRequestException(landInfo.getTitel() + " already exists");
			}
		}
		if (landInfo.getDescription() != null)
			_landInfo.setDescription(landInfo.getDescription());

		landInfoRepository.save(_landInfo);

		return LandInfo2LandInfoReadListTO.apply(_landInfo);
	}

	public LandInfo findLandInfo(UUID id) {
		return landInfoRepository.findById(id)
				.orElseThrow(() -> new ApiRequestException("Cannot find LandInfo with id: " + id));
	}
}
