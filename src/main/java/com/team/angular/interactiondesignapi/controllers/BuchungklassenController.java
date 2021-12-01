package com.team.angular.interactiondesignapi.controllers;

import com.team.angular.interactiondesignapi.models.Buchungsklassen;
import com.team.angular.interactiondesignapi.services.BuchungsklassenService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public class BuchungklassenController {
    @Autowired
    protected BuchungsklassenService buchungsklassenService;

    @ApiOperation("Get All Buchungsklassen")
    @GetMapping("")
    public List<Buchungsklassen> getAllBuchungsklassen() {
        return buchungsklassenService.getAll();
    }

    @ApiOperation("Get One Buchungsklassen")
    @GetMapping("/{id}")
    public Buchungsklassen getBuchungsklassenById(
            @ApiParam(name = "BuchungsklassenId", value = "get One Buchungsklassen") @PathVariable UUID id) {
        return buchungsklassenService.getBuchungsklassen(id);
    }

    @ApiOperation("Add One Buchungsklassen")
    @PostMapping("")
    public Buchungsklassen addBuchungsklassen(
            @ApiParam(name = "Buchungsklassen", value = "Buchungsklassen to add") @RequestBody Buchungsklassen buchungsklassen) {
        return buchungsklassenService.addBuchungsklassen(buchungsklassen);
    }

    /*
     * @ApiOperation("Update Buchungsklassen")
     * 
     * @PutMapping("")
     * public Buchungsklassen updateBuchungsklassen(
     * 
     * @ApiParam(name = "Buchungsklassen", value =
     * "Buchungsklassen to update") @RequestBody Buchungsklassen
     * buchungsklassen) {
     * return buchungsklassenService.updateBuchungsklassen(buchungsklassen);
     * }
     */

    @ApiOperation("Delete Buchungsklassen")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> daleteBuchungsklassen(
            @ApiParam(name = "BuchungsklassenId", value = "Id of the Buchungsklassen") @PathVariable UUID id) {
        return buchungsklassenService.deleteBuchungsklassen(id);

    }

}
