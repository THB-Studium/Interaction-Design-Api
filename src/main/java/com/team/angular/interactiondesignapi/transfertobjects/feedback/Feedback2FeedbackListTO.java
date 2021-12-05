package com.team.angular.interactiondesignapi.transfertobjects.feedback;

import java.util.List;
import java.util.stream.Collectors;

import com.team.angular.interactiondesignapi.models.Feedback;

public class Feedback2FeedbackListTO {
	
	public static FeedbackReadListTO apply(Feedback in) {
		FeedbackReadListTO out = new FeedbackReadListTO();

		out.setId(in.getId());
		out.setAutor(in.getAutor());
		out.setVeroefentlich(in.isVeroefentlich());
		out.setDescription(in.getDescription());
		return out;
	}

	public static List<FeedbackReadListTO> apply(List<Feedback> lands) {
		return lands.stream().map(u -> apply(u)).collect(Collectors.toList());
	}

}
