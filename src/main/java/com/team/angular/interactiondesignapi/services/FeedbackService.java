package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Feedback;
import com.team.angular.interactiondesignapi.repositories.FeedbackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.team.angular.interactiondesignapi.config.Helper;
import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Feedback;
import com.team.angular.interactiondesignapi.repositories.FeedbackRepository;
import com.team.angular.interactiondesignapi.transfertobjects.feedback.Feedback2FeedbackListTO;
import com.team.angular.interactiondesignapi.transfertobjects.feedback.FeedbackReadListTO;

@Service
public class FeedbackService {

	private static final Logger log = LoggerFactory.getLogger(FeedbackService.class);
	@Autowired
	private FeedbackRepository feedbackRepository;

	public List<FeedbackReadListTO> getAll() {
		return Feedback2FeedbackListTO.apply(feedbackRepository.findAll());
	}

	public Feedback addFeedback(FeedbackReadListTO feedback, MultipartFile bild) {

		Feedback newFeedback = new Feedback();
		newFeedback.setAutor(feedback.getAutor());
		newFeedback.setDescription(feedback.getDescription());
		newFeedback.setVeroefentlich(false);
		newFeedback.setBild(Helper.convertMultiPartFileToByte(bild));

        Feedback saved = feedbackRepository.save(newFeedback);

        return saved;

    }

    public Feedback getFeedback(UUID id) {

        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Feedback with id: " + id));

        feedback = feedbackRepository.findById(id).get();

        return feedback;

    }

	public Feedback updateFeedback(Feedback feedback_, MultipartFile bild) {

		Feedback feedback = getFeedback(feedback_.getId());

		if (feedback_.getAutor() != null)
			feedback.setAutor(feedback_.getAutor());
		if (feedback_.getBild() != null)
			feedback.setBild(Helper.convertMultiPartFileToByte(bild));
		if (feedback_.getDescription() != null)
			feedback.setDescription(feedback_.getDescription());

		return feedback;
	}

    public ResponseEntity<?> deleteFeedback(UUID id) {
        Feedback actual = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Feedback with id: " + id));

        feedbackRepository.deleteById(actual.getId());
        log.info("successfully delted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

}
