package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Feedback;
import com.team.angular.interactiondesignapi.repositories.FeedbackRepository;
import com.team.angular.interactiondesignapi.transfertobjects.feedback.Feedback2FeedbackListTO;
import com.team.angular.interactiondesignapi.transfertobjects.feedback.FeedbackReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.feedback.FeedbackWriteTO;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.team.angular.interactiondesignapi.config.CompressImage.compressBild;

@Service
public class FeedbackService {

    private static final Logger log = LoggerFactory.getLogger(FeedbackService.class);
    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<FeedbackReadListTO> getAll() {
        return Feedback2FeedbackListTO.apply(feedbackRepository.findAll());
    }

    public Feedback addFeedback(FeedbackWriteTO feedback) throws Exception {

        Feedback newFeedback = new Feedback();
        newFeedback.setAutor(feedback.getAutor());
        newFeedback.setDescription(feedback.getDescription());
        newFeedback.setVeroefentlich(false);

        if (feedback.getBild() != null) {
            newFeedback.setBild(compressBild(feedback.getBild()));
        }

        return feedbackRepository.save(newFeedback);

    }

    public Feedback getFeedback(UUID id) {

        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Feedback with id: " + id));

        // for what?
        feedback = feedbackRepository.findById(id).get();

        return feedback;
    }

    public Feedback updateFeedback(FeedbackWriteTO feedback_) {

        Feedback feedback = getFeedback(feedback_.getId());

        if (feedback_.getAutor() != null)
            feedback.setAutor(feedback_.getAutor());
        if (feedback_.getBild() != null)
            feedback.setBild(Base64.decodeBase64(feedback_.getBild().substring(22)));
        if (feedback_.getDescription() != null)
            feedback.setDescription(feedback_.getDescription());

        feedback.setVeroefentlich(feedback_.isVeroefentlich());

        return feedbackRepository.save(feedback);
    }

    public ResponseEntity<?> deleteFeedback(UUID id) {
        Feedback actual = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Feedback with id: " + id));

        feedbackRepository.deleteById(actual.getId());
        log.info("successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

}
