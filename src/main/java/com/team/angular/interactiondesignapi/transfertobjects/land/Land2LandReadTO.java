package com.team.angular.interactiondesignapi.transfertobjects.land;

import java.util.List;
import java.util.stream.Collectors;

import com.team.angular.interactiondesignapi.models.Land;

public class Land2LandReadTO {

	public static LandReadTO apply(Land in) {
		LandReadTO out = new LandReadTO();

		out.setId(in.getId());
		out.setName(in.getName());
		out.setStartDatum(in.getStartDatum());
		out.setEndDatum(in.getEndDatum());
		out.setTitel(in.getTitel());
		out.setKarteBild(in.getKarteBild());
		out.setPlaetze(in.getPlaetze());
		out.setFreiPlaetze(in.getFreiPlaetze());
		out.setAnmeldungsFrist(in.getAnmeldungsFrist());
		out.setLeistungen(in.getLeistungen());

		return out;
	}

	public static List<LandReadTO> apply(List<Land> lands) {
		return lands.stream().map(u -> apply(u)).collect(Collectors.toList());
	}

}
