package com.team.angular.interactiondesignapi.controllers;

import com.team.angular.interactiondesignapi.models.Feedback;
import com.team.angular.interactiondesignapi.services.FeedbackService;
import com.team.angular.interactiondesignapi.transfertobjects.feedback.FeedbackReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.feedback.FeedbackWriteTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/feedbacks")
@CrossOrigin(origins = "*")
public class FeedbacksController {

    @Autowired
    protected FeedbackService feedbackService;

    @ApiOperation("Get All Feedbacks")
    @GetMapping("")
    public List<FeedbackReadListTO> getAllFeedbacks( @RequestParam(defaultValue = "0") Integer pageNo,
                                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                                     @RequestParam(defaultValue = "id") String sortBy) {
        return feedbackService.getAll(pageNo, pageSize, sortBy);
    }

    @ApiOperation("Get One Feedback")
    @GetMapping("/{id}")
    public Feedback getFeedbackById(@ApiParam(name = "FeedbackId", value = "ID of the Feedback") @PathVariable UUID id) {
        return feedbackService.getFeedback(id);
    }

    @ApiOperation("Add One Feedback")
    @PostMapping("")
    public Feedback addFeedback(
            @ApiParam(name = "FeedbackWriteTO", value = "Feedback to add") @RequestBody FeedbackWriteTO feedback) {
        return feedbackService.addFeedback(feedback);
    }

    @ApiOperation("Update Feedback")
    @PutMapping("")
    public Feedback updateFeedback(
            @ApiParam(name = "FeedbackWriteTO", value = "Feedback to update") @RequestBody FeedbackWriteTO feedback) {
        return feedbackService.updateFeedback(feedback);
    }

    @ApiOperation("Delete Feedback")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFeedback(
            @ApiParam(name = "FeedbackId", value = "Id of the Feedback") @PathVariable UUID id) {
        return feedbackService.deleteFeedback(id);

    }

}
