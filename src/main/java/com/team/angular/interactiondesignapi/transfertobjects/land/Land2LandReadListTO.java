package com.team.angular.interactiondesignapi.transfertobjects.land;

import java.util.List;
import java.util.stream.Collectors;

import com.team.angular.interactiondesignapi.models.Land;

public class Land2LandReadListTO {

	public static LandReadListTO apply(Land in) {
		LandReadListTO out = new LandReadListTO();

		out.setId(in.getId());
		out.setName(in.getName());
		out.setStartDatum(in.getStartDatum());
		out.setEndDatum(in.getEndDatum());
		out.setTitel(in.getTitel());
		out.setPlaetze(in.getPlaetze());
		out.setFreiPlaetze(in.getFreiPlaetze());
		out.setAnmeldungsFrist(in.getAnmeldungsFrist());

		return out;
	}

	public static List<LandReadListTO> apply(List<Land> lands) {
		return lands.stream().map(u -> apply(u)).collect(Collectors.toList());
	}

}
