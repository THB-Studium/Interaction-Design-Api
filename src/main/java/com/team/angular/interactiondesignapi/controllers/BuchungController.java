package com.team.angular.interactiondesignapi.controllers;

import com.team.angular.interactiondesignapi.models.Buchungstatus;
import com.team.angular.interactiondesignapi.services.BuchungService;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungUpdateTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungWriteTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/buchungen")
@CrossOrigin(origins = "*")
public class BuchungController {

    @Autowired
    protected BuchungService buchungService;

    @ApiOperation("Get All Buchungen")
    @GetMapping("")
    public List<BuchungReadTO> getAllBuchungs(@RequestParam(defaultValue = "0") Integer pageNo,
                                              @RequestParam(defaultValue = "10") Integer pageSize,
                                              @RequestParam(defaultValue = "buchungDatum") String sortBy) {
        return buchungService.getAll(pageNo, pageSize, sortBy);
    }

    @ApiOperation("Get One Buchung")
    @GetMapping("/{id}")
    public BuchungReadTO getBuchungById(
            @ApiParam(name = "BuchungId", value = "ID of the Buchung") @PathVariable UUID id) {
        return buchungService.getBuchung(id);
    }

    @ApiOperation("Export Buchung als pdf")
    @GetMapping("/exportPdf/{id}")
    public ResponseEntity<ByteArrayResource> exportPdf(
            @ApiParam(name = "BuchungId", value = "ID of the Buchung") @PathVariable UUID id)
            throws URISyntaxException, IOException {

        byte[] data = null;
        try {
            data = buchungService.exportPdf(id);
        } catch (JRException e) {
            e.printStackTrace();
        }

        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity
                .ok().contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachement; filename=Buchung_" + LocalDate.now()
                        .toString() + ".pdf")
                .body(resource);
    }

    @ApiOperation("Add One Buchung")
    @PostMapping("")
    public BuchungReadTO addBuchung(@ApiParam(name = "Buchung", value = "Buchung to add")
                                    @RequestBody BuchungWriteTO buchung) throws Exception {
        return buchungService.addBuchung(buchung);
    }

    @ApiOperation("Update Buchung")
    @PutMapping("")
    public BuchungReadTO updateBuchung(
            @ApiParam(name = "Buchung", value = "Buchung to update") @RequestBody BuchungUpdateTO buchung) throws JRException, URISyntaxException, IOException {
        return buchungService.updateBuchung(buchung);
    }

    @ApiOperation("Delete Buchung")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> daleteBuchung(
            @ApiParam(name = "BuchungId", value = "Id of the Buchung") @PathVariable UUID id) {
        return buchungService.deleteBuchung(id);
    }

    @ApiOperation("Remove MitReisender Buchung")
    @DeleteMapping("/removeMitReisender/{id}")
    public ResponseEntity<?> removeMitReiser(
            @ApiParam(name = "BuchungId", value = "Id of the Buchung") @PathVariable UUID id) {
        return buchungService.removeMitReisender(id);
    }

    @ApiOperation("change Buchung status")
    @PostMapping("/changestatus/{id}/{status}")
    public ResponseEntity<?> changeStatus(@ApiParam(name = "Buchung", value = "Buchung to add")
                                          @PathVariable UUID id, @PathVariable Buchungstatus status) {
        return buchungService.changeStatus(id, status);
    }

}
