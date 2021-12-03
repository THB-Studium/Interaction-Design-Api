package com.team.angular.interactiondesignapi.controllers;

import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import com.team.angular.interactiondesignapi.services.ReiseAngebotService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public class ReiseAngebotController {
    @Autowired
    protected ReiseAngebotService reiseAngebotService;

    @ApiOperation("Get All ReiseAngebot")
    @GetMapping("")
    public List<ReiseAngebot> getAllReiseAngebot() {
        return reiseAngebotService.getAll();
    }

    @ApiOperation("Get One ReiseAngebot")
    @GetMapping("/{id}")
    public ReiseAngebot getReiseAngebotById(
            @ApiParam(name = "ReiseAngebotId", value = "get One ReiseAngebot") @PathVariable UUID id) {
        return reiseAngebotService.getReiseAngebot(id);
    }

    @ApiOperation("Add One ReiseAngebot")
    @PostMapping("")
    public ReiseAngebot addReiseAngebot(
            @ApiParam(name = "ReiseAngebot", value = "ReiseAngebot to add") @RequestBody ReiseAngebot reiseAngebot) {
        return reiseAngebotService.addReiseAngebot(reiseAngebot);
    }

    /*
     * @ApiOperation("Update ReiseAngebot")
     * 
     * @PutMapping("")
     * public ReiseAngebot updateReiseAngebot(
     * 
     * @ApiParam(name = "ReiseAngebot", value =
     * "ReiseAngebot to update") @RequestBody ReiseAngebot
     * reiseAngebot) {
     * return reiseAngebotService.updateReiseAngebot(reiseAngebot);
     * }
     */

    @ApiOperation("Delete ReiseAngebot")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> daleteReiseAngebot(
            @ApiParam(name = "ReiseAngebotId", value = "Id of the ReiseAngebot") @PathVariable UUID id) {
        return reiseAngebotService.deleteReiseAngebot(id);

    }

}
