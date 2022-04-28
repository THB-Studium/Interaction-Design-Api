package com.team.angular.interactiondesignapi.controllers;

import com.team.angular.interactiondesignapi.services.LandInfoService;
import com.team.angular.interactiondesignapi.transfertobjects.landInfo.LandInfoReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.landInfo.LandInfoReadWriteTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/landInfos")
@CrossOrigin(origins = "*")
public class LandInfoController {
    @Autowired
    protected LandInfoService landInfoService;

    @ApiOperation("Get All LandInfo")
    @GetMapping("")
    public List<LandInfoReadListTO> getAllLand_info( @RequestParam(defaultValue = "0") Integer pageNo,
                                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                                     @RequestParam(defaultValue = "id") String sortBy) {
        return landInfoService.getAll(pageNo, pageSize, sortBy);
    }

    @ApiOperation("Get One LandInfo")
    @GetMapping("/{id}")
    public LandInfoReadWriteTO getLandInfoById(
            @ApiParam(name = "LandInfoId", value = "ID of the LandInfo") @PathVariable UUID id) {
        return landInfoService.getLandInfo(id);
    }

    @ApiOperation("Add One LandInfo")
    @PostMapping("")
    public LandInfoReadWriteTO addLandInfo(
            @ApiParam(name = "LandInfoReadWriteTO", value = "LandInfo to add") @RequestBody LandInfoReadWriteTO landInfo) {
        return landInfoService.addLandInfo(landInfo);
    }

    @ApiOperation("Update LandInfo")
    @PutMapping("")
    public LandInfoReadListTO updateLandInfo(@ApiParam(name = "LandInfoReadListTO", value = "LandInfo to update")
                                             @RequestBody LandInfoReadListTO landInfo) {
        return landInfoService.updateLandInfo(landInfo);
    }

    @ApiOperation("Delete LandInfo")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLandInfo(
            @ApiParam(name = "LandInfoId", value = "Id of the LandInfo") @PathVariable UUID id) {
        return landInfoService.deleteLandInfo(id);
    }

}
