package com.team.angular.interactiondesignapi.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

	@Autowired
	private UnterkunftRepository unterkunftRepository;
	
	@Autowired
	private LandRepository landRepository;

	private static final Logger log = LoggerFactory.getLogger(UnterkunftService.class);
	
	public List<UnterkunftReadListTO> getAll() {
		return Unterkunft2UnterkunftReadListTO.apply(unterkunftRepository.findAll());
	}

	public UnterkunftReadTO addUnterkunft(UnterkunftWriteTO unterkunft, List<MultipartFile> files) {

		List<byte[]> bilder = new ArrayList<>();

		for (MultipartFile file : files) {

			bilder.add(new byte[(int) convertMultiPartFileToFile(file).length()]);
		}
		
		Land land = landRepository.findById(unterkunft.getLand()).orElseThrow(() 
				-> new ResourceNotFoundException("Cannot find UpdateUnterkunft with id: " + unterkunft.getId()));

		Unterkunft newUnterkunft = new Unterkunft();

		newUnterkunft.setName(unterkunft.getName());
		newUnterkunft.setLink(unterkunft.getLink());
		newUnterkunft.setAdresse(unterkunft.getAdresse());
		newUnterkunft.setBeschreibung(unterkunft.getBeschreibung());
		newUnterkunft.setBilder(bilder);
		newUnterkunft.setLand(land);

		return Unterkunft2UnterkunftReadTO.apply(unterkunftRepository.save(newUnterkunft));
	}
	
	public UnterkunftReadTO updateUnterkunft(UnterkunftWriteTO unterkunft, List<MultipartFile> files) {
		
		Unterkunft actual_unterkunft = unterkunftRepository.findById(unterkunft.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find UpdateUnterkunft with id: " + unterkunft.getId()));

		List<byte[]> bilder = new ArrayList<>();

		for (MultipartFile file : files) {

			bilder.add(new byte[(int) convertMultiPartFileToFile(file).length()]);
		}
		
		Land land = landRepository.findById(unterkunft.getLand()).orElseThrow(() 
				-> new ResourceNotFoundException("Cannot find UpdateUnterkunft with id: " + unterkunft.getId()));

		actual_unterkunft.setName(unterkunft.getName());
		actual_unterkunft.setLink(unterkunft.getLink());
		actual_unterkunft.setAdresse(unterkunft.getAdresse());
		actual_unterkunft.setBeschreibung(unterkunft.getBeschreibung());
		actual_unterkunft.setBilder(bilder);
		actual_unterkunft.setLand(land);

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
