package com.team.angular.interactiondesignapi.controllers;

import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import com.team.angular.interactiondesignapi.services.ReiseAngebotService;
import com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot.ReiseAngebotReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot.ReiseAngebotReadTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public class ReiseAngebotController {
    @Autowired
    protected ReiseAngebotService reiseAngebotService;

    @ApiOperation("Get All ReiseAngebot")
    @GetMapping("")
    public List<ReiseAngebotReadListTO> getAllReiseAngebot() {
        return reiseAngebotService.getAll();
    }

    @ApiOperation("Get One ReiseAngebot")
    @GetMapping("/{id}")
    public ReiseAngebotReadTO getReiseAngebotById(
            @ApiParam(name = "ReiseAngebotId", value = "get One ReiseAngebot") @PathVariable UUID id) {
        return reiseAngebotService.getReiseAngebot(id);
    }

    @ApiOperation("Add One ReiseAngebot")
    @PostMapping("")
    public ReiseAngebotReadTO addReiseAngebot(
            @ApiParam(name = "ReiseAngebot", value = "ReiseAngebot to add") @RequestPart(value = "reiseAngebot")
                    ReiseAngebotReadTO reiseAngebot, @RequestPart(value = "bild") MultipartFile bild) {
        return reiseAngebotService.addReiseAngebot(reiseAngebot, bild);
    }

    @ApiOperation("Update ReiseAngebot")
    @PutMapping("")
    public ReiseAngebotReadTO updateReiseAngebot(@ApiParam(name = "ReiseAngebot", value = "ReiseAngebot to update")
                                           @RequestPart(value = "reiseAngebot") ReiseAngebotReadListTO reiseAngebot,
                                           @RequestPart(value = "bild") MultipartFile bild) {
        return reiseAngebotService.updateReiseAngebot(reiseAngebot, bild);
    }

    @ApiOperation("Delete ReiseAngebot")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> daleteReiseAngebot(
            @ApiParam(name = "ReiseAngebotId", value = "Id of the ReiseAngebot") @PathVariable UUID id) {
        return reiseAngebotService.deleteReiseAngebot(id);

    }

}
