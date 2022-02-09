package com.team.angular.interactiondesignapi.transfertobjects.feedback;

import com.team.angular.interactiondesignapi.models.Feedback;

import java.util.List;
import java.util.stream.Collectors;

public class Feedback2FeedbackListTO {

    public static FeedbackReadListTO apply(Feedback in) {
        FeedbackReadListTO out = new FeedbackReadListTO();

        out.setId(in.getId());
        out.setAutor(in.getAutor());
        out.setVeroeffentlich(in.isVeroeffentlich());
        out.setDescription(in.getDescription());
        return out;
    }

    public static List<FeedbackReadListTO> apply(List<Feedback> feedbacks) {
        return feedbacks.stream().map(u -> apply(u)).collect(Collectors.toList());
    }

}
