package com.team.angular.interactiondesignapi.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team.angular.interactiondesignapi.services.BuchungService;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungUpdateTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungWriteTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/buchungs")
@CrossOrigin(origins = "*")
public class BuchungController {

    @Autowired
    protected BuchungService buchungService;

    @ApiOperation("Get All Buchungs")
    @GetMapping("")
    public List<BuchungReadTO> getAllBuchungs() {
        return buchungService.getAll();
    }

    @ApiOperation("Get One Buchung")
    @GetMapping("/{id}")
    public BuchungReadTO getBuchungById(
            @ApiParam(name = "BuchungId", value = "ID f the Buchung") @PathVariable UUID id) {
        return buchungService.getBuchung(id);
    }
    
    @ApiOperation("Export Buchung als pdf")
    @GetMapping("/exportPdf/{id}")
    public ResponseEntity<ByteArrayResource> exportPdf(
    		@ApiParam(name = "BuchungId", value = "ID f the Buchung") @PathVariable UUID id
    		) throws URISyntaxException, IOException {
    	
    	byte[] data = null;
		try {
			data = buchungService.exportPdf(id);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	ByteArrayResource resource = new ByteArrayResource(data);
    	
    	
        return ResponseEntity
        		.ok().contentLength(data.length)
        		.header("Content-type", "application/octet-stream")
        		.header("Content-disposition", "attachement; filename=Buchung_"+LocalDate.now().toString()+".pdf")
        		.body(resource);
    }

    @ApiOperation("Add One Buchung")
    @PostMapping("")
    public BuchungReadTO addBuchung(
            @ApiParam(name = "Buchung", value = "Buchung to add") @RequestBody BuchungWriteTO buchung) throws Exception {
        return buchungService.addBuchung(buchung);
    }

    @ApiOperation("Update Buchung")
    @PutMapping("")
    public BuchungReadTO updateBuchung(
            @ApiParam(name = "Buchung", value = "Buchung to update") @RequestBody BuchungUpdateTO buchung) {
        return buchungService.updateBuchung(buchung);
    }

    @ApiOperation("Delete Buchung")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> daleteBuchung(
            @ApiParam(name = "BuchungId", value = "Id of the Buchung") @PathVariable UUID id) {
        return buchungService.deleteBuchung(id);
    }

    @ApiOperation("Remove MitReiser Buchung")
    @DeleteMapping("/removeMitReiser/{id}")
    public ResponseEntity<?> removeMitReiser(
            @ApiParam(name = "BuchungId", value = "Id of the Buchung") @PathVariable UUID id) {
        return buchungService.removeMitReiser(id);
    }

}
