package com.team.angular.interactiondesignapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.models.Unterkunft;
import com.team.angular.interactiondesignapi.repositories.LandRepository;
import com.team.angular.interactiondesignapi.repositories.UnterkunftRepository;
import com.team.angular.interactiondesignapi.transfertobjects.unterkunft.Unterkunft2UnterkunftReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.unterkunft.Unterkunft2UnterkunftReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.unterkunft.UnterkunftReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.unterkunft.UnterkunftReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.unterkunft.UnterkunftWriteTO;

@Service
public class UnterkunftService {

	private static final Logger log = LoggerFactory.getLogger(UnterkunftService.class);
	@Autowired
	private UnterkunftRepository unterkunftRepository;
	@Autowired
	private LandRepository landRepository;

	public List<UnterkunftReadListTO> getAll() {
		return Unterkunft2UnterkunftReadListTO.apply(unterkunftRepository.findAll());
	}

	public UnterkunftReadTO addUnterkunft(UnterkunftWriteTO unterkunft) throws Exception {

		List<byte[]> bilder = new ArrayList<>();

		Unterkunft newUnterkunft = new Unterkunft();

		if (!unterkunftRepository.existsUnterkunftByName(unterkunft.getName())) {
			newUnterkunft.setName(unterkunft.getName());
		} else {
			throw new Exception(unterkunft.getName() + " already exists");
		}

		if (unterkunft.getBilder() != null) {
			for (String file : unterkunft.getBilder()) {
				bilder.add(Base64.decodeBase64(file.substring(22)));
			}
			newUnterkunft.setBilder(bilder);
		}

		newUnterkunft.setLink(unterkunft.getLink());
		newUnterkunft.setAddresse(unterkunft.getAddresse());
		newUnterkunft.setBeschreibung(unterkunft.getBeschreibung());

		if (unterkunft.getLandId() != null) {
			Land land = landRepository.findById(unterkunft.getLandId()).orElseThrow(
					() -> new ResourceNotFoundException("Cannot find Land with id: " + unterkunft.getLandId()));
			newUnterkunft.setLand(land);
		}

		return Unterkunft2UnterkunftReadTO.apply(unterkunftRepository.save(newUnterkunft));
	}

	public UnterkunftReadTO updateUnterkunft(UnterkunftWriteTO unterkunft) throws Exception {

		Unterkunft actual_unterkunft = unterkunftRepository.findById(unterkunft.getId()).orElseThrow(
				() -> new ResourceNotFoundException("Cannot find UpdateUnterkunft with id: " + unterkunft.getId()));

		List<byte[]> bilder = new ArrayList<>();

		if (unterkunft.getName() != null && !unterkunft.getName().equals(actual_unterkunft.getName())) {
			if (!unterkunftRepository.existsUnterkunftByName(unterkunft.getName())) {
				actual_unterkunft.setName(unterkunft.getName());
			} else {
				throw new Exception(unterkunft.getName() + " already exists");
			}
		}

		if (unterkunft.getBilder() != null && unterkunft.getBilder().size() > 0) {
			for (String file : unterkunft.getBilder()) {
				bilder.add(Base64.decodeBase64(file.substring(22)));
			}
			actual_unterkunft.setBilder(bilder);
		}

		Land land = landRepository.findById(unterkunft.getLandId()).orElseThrow(
				() -> new ResourceNotFoundException("Cannot find UpdateUnterkunft with id: " + unterkunft.getId()));

		// Land land = null;
		if (unterkunft.getLandId() != null) {
			land = landRepository.findById(unterkunft.getLandId()).orElseThrow(
					() -> new ResourceNotFoundException("Cannot find UpdateUnterkunft with id: " + unterkunft.getId()));
			actual_unterkunft.setLand(land);
		}

		if (unterkunft.getLink() != null)
			actual_unterkunft.setLink(unterkunft.getLink());
		if (unterkunft.getAddresse() != null)
			actual_unterkunft.setAddresse(unterkunft.getAddresse());
		if (unterkunft.getBeschreibung() != null)
			actual_unterkunft.setBeschreibung(unterkunft.getBeschreibung());

		return Unterkunft2UnterkunftReadTO.apply(unterkunftRepository.save(actual_unterkunft));
	}

	public UnterkunftReadTO getUnterkunft(UUID id) {
		Unterkunft unterkunft = unterkunftRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find Feedback with id: " + id));

		return Unterkunft2UnterkunftReadTO.apply(unterkunftRepository.save(unterkunft));
	}

	public ResponseEntity<?> deleteUnterkunft(UUID id) {
		Unterkunft unterkunft = unterkunftRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find Feedback with id: " + id));

		unterkunftRepository.delete(unterkunft);

		return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
	}

}
