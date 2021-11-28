package com.team.angular.interactiondesignapi.transfertobjects.hightlights;

import java.util.List;
import java.util.stream.Collectors;

import com.team.angular.interactiondesignapi.models.Highlight;
import com.team.angular.interactiondesignapi.transfertobjects.land.Land2LandReadTO;

public class Highlight2HighlightReadTO {

	public static HighlightReadTO apply(Highlight in) {
		HighlightReadTO out = new HighlightReadTO();


		out.setName(in.getName());
		out.setDescription(in.getDescription());
		out.setBild(in.getBild());
		out.setLand(Land2LandReadTO.apply(in.getLand()));

		return out;
	}

	public static List<HighlightReadTO> apply(List<Highlight> lands) {
		return lands.stream().map(u -> apply(u)).collect(Collectors.toList());
	}

}
