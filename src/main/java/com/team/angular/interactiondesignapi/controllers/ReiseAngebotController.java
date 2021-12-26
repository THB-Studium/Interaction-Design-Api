package com.team.angular.interactiondesignapi.controllers;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot.ReiseAngebotUpdateTO;
import com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot.ReiseAngebotWriteTO;
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

import com.team.angular.interactiondesignapi.services.ReiseAngebotService;
import com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot.ReiseAngebotReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot.ReiseAngebotReadTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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

    @ApiOperation("Get One ReiseAngebot")
    @GetMapping("/{id}")
    public ReiseAngebotReadTO getReiseAngebotById(
            @ApiParam(name = "ReiseAngebotId", value = "get One ReiseAngebot") @PathVariable UUID id) {
        return reiseAngebotService.getReiseAngebot(id);
    }

    //all test
    @ApiOperation("Add One ReiseAngebot")
    @PostMapping("")
    public ReiseAngebotReadTO addReiseAngebot(
            @ApiParam(name = "ReiseAngebot", value = "Transfert Object to add Land: (String titel, byte[] startbild, Date startDatum)") @RequestPart(value = "reiseAngebot")
                    ReiseAngebotWriteTO reiseAngebot, @RequestPart(value = "bild") MultipartFile bild) {
        return reiseAngebotService.addReiseAngebot(reiseAngebot, bild);
    }

    @ApiOperation("Update ReiseAngebot")
    @PutMapping("")
    public ReiseAngebotReadTO updateReiseAngebot(@ApiParam(name = "ReiseAngebot", value = "ReiseAngebot to update")
                                           @RequestPart(value = "reiseAngebot") ReiseAngebotUpdateTO reiseAngebot,
                                           @RequestPart(value = "bild") MultipartFile bild) {
        return reiseAngebotService.updateReiseAngebot(reiseAngebot, bild);
    }

    @ApiOperation("Delete ReiseAngebot")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReiseAngebot(
            @ApiParam(name = "ReiseAngebotId", value = "Id of the ReiseAngebot") @PathVariable UUID id) {
        return reiseAngebotService.deleteReiseAngebot(id);
    }

}
