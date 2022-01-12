package com.team.angular.interactiondesignapi.transfertobjects.hightlight;

import java.util.List;
import java.util.stream.Collectors;

import com.team.angular.interactiondesignapi.models.Highlight;

public class Highlight2HighlightReadListTO {

	public static HighlightReadListTO apply(Highlight in) {
		HighlightReadListTO out = new HighlightReadListTO();
		out.setId(in.getId());
		out.setName(in.getName());
		out.setDescription(in.getDescription());
		return out;
	}

	public static List<HighlightReadListTO> apply(List<Highlight> highlights) {
		return highlights.stream().map(highlight -> apply(highlight)).collect(Collectors.toList());
	}

}
