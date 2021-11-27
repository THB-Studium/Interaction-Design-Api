package com.team.angular.interactiondesignapi.services;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Feedback;
import com.team.angular.interactiondesignapi.repositories.FeedbackRepository;

@Service
public class FeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepository;

	private static final Logger log = LoggerFactory.getLogger(FeedbackService.class);

	public List<Feedback> getAll() {
		return feedbackRepository.findAll();
	}

	public Feedback addFeedback(Feedback feedback) {

		Feedback newFeedback = new Feedback();
		newFeedback.setAutor(feedback.getAutor());
		newFeedback.setDescription(feedback.getDescription());

		Feedback saved = feedbackRepository.save(newFeedback);

		return saved;

	}

	public Feedback getFeedback(UUID id) {

		Feedback feedback = feedbackRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find Feedback with id: " + id));

		feedback = feedbackRepository.findById(id).get();

		return feedback;

	}

	public Feedback updateFeedback(Feedback feedback) {

		Feedback actual = feedbackRepository.findById(feedback.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find Feedback with id: " + feedback.getId()));

		actual.setAutor(feedback.getAutor());

		actual.setDescription(feedback.getDescription());

		Feedback saved = feedbackRepository.save(actual);

		return saved;
	}

	public ResponseEntity<?> deleteFeedback(UUID id) {
		Feedback actual = feedbackRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find Feedback with id: " + id));

		feedbackRepository.deleteById(actual.getId());
		log.info("successfully delted");

		return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
	}

}
