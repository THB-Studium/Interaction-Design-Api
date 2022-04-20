package com.team.angular.interactiondesignapi.controllers;

import com.team.angular.interactiondesignapi.services.ErwartungenService;
import com.team.angular.interactiondesignapi.transfertobjects.erwartungen.ErwartungenReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.erwartungen.ErwartungenReadWriteTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/erwartungen")
@CrossOrigin(origins = "*")
public class ErwartungenController {
    @Autowired
    protected ErwartungenService erwartungenService;

    @ApiOperation("Get All Erwartungen")
    @GetMapping("")
    public List<ErwartungenReadListTO> getAllErwartungen() {
        return erwartungenService.getAll();
    }

    @ApiOperation("Get One Erwartungen")
    @GetMapping("/{id}")
    public ErwartungenReadWriteTO getErwartungenById(
            @ApiParam(name = "ErwartungenId", value = "ID of the Erwartungen") @PathVariable UUID id) {
        return erwartungenService.getErwartungen(id);
    }

    @ApiOperation("Add One Erwartungen")
    @PostMapping("")
    public ErwartungenReadWriteTO addErwartungen(
            @ApiParam(name = "ErwartungenReadWriteTO", value = "Erwartungen to add") @RequestBody ErwartungenReadWriteTO erwartungen) {
        return erwartungenService.addErwartungen(erwartungen);
    }

    @ApiOperation("Update Erwartungen")
    @PutMapping("")
    public ErwartungenReadListTO updateErwartungen(@ApiParam(name = "ErwartungenReadListTO", value = "Erwartungen to update")
                                                   @RequestBody ErwartungenReadListTO erwartungen) {
        return erwartungenService.updateErwartungen(erwartungen);
    }

    @ApiOperation("Delete Erwartungen")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteErwartungen(
            @ApiParam(name = "ErwartungenId", value = "Id of the Erwartungen") @PathVariable UUID id) {
        return erwartungenService.deleteErwartungen(id);
    }

}
