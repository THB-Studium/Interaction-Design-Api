package com.team.angular.interactiondesignapi.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team.angular.interactiondesignapi.services.BuchungService;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungUpdateTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.ChangeStatus;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/buchungen")
@CrossOrigin(origins = "*")
public class BuchungController {

	@Autowired
	protected BuchungService buchungService;

	@ApiOperation("Get All Buchungen")
	@GetMapping("")
	public List<BuchungReadTO> getAllBuchungs(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "buchungDatum") String sortBy) {
		return buchungService.getAll(pageNo, pageSize, sortBy);
	}

	@ApiOperation("Get One Buchung")
	@GetMapping("/{id}")
	public BuchungReadTO getBuchungById(
			@ApiParam(name = "BuchungId", value = "ID of the Buchung") @PathVariable UUID id) {
		return buchungService.getBuchung(id);
	}

	@ApiOperation("Get One Booking by Number")
	@GetMapping("/search/{nummer}")
	public BuchungReadTO getBuchungByBuchungsnummer(
			@ApiParam(name = "Buchungsnummer", value = "Number of the Buchung") @PathVariable String nummer) {
		return buchungService.getBuchungByBuchungsnummer(nummer);
	}

	@ApiOperation("Export Buchung als pdf")
	@GetMapping("/exportPdf/{id}")
	public ResponseEntity<ByteArrayResource> exportPdf(
			@ApiParam(name = "BuchungId", value = "ID of the Buchung") @PathVariable UUID id)
			throws URISyntaxException, IOException {

		byte[] data = null;
		try {
			data = buchungService.exportPdf(id);
		} catch (JRException e) {
			e.printStackTrace();
		}

		ByteArrayResource resource = new ByteArrayResource(data);

		return ResponseEntity.ok().contentLength(data.length).header("Content-type", "application/octet-stream")
				.header("Content-disposition", "attachement; filename=Buchung_" + LocalDate.now().toString() + ".pdf")
				.body(resource);
	}

	@ApiOperation("Add One Buchung")
	@PostMapping("")
	public BuchungReadTO addBuchung(
			@ApiParam(name = "Buchung", value = "Buchung to add") @RequestBody BuchungWriteTO buchung)
			throws Exception {
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

	@ApiOperation("Remove MitReisender Buchung")
	@DeleteMapping("/removeMitReisender/{id}")
	public ResponseEntity<?> removeMitReiser(
			@ApiParam(name = "BuchungId", value = "Id of the Buchung") @PathVariable UUID id) {
		return buchungService.removeMitReisender(id);
	}

	@ApiOperation("change Buchung status")
	@PutMapping("/changestatus")
	public ResponseEntity<?> changeStatus(
			@ApiParam(name = "Status", value = "New Status") @RequestBody ChangeStatus status) {
		return buchungService.changeStatus(status);
	}

}
