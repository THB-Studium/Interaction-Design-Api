package com.team.angular.interactiondesignapi.transfertobjects.hightlights;

import java.util.List;
import java.util.stream.Collectors;

import com.team.angular.interactiondesignapi.models.Highlight;

public class Highlight2HighlightReadListTO {

	public static HighlightReadTO apply(Highlight in) {
		HighlightReadTO out = new HighlightReadTO();


		out.setName(in.getName());
		out.setDescription(in.getDescription());

		return out;
	}

	public static List<HighlightReadTO> apply(List<Highlight> lands) {
		return lands.stream().map(u -> apply(u)).collect(Collectors.toList());
	}

}
