package com.team.angular.interactiondesignapi.controllers;

import com.team.angular.interactiondesignapi.services.ReisenderService;
import com.team.angular.interactiondesignapi.transfertobjects.reisender.ReisenderReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.reisender.ReisenderReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.reisender.ReisenderWriteTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reisende")
@CrossOrigin(origins = "*")
public class ReisenderController {

    @Autowired
    protected ReisenderService reisenderService;

    @ApiOperation("Get All Reisende")
    @GetMapping("")
    public List<ReisenderReadListTO> getAllReisende( @RequestParam(defaultValue = "0") Integer pageNo,
                                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                                     @RequestParam(defaultValue = "id") String sortBy) {
        return reisenderService.getAll(pageNo, pageSize, sortBy);
    }

    @ApiOperation("Get One Reisender")
    @GetMapping("/{id}")
    public ReisenderReadTO getReisenderById(@ApiParam(name = "ReisenderId", value = "ID of the Reisender") @PathVariable UUID id) {
        return reisenderService.getReisender(id);
    }

    @ApiOperation("Add One Reisender")
    @PostMapping("")
    public ReisenderReadTO addReisender(
            @ApiParam(name = "ReisenderWriteTO", value = "Reisender to add") @RequestBody ReisenderWriteTO reisender) {
        return reisenderService.addReisender(reisender);
    }

    @ApiOperation("Update Reisender")
    @PutMapping("")
    public ReisenderReadTO updateReisender(
            @ApiParam(name = "ReisenderWriteTO", value = "Reisender to update") @RequestBody ReisenderWriteTO reisender) {
        return reisenderService.updateReisender(reisender);
    }

    @ApiOperation("Delete Reisender")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReisender(
            @ApiParam(name = "ReisenderId", value = "Id of the Reisender") @PathVariable UUID id) {
        return reisenderService.deleteReisender(id);

    }

}
