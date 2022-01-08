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

import com.team.angular.interactiondesignapi.services.LandService;
import com.team.angular.interactiondesignapi.transfertobjects.land.LandReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.land.LandWriteTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/laender")
@CrossOrigin(origins = "*")
public class LandController {

	@Autowired
	protected LandService landService;

	@ApiOperation("Get All Lands")
	@GetMapping("")
	public List<LandReadTO> getAllLands() {
		return landService.getAll();
	}

	@ApiOperation("Get One Land")
	@GetMapping("/{id}")
	public LandReadTO getLandById(@ApiParam(name = "LandId", value = "get One Land") @PathVariable UUID id) {
		return landService.getLand(id);
	}

	@ApiOperation("Add One Land")
	@PostMapping("")
	public LandReadTO addLand(
			@ApiParam(name = "Land", value = "Transfert Object to add Land: (String name, List flughafen, String unterkunft_text, String corona_infos, String klima, String gesundheit, String reiseOrdnung, String hinweise, List mitReiserBerechtigt, String sonstigeHinweise, UUID reiseAngebotId)") @RequestPart(value = "land") LandWriteTO land,
			@RequestPart(value = "bild") MultipartFile bild) {
		return landService.addLand(land, bild);
	}

	// todo: @RequestPart(value = "bild", required = false) MultipartFile bild
	@ApiOperation("Update Land")
	@PutMapping("")
	public LandReadTO updateLand(
			@ApiParam(name = "Land", value = "Transfert Object to update Land: (String name, List flughafen, String unterkunft_text, String corona_infos, String klima, String gesundheit, String reiseOrdnung, String hinweise, List mitReiserBerechtigt, String sonstigeHinweise, UUID reiseAngebotId)") @RequestPart(value = "land") LandWriteTO land,
			@RequestPart(value = "bild") MultipartFile bild) {
		return landService.updateLand(land, bild);
	}

	@ApiOperation("Delete Land")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteLand(@ApiParam(name = "LandId", value = "Id of the Land") @PathVariable UUID id) {
		return landService.deleteLand(id);

	}

}
