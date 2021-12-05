package com.team.angular.interactiondesignapi.controllers;

import com.team.angular.interactiondesignapi.models.Land_info;
import com.team.angular.interactiondesignapi.services.Land_infoService;
import com.team.angular.interactiondesignapi.transfertobjects.land_info.Land_infoReadTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public class Land_infoController {
    @Autowired
    protected Land_infoService land_infoService;

    @ApiOperation("Get All Land_info")
    @GetMapping("")
    public List<Land_info> getAllLand_info() {
        return land_infoService.getAll();
    }

    @ApiOperation("Get One Land_info")
    @GetMapping("/{id}")
    public Land_infoReadTO getLand_infoById(
            @ApiParam(name = "Land_infoId", value = "get One Land_info") @PathVariable UUID id) {
        return land_infoService.getLand_info(id);
    }

    @ApiOperation("Add One Land_info")
    @PostMapping("")
    public Land_info addLand_info(
            @ApiParam(name = "Land_info", value = "Land_info to add") @RequestBody Land_info land_info) {
        return land_infoService.addLand_info(land_info);
    }

    /*
     * @ApiOperation("Update Land_info")
     * 
     * @PutMapping("")
     * public Land_info updateLand_info(
     * 
     * @ApiParam(name = "Land_info", value =
     * "Land_info to update") @RequestBody Land_info
     * land_info) {
     * return land_infoService.updateLand_info(land_info);
     * }
     */

    @ApiOperation("Delete Land_info")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> daleteLand_info(
            @ApiParam(name = "Land_infoId", value = "Id of the Land_info") @PathVariable UUID id) {
        return land_infoService.deleteLand_info(id);

    }

}
