package com.team.angular.interactiondesignapi.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.team.angular.interactiondesignapi.services.UnterkunftService;
import com.team.angular.interactiondesignapi.transfertobjects.unterkunft.UnterkunftReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.unterkunft.UnterkunftReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.unterkunft.UnterkunftWriteTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/unterkunfte")
@CrossOrigin(origins = "*")
public class UnterkunftController {

	@Autowired
	protected UnterkunftService unterkunftService;

	@ApiOperation("Get All Unterkunfte")
	@GetMapping("")
	public List<UnterkunftReadListTO> getAllUnterkunfte() {
		return unterkunftService.getAll();
	}

	@ApiOperation("Get One Unterkunft")
	@GetMapping("/{id}")
	public UnterkunftReadTO getUnterkunftById(
			@ApiParam(name = "UnterkunftId", value = "get One Unterkunft") @PathVariable UUID id) {
		return unterkunftService.getUnterkunft(id);
	}

	@ApiOperation("Add One Unterkunft")
	@PostMapping("")
	public UnterkunftReadTO addUnterkunft(
			@ApiParam(name = "Unterkunft", value = "TO to add Unterkunft: (String name, String link, String adresse, String beschreibung, UUID landId)") @RequestPart(value = "unterkunft") UnterkunftWriteTO unterkunft,
			@RequestPart(value = "files", required = false) List<MultipartFile> files) {
		return unterkunftService.addUnterkunft(unterkunft, files);
	}

	@ApiOperation("Update Unterkunft")
	@PutMapping("")
	public UnterkunftReadTO updateUnterkunft(
			@ApiParam(name = "Unterkunft", value = "TO to update Unterkunft: (String name, String link, String adresse, String beschreibung, UUID landId)\"") @RequestPart(value = "unterkunft") UnterkunftWriteTO unterkunft,
			@RequestPart(value = "files", required = false) List<MultipartFile> files) {
		return unterkunftService.updateUnterkunft(unterkunft, files);
	}

	@ApiOperation("Delete Unterkunft")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> daleteUnterkunft(
			@ApiParam(name = "UnterkunftId", value = "Id of the Unterkunft") @PathVariable UUID id) {
		return unterkunftService.deleteUnterkunft(id);

	}
}
