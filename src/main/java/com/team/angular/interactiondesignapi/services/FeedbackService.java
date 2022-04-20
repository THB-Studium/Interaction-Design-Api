package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ApiRequestException;
import com.team.angular.interactiondesignapi.models.Feedback;
import com.team.angular.interactiondesignapi.repositories.FeedbackRepository;
import com.team.angular.interactiondesignapi.transfertobjects.feedback.Feedback2FeedbackListTO;
import com.team.angular.interactiondesignapi.transfertobjects.feedback.FeedbackReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.feedback.FeedbackWriteTO;
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

    public Feedback addFeedback(FeedbackWriteTO feedback) {

        Feedback newFeedback = new Feedback();
        newFeedback.setAutor(feedback.getAutor());
        newFeedback.setDescription(feedback.getDescription());
        newFeedback.setVeroeffentlich(false);

        if (feedback.getBild() != null) {
            newFeedback.setBild(compressBild(feedback.getBild()));
        }

        return feedbackRepository.save(newFeedback);
    }

    public Feedback getFeedback(UUID id) {

        //todo: for what?
        //feedback = feedbackRepository.findById(id).get();

        return feedbackRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Feedback with id: " + id));
    }

    public Feedback updateFeedback(FeedbackWriteTO feedback_) {

        Feedback feedback = getFeedback(feedback_.getId());

        if (feedback_.getAutor() != null)
            feedback.setAutor(feedback_.getAutor());
        if (feedback_.getBild() != null)
            feedback.setBild(compressBild(feedback_.getBild()));
        if (feedback_.getDescription() != null)
            feedback.setDescription(feedback_.getDescription());

        feedback.setVeroeffentlich(feedback_.isVeroeffentlich());

        return feedbackRepository.save(feedback);
    }

    public ResponseEntity<?> deleteFeedback(UUID id) {
        Feedback actual = feedbackRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Feedback with id: " + id));

        feedbackRepository.deleteById(actual.getId());
        log.info("successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

}
