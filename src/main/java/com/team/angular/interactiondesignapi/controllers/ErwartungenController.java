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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team.angular.interactiondesignapi.services.ErwartungenService;
import com.team.angular.interactiondesignapi.transfertobjects.erwartungen.ErwartungenReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.erwartungen.ErwartungenReadWriteTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/erwartungen")
@CrossOrigin(origins = "*")
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
    public ErwartungenReadWriteTO getErwartungenById(
            @ApiParam(name = "ErwartungenId", value = "ID of the Erwartungen") @PathVariable UUID id) {
        return erwartungenService.getErwartungen(id);
    }

    @ApiOperation("Add One Erwartungen")
    @PostMapping("")
    public ErwartungenReadWriteTO addErwartungen(
            @ApiParam(name = "Erwartungen", value = "Erwartungen to add") @RequestBody ErwartungenReadWriteTO erwartungen) {
        return erwartungenService.addErwartungen(erwartungen);
    }

    // Aktualisierung von Erwartungen mit ReiseAngebot (ohne Bild)
    @ApiOperation("Update Erwartungen")
    @PutMapping("")
    public ErwartungenReadListTO updateErwartungen(@ApiParam(name = "Erwartungen", value = "Erwartungen to update")
                                         @RequestBody ErwartungenReadListTO erwartungen) {
        return erwartungenService.updateErwartungen(erwartungen);
    }

    @ApiOperation("Delete Erwartungen")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteErwartungen(
            @ApiParam(name = "ErwartungenId", value = "Id of the Erwartungen") @PathVariable UUID id) {
        return erwartungenService.deleteErwartungen(id);
    }

}
