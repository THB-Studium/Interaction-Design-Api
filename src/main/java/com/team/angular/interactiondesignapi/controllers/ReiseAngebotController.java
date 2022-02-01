package com.team.angular.interactiondesignapi.controllers;

import com.team.angular.interactiondesignapi.services.ReiseAngebotService;
import com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot.ReiseAngebotHomeTO;
import com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot.ReiseAngebotReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot.ReiseAngebotReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot.ReiseAngebotWriteTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reiseAngebot")
@CrossOrigin(origins = "*")
public class ReiseAngebotController {
    @Autowired
    protected ReiseAngebotService reiseAngebotService;

    @ApiOperation("Get All ReiseAngebot")
    @GetMapping("")
    public List<ReiseAngebotReadListTO> getAllReiseAngebot() {
        return reiseAngebotService.getAll();
    }

    @ApiOperation("Get All ReiseAngebotHome")
    @GetMapping("/reiseAngebotHome")
    public List<ReiseAngebotHomeTO> getAllReiseAngebotHome() {
        return reiseAngebotService.getAllForHome();
    }


    @ApiOperation("Get One ReiseAngebot")
    @GetMapping("/{id}")
    public ReiseAngebotReadTO getReiseAngebotById(
            @ApiParam(name = "ReiseAngebotId", value = "ID of the ReiseAngebot") @PathVariable UUID id) {
        return reiseAngebotService.getReiseAngebot(id);
    }

    // all test
    @ApiOperation("Add One ReiseAngebot")
    @PostMapping("")
    public ReiseAngebotReadTO addReiseAngebot(
            @ApiParam(name = "ReiseAngebot", value = "ReiseAngebot to add") @RequestBody ReiseAngebotWriteTO reiseAngebot) throws Exception {
        return reiseAngebotService.addReiseAngebot(reiseAngebot);
    }

    @ApiOperation("Update ReiseAngebot")
    @PutMapping("")
    public ReiseAngebotReadTO updateReiseAngebot(
            @ApiParam(name = "ReiseAngebot", value = "ReiseAngebot to update") @RequestBody ReiseAngebotWriteTO reiseAngebot) throws Exception {
        return reiseAngebotService.updateReiseAngebot(reiseAngebot);
    }

    @ApiOperation("Delete ReiseAngebot")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReiseAngebot(
            @ApiParam(name = "ReiseAngebotId", value = "Id of the ReiseAngebot") @PathVariable UUID id) {
        return reiseAngebotService.deleteReiseAngebot(id);
    }

    @ApiOperation("Add Interessiert")
    @PutMapping("/addInteressiert/{id}")
    public ResponseEntity<?> addInteressiert(
            @ApiParam(name = "ReiseAngebotId", value = "Id of the ReiseAngebot") @PathVariable UUID id) {
        return reiseAngebotService.addInteressiert(id);
    }

    @ApiOperation("Reset Interessiert")
    @PutMapping("/resetInteressiert/{id}")
    public ResponseEntity<?> resetInteressiert(
            @ApiParam(name = "ReiseAngebotId", value = "Id of the ReiseAngebot") @PathVariable UUID id) {
        return reiseAngebotService.resetInteressiert(id);
    }


    @ApiOperation("uninteressiert")
    @PutMapping("/uninteressiert/{id}")
    public ResponseEntity<?> uninteressiert(
            @ApiParam(name = "ReiseAngebotId", value = "Id of the ReiseAngebot") @PathVariable UUID id) {
        return reiseAngebotService.uninteressiert(id);
    }

}
