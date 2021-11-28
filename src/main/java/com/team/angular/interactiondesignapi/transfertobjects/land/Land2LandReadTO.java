package com.team.angular.interactiondesignapi.transfertobjects.land;

import java.util.List;
import java.util.stream.Collectors;

import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.Buchung2BuchungReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.hightlights.Highlight2HighlightReadTO;

public class Land2LandReadTO {

	public static LandReadTO apply(Land in) {
		LandReadTO out = new LandReadTO();

		out.setId(in.getId());
		out.setName(in.getName());
		out.setBild(in.getBild());
		out.setStartDatum(in.getStartDatum());
		out.setEndDatum(in.getEndDatum());
		out.setTitel(in.getTitel());
		out.setKarteBild(in.getKarteBild());
		out.setPlaetze(in.getPlaetze());
		out.setFreiPlaetze(in.getFreiPlaetze());
		out.setAnmeldungsFrist(in.getAnmeldungsFrist());
		out.setLeistungen(in.getLeistungen());
		
//		if(in.getUnterkunft() != null) {
//			out.setUnterkunft(Unterkunft2UnterkunftReadListTO.apply(in.getUnterkunft()));
//		}
		if(in.getBuchungen() != null) {
			out.setBuchungen(Buchung2BuchungReadListTO.apply(in.getBuchungen()));
		}
		if(in.getHighlights() != null) {
			out.setHighlights(Highlight2HighlightReadTO.apply(in.getHighlights()));
		}
		if(in.getErwartungen() != null) {
			out.setErwartungenId(in.getErwartungen().getId());
		}
		if(in.getInfos_Land() != null) {
			out.setInfosLandId(in.getInfos_Land().getId());
		}
		if(in.getBuchungsklassen() != null) {
			out.setBuchungsklassenId(in.getBuchungsklassen().getId());
		}

		return out;
	}

	public static List<LandReadTO> apply(List<Land> lands) {
		return lands.stream().map(u -> apply(u)).collect(Collectors.toList());
	}

}
