package com.team.angular.interactiondesignapi.controllers;

import com.team.angular.interactiondesignapi.services.UnterkunftService;
import com.team.angular.interactiondesignapi.transfertobjects.unterkunft.UnterkunftReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.unterkunft.UnterkunftReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.unterkunft.UnterkunftWriteTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/unterkunfte")
@CrossOrigin(origins = "*")
public class UnterkunftController {

    @Autowired
    protected UnterkunftService unterkunftService;

    @ApiOperation("Get All Unterkunfte")
    @GetMapping("")
    public List<UnterkunftReadListTO> getAllUnterkunfte() {
        return unterkunftService.getAll();
    }

    @ApiOperation("Get One Unterkunft")
    @GetMapping("/{id}")
    public UnterkunftReadTO getUnterkunftById(
            @ApiParam(name = "UnterkunftId", value = "ID of the Unterkunft") @PathVariable UUID id) {
        return unterkunftService.getUnterkunft(id);
    }

    @ApiOperation("Add One Unterkunft")
    @PostMapping("")
    public UnterkunftReadTO addUnterkunft(
            @ApiParam(name = "UnterkunftWriteTO", value = "Unterkunft to add") @RequestBody UnterkunftWriteTO unterkunft) {
        return unterkunftService.addUnterkunft(unterkunft);
    }

    @ApiOperation("Update Unterkunft")
    @PutMapping("")
    public UnterkunftReadTO updateUnterkunft(
            @ApiParam(name = "UnterkunftWriteTO", value = "Unterkunft to update") @RequestBody UnterkunftWriteTO unterkunft) {
        return unterkunftService.updateUnterkunft(unterkunft);
    }

    @ApiOperation("Delete Unterkunft")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUnterkunft(
            @ApiParam(name = "UnterkunftId", value = "Id of the Unterkunft") @PathVariable UUID id) {
        return unterkunftService.deleteUnterkunft(id);

    }
}
