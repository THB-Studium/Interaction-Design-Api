package com.team.angular.interactiondesignapi.controllers;

import com.team.angular.interactiondesignapi.services.BuchungsklassenService;
import com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen.BuchungsklassenReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen.BuchungsklassenReadTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/buchungsklassen")
public class BuchungklassenController {
    @Autowired
    protected BuchungsklassenService buchungsklassenService;

    @ApiOperation("Get One Buchungsklassen")
    @GetMapping("/{id}")
    public BuchungsklassenReadTO getBuchungsklassenById(
            @ApiParam(name = "BuchungsklassenId", value = "get One Buchungsklassen") @PathVariable UUID id) {
        return buchungsklassenService.getBuchungsklassen(id);
    }

    @ApiOperation("Get All Buchungsklassen")
    @GetMapping("")
    public List<BuchungsklassenReadListTO> getAllBuchungsklassen() {
        return buchungsklassenService.getAll();
    }

    @ApiOperation("Add One Buchungsklassen")
    @PostMapping("")
    public BuchungsklassenReadTO addBuchungsklassen(
            @ApiParam(name = "Buchungsklassen", value = "Buchungsklassen to add") @RequestBody BuchungsklassenReadTO buchungsklassen) {
        return buchungsklassenService.addBuchungsklassen(buchungsklassen);
    }

    @ApiOperation("Update Buchungsklassen")
    @PutMapping("")
    public BuchungsklassenReadTO updateBuchungsklassen(@ApiParam(name = "Buchungsklassen", value =
            "Buchungsklassen to update") @RequestBody BuchungsklassenReadListTO buchungsklassen) {
        return buchungsklassenService.updateBuchungsklassen(buchungsklassen);
    }

    @ApiOperation("Delete Buchungsklassen")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> daleteBuchungsklassen(
            @ApiParam(name = "BuchungsklassenId", value = "Id of the Buchungsklassen") @PathVariable UUID id) {
        return buchungsklassenService.deleteBuchungsklassen(id);

    }

}
