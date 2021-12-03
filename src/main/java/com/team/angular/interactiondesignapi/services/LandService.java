package com.team.angular.interactiondesignapi.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Buchungsklassen;
import com.team.angular.interactiondesignapi.models.Erwartungen;
import com.team.angular.interactiondesignapi.models.Land_info;
import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.repositories.BuchungsklassenRepository;
import com.team.angular.interactiondesignapi.repositories.ErwartungenRepository;
import com.team.angular.interactiondesignapi.repositories.Land_infoRepository;
import com.team.angular.interactiondesignapi.repositories.LandRepository;
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
	private BuchungsklassenRepository buchungsklassenRepository;

	@Autowired
	private Land_infoRepository infos_landRepository;

	@Autowired
	private ErwartungenRepository erwartungenRepository;

	private static final Logger log = LoggerFactory.getLogger(LandService.class);

	public List<LandReadListTO> getAll() {
		return Land2LandReadListTO.apply(landRepository.findAll());
	}

	public LandReadTO addLand(LandWriteTO land, MultipartFile bild, MultipartFile karteBild) {

		Erwartungen erwartungen = erwartungenRepository.findById(land.getErwartungenId()).orElseThrow(
				() -> new ResourceNotFoundException("Cannot find landsklasse with id: " + land.getErwartungenId()));

		Land_info infos_Land = infos_landRepository.findById(land.getInfos_LandId()).orElseThrow(
				() -> new ResourceNotFoundException("Cannot find Reiser with id: " + land.getInfos_LandId()));

		Buchungsklassen buchungsklassen = buchungsklassenRepository.findById(land.getBuchungsklassenId()).orElseThrow(
				() -> new ResourceNotFoundException("Cannot find Land with id: " + land.getBuchungsklassenId()));

		Land newLand = new Land();
		newLand.setName(land.getName());
		newLand.setBild(new byte[(int) convertMultiPartFileToFile(bild).length()]);
		newLand.setStartDatum(land.getStartDatum());
		newLand.setEndDatum(land.getEndDatum());
		newLand.setTitel(land.getTitel());
		newLand.setKarteBild(new byte[(int) convertMultiPartFileToFile(karteBild).length()]);
		newLand.setPlaetze(land.getPlaetze());
		newLand.setFreiPlaetze(land.getFreiPlaetze());
		newLand.setAnmeldungsFrist(land.getAnmeldungsFrist());
		newLand.setLeistungen(land.getLeistungen());
		newLand.setErwartungen(erwartungen);
		newLand.setInfos_Land(infos_Land);
		newLand.setBuchungsklassen(buchungsklassen);

		return Land2LandReadTO.apply(landRepository.save(newLand));

	}

	public LandReadTO getLand(UUID id) {

		Land land = landRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find Land with id: " + id));

		return Land2LandReadTO.apply(land);
	}

	public LandReadTO updateLand(LandWriteTO land, MultipartFile bild, MultipartFile karteBild) {

		Land newLand = landRepository.findById(land.getId()).orElseThrow(
				() -> new ResourceNotFoundException("Cannot find Land with id: " + land.getId()));

		Erwartungen erwartungen = erwartungenRepository.findById(land.getErwartungenId()).orElseThrow(
				() -> new ResourceNotFoundException("Cannot find landsklasse with id: " + land.getErwartungenId()));

		Land_info infos_Land = infos_landRepository.findById(land.getInfos_LandId()).orElseThrow(
				() -> new ResourceNotFoundException("Cannot find Reiser with id: " + land.getInfos_LandId()));

		Buchungsklassen buchungsklassen = buchungsklassenRepository.findById(land.getBuchungsklassenId()).orElseThrow(
				() -> new ResourceNotFoundException("Cannot find Land with id: " + land.getBuchungsklassenId()));

		newLand.setName(land.getName());
		newLand.setBild(new byte[(int) convertMultiPartFileToFile(bild).length()]);
		newLand.setStartDatum(land.getStartDatum());
		newLand.setEndDatum(land.getEndDatum());
		newLand.setTitel(land.getTitel());
		newLand.setKarteBild(new byte[(int) convertMultiPartFileToFile(karteBild).length()]);
		newLand.setPlaetze(land.getPlaetze());
		newLand.setFreiPlaetze(land.getFreiPlaetze());
		newLand.setAnmeldungsFrist(land.getAnmeldungsFrist());
		newLand.setLeistungen(land.getLeistungen());
		newLand.setErwartungen(erwartungen);
		newLand.setInfos_Land(infos_Land);
		newLand.setBuchungsklassen(buchungsklassen);

		return Land2LandReadTO.apply(landRepository.save(newLand));
	}

	public ResponseEntity<?> deleteLand(UUID id) {
		Land actual = landRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find Land with id: " + id));

		landRepository.deleteById(actual.getId());
		log.info("successfully delted");

		return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
	}

	private File convertMultiPartFileToFile(MultipartFile file) {
		File convertedFile = new File(file.getOriginalFilename());
		try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
			fos.write(file.getBytes());
		} catch (IOException e) {
			log.error("Failed to convert the MultipartFile to a File");
		}

		return convertedFile;
	}

}
