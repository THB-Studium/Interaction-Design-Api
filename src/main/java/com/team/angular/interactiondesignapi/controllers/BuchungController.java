package com.team.angular.interactiondesignapi.controllers;

import com.team.angular.interactiondesignapi.services.BuchungService;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungUpdateTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungWriteTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/buchungs")
@CrossOrigin(origins = "*")
public class BuchungController {

    @Autowired
    protected BuchungService buchungService;

    @ApiOperation("Get All Buchungs")
    @GetMapping("")
    public List<BuchungReadTO> getAllBuchungs() {
        return buchungService.getAll();
    }

    @ApiOperation("Get One Buchung")
    @GetMapping("/{id}")
    public BuchungReadTO getBuchungById(
            @ApiParam(name = "BuchungId", value = "ID f the Buchung") @PathVariable UUID id) {
        return buchungService.getBuchung(id);
    }

    @ApiOperation("Add One Buchung")
    @PostMapping("")
    public BuchungReadTO addBuchung(
            @ApiParam(name = "Buchung", value = "Buchung to add") @RequestBody BuchungWriteTO buchung) throws Exception {
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

    @ApiOperation("Remove MitReiser Buchung")
    @DeleteMapping("/removeMitReiser/{id}")
    public ResponseEntity<?> removeMitReiser(
            @ApiParam(name = "BuchungId", value = "Id of the Buchung") @PathVariable UUID id) {
        return buchungService.removeMitReiser(id);
    }

}
