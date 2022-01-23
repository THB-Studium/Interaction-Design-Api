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

import com.team.angular.interactiondesignapi.models.Feedback;
import com.team.angular.interactiondesignapi.services.FeedbackService;
import com.team.angular.interactiondesignapi.transfertobjects.feedback.FeedbackReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.feedback.FeedbackWriteTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/feedbacks")
@CrossOrigin(origins = "*")
public class FeedbacksController {

	@Autowired
	protected FeedbackService feedbackService;

	@ApiOperation("Get All Feedbacks")
	@GetMapping("")
	public List<FeedbackReadListTO> getAllFeedbacks() {
		return feedbackService.getAll();
	}

	@ApiOperation("Get One Feedback")
	@GetMapping("/{id}")
	public Feedback getFeedbackById(@ApiParam(name = "FeedbackId", value = "ID of the Feedback") @PathVariable UUID id) {
		return feedbackService.getFeedback(id);
	}

	@ApiOperation("Add One Feedback")
	@PostMapping("")
	public Feedback addFeedback(
			@ApiParam(name = "Feedback", value = "Feedback to add") @RequestBody FeedbackWriteTO feedback) {
		return feedbackService.addFeedback(feedback);
	}

	@ApiOperation("Update Feedback")
	@PutMapping("")
	public Feedback updateFeedback(
			@ApiParam(name = "Feedback", value = "Feedback to update") @RequestBody FeedbackWriteTO feedback) {
		return feedbackService.updateFeedback(feedback);
	}

	@ApiOperation("Delete Feedback")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> daleteFeedback(
			@ApiParam(name = "FeedbackId", value = "Id of the Feedback") @PathVariable UUID id) {
		return feedbackService.deleteFeedback(id);

	}

}
