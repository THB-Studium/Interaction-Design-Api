package com.team.angular.interactiondesignapi.services;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.team.angular.interactiondesignapi.config.Helper;
import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import com.team.angular.interactiondesignapi.repositories.LandRepository;
import com.team.angular.interactiondesignapi.repositories.ReiseAngebotRepository;
import com.team.angular.interactiondesignapi.transfertobjects.land.Land2LandReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.land.Land2LandReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.land.LandReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.land.LandReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.land.LandWriteTO;

@Service
public class LandService {

	@Autowired
	private LandRepository landRepository;

	@Autowired
	private ReiseAngebotRepository reiseAngebotRepository;

	private static final Logger log = LoggerFactory.getLogger(LandService.class);

	public List<LandReadListTO> getAll() {
		return Land2LandReadListTO.apply(landRepository.findAll());
	}

	public LandReadTO addLand(LandWriteTO land, MultipartFile bild) {

		ReiseAngebot reiseAngebot = reiseAngebotRepository.findById(land.getReiseAngebotId())
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find ReiseAngebot with id: " + land.getReiseAngebotId()));

		Land newLand = new Land();
		newLand.setName(land.getName());
		newLand.setFlughafen(land.getFlughafen());
		newLand.setKarte_bild(Helper.convertMultiPartFileToByte(bild));
		newLand.setReiseAngebot(reiseAngebot);

		return Land2LandReadTO.apply(landRepository.save(newLand));

	}

	public LandReadTO getLand(UUID id) {

		Land land = landRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find Land with id: " + id));

		return Land2LandReadTO.apply(land);
	}

	public LandReadTO updateLand(LandWriteTO land, MultipartFile bild) {

		ReiseAngebot reiseAngebot = reiseAngebotRepository.findById(land.getReiseAngebotId())
		.orElseThrow(() -> new ResourceNotFoundException("Cannot find ReiseAngebot with id: " + land.getReiseAngebotId()));

		Land newLand = landRepository.findById(land.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find Land with id: " + land.getId()));

		if (land.getName() != null)
			newLand.setName(land.getName());
		if (land.getFlughafen() != null)
			newLand.setFlughafen(land.getFlughafen());
		if (bild != null)
			newLand.setKarte_bild(Helper.convertMultiPartFileToByte(bild));
		if(land.getReiseAngebotId() != null)
		    newLand.setReiseAngebot(reiseAngebot);

		return Land2LandReadTO.apply(landRepository.save(newLand));
	}

	public ResponseEntity<?> deleteLand(UUID id) {
		Land actual = landRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find Land with id: " + id));

		landRepository.deleteById(actual.getId());
		log.info("successfully delted");

		return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
	}

}
