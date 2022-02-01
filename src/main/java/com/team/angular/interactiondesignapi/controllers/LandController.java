package com.team.angular.interactiondesignapi.controllers;

import com.team.angular.interactiondesignapi.services.LandService;
import com.team.angular.interactiondesignapi.transfertobjects.land.LandReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.land.LandReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.land.LandWriteTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/laender")
@CrossOrigin(origins = "*")
public class LandController {

    @Autowired
    protected LandService landService;

    @ApiOperation("Get All Lands")
    @GetMapping("")
    public List<LandReadListTO> getAllLands() {
        return landService.getAll();
    }

    @ApiOperation("Get One Land")
    @GetMapping("/{id}")
    public LandReadTO getLandById(@ApiParam(name = "LandId", value = "ID of the Land") @PathVariable UUID id) {
        return landService.getLand(id);
    }

    @ApiOperation("Add One Land")
    @PostMapping("")
    public LandReadTO addLand(
            @ApiParam(name = "Land", value = "Land to add") @RequestBody LandWriteTO land) throws Exception {
        return landService.addLand(land);
    }

    @ApiOperation("Update Land")
    @PutMapping("")
    public LandReadTO updateLand(
            @ApiParam(name = "Land", value = "Land to update") @RequestBody LandWriteTO land) throws Exception {
        return landService.updateLand(land);
    }

    @ApiOperation("Delete Land")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLand(@ApiParam(name = "LandId", value = "Id of the Land") @PathVariable UUID id) {
        return landService.deleteLand(id);

    }

}
