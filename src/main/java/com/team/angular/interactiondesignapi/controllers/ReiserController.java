package com.team.angular.interactiondesignapi.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.team.angular.interactiondesignapi.services.ReiserService;
import com.team.angular.interactiondesignapi.transfertobjects.reiser.ReiserReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.reiser.ReiserReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.reiser.ReiserWriteTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/reisers")
@CrossOrigin(origins = "*")
public class ReiserController {

	@Autowired
	protected ReiserService reiserService;

	@ApiOperation("Get All Reisers")
	@GetMapping("")
	public List<ReiserReadListTO> getAllReisers() {
		return reiserService.getAll();
	}

	@ApiOperation("Get One Reiser")
	@GetMapping("/{id}")
	public ReiserReadTO getReiserById(@ApiParam(name = "ReiserId", value = "ID of the Reiser") @PathVariable UUID id) {
		return reiserService.getReiser(id);
	}

	@ApiOperation("Add One Reiser")
	@PostMapping("")
	public ReiserReadTO addReiser(
			@ApiParam(name = "Reiser", value = "Reiser to add") @RequestBody ReiserWriteTO reiser) {
		return reiserService.addReiser(reiser);
	}

	@ApiOperation("Update Reiser")
	@PutMapping("")
	public ReiserReadTO updateReiser(
			@ApiParam(name = "Reiser", value = "Reiser to update") @RequestBody ReiserWriteTO reiser) {
		return reiserService.updateReiser(reiser);
	}

	@ApiOperation("Delete Reiser")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteReiser(
			@ApiParam(name = "ReiserId", value = "Id of the Reiser") @PathVariable UUID id) {
		return reiserService.deleteReiser(id);

	}

}
