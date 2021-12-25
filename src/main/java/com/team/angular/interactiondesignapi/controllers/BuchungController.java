package com.team.angular.interactiondesignapi.controllers;

import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team.angular.interactiondesignapi.services.BuchungService;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungUpdateTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungWriteTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/buchungen")
@CrossOrigin(origins = "*")
public class BuchungController {

	@Autowired
	protected BuchungService buchungService;

	@ApiOperation("Get All Buchungs")
	@GetMapping("")
	public Set<BuchungReadListTO> getAllBuchungs() {
		return buchungService.getAll();
	}

	@ApiOperation("Get One Buchung")
	@GetMapping("/{id}")
	public BuchungReadTO getBuchungById(
			@ApiParam(name = "BuchungId", value = "get One Buchung") @PathVariable UUID id) {
		return buchungService.getBuchung(id);
	}

	@ApiOperation("Add One Buchung")
	@PostMapping("")
	public BuchungReadTO addBuchung(
			@ApiParam(name = "Buchung", value = "Buchung to add") @RequestBody BuchungWriteTO buchung) {
		return buchungService.addBuchung(buchung);
	}

	@ApiOperation("Update Buchung")
	@PutMapping("")
	public BuchungReadTO updateBuchung(
			@ApiParam(name = "Buchung", value = "Buchung to update") @RequestBody BuchungUpdateTO buchung) {
		return buchungService.updateBuchung(buchung);
	}

	@ApiOperation("Delete Buchung")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> daleteBuchung(
			@ApiParam(name = "BuchungId", value = "Id of the Buchung") @PathVariable UUID id) {
		return buchungService.deleteBuchung(id);

	}

}
