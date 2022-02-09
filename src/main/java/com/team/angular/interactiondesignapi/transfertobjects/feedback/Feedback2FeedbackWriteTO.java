package com.team.angular.interactiondesignapi.transfertobjects.feedback;

import com.team.angular.interactiondesignapi.models.Feedback;

import java.util.UUID;


public class Feedback2FeedbackWriteTO {

    public static FeedbackWriteTO apply(Feedback in) {
        FeedbackWriteTO out = new FeedbackWriteTO();

        out.setId(in.getId());
        out.setAutor(in.getAutor());
        out.setVeroeffentlich(in.isVeroeffentlich());
        out.setDescription(in.getDescription());
        if (in.getBild() != null)
            out.setBild("data:image/png;base64," + UUID.randomUUID().toString());
        return out;
    }

}
