package com.team.angular.interactiondesignapi.controllers;

import com.team.angular.interactiondesignapi.services.BuchungsklassenService;
import com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen.BuchungsklassenReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen.BuchungsklassenReadWriteTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/buchungsklassen")
@CrossOrigin(origins = "*")
public class BuchungsklassenController {
    @Autowired
    protected BuchungsklassenService buchungsklassenService;

    @ApiOperation(value = "Get One Buchungsklassen", response = BuchungsklassenReadWriteTO.class)
    @GetMapping("/{id}")
    public BuchungsklassenReadWriteTO getBuchungsklassenById(
            @ApiParam(name = "BuchungsklassenId", value = "ID of the Buchungsklassen") @PathVariable UUID id) {
        return buchungsklassenService.getBuchungsklassen(id);
    }

    @ApiOperation(value = "Get All Buchungsklassen")
    @GetMapping("")
    public List<BuchungsklassenReadListTO> getAllBuchungsklassen() {
        return buchungsklassenService.getAll();
    }

    @ApiOperation("Add One Buchungsklassen")
    @PostMapping("")
    public BuchungsklassenReadWriteTO addBuchungsklassen(
            @ApiParam(name = "Buchungsklassen", value = "Buchungsklassen to add") @RequestBody BuchungsklassenReadWriteTO buchungsklassen) throws Exception {
        return buchungsklassenService.addBuchungsklassen(buchungsklassen);
    }

    @ApiOperation("Update Buchungsklassen")
    @PutMapping("")
    public BuchungsklassenReadListTO updateBuchungsklassen(@ApiParam(name = "Buchungsklassen", value =
            "Buchungsklassen to update") @RequestBody BuchungsklassenReadWriteTO buchungsklassen) {
        return buchungsklassenService.updateBuchungsklassen(buchungsklassen);
    }

    @ApiOperation("Delete Buchungsklassen")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBuchungsklassen(
            @ApiParam(name = "BuchungsklassenId", value = "Id of the Buchungsklassen") @PathVariable UUID id) {
        return buchungsklassenService.deleteBuchungsklassen(id);
    }

}
