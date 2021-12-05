package com.team.angular.interactiondesignapi.controllers;

import com.team.angular.interactiondesignapi.models.Erwartungen;
import com.team.angular.interactiondesignapi.services.ErwartungenService;
import com.team.angular.interactiondesignapi.transfertobjects.erwartungen.ErwartungenReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.erwartungen.ErwartungenReadTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public class ErwartungenController {
    @Autowired
    protected ErwartungenService erwartungenService;

    @ApiOperation("Get All Erwartungen")
    @GetMapping("")
    public List<ErwartungenReadListTO> getAllErwartungen() {
        return erwartungenService.getAll();
    }

    @ApiOperation("Get One Erwartungen")
    @GetMapping("/{id}")
    public ErwartungenReadTO getErwartungenById(
            @ApiParam(name = "ErwartungenId", value = "get One Erwartungen") @PathVariable UUID id) {
        return erwartungenService.getErwartungen(id);
    }

    @ApiOperation("Add One Erwartungen")
    @PostMapping("")
    public Erwartungen addErwartungen(
            @ApiParam(name = "Erwartungen", value = "Erwartungen to add") @RequestBody Erwartungen erwartungen) {
        return erwartungenService.addErwartungen(erwartungen);
    }

    // Aktualisierung von Erwartungen mit ReiseAngebot (ohne Bild)
    @ApiOperation("Update Erwartungen")
    @PutMapping("")
    public Erwartungen updateErwartungen(@ApiParam(name = "Erwartungen", value = "Erwartungen to update")
                                         @RequestBody Erwartungen erwartungen) {
        return erwartungenService.updateErwartungen(erwartungen);
    }

    @ApiOperation("Delete Erwartungen")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteErwartungen(
            @ApiParam(name = "ErwartungenId", value = "Id of the Erwartungen") @PathVariable UUID id) {
        return erwartungenService.deleteErwartungen(id);

    }

}
