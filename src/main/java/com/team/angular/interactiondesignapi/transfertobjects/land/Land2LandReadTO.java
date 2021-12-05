package com.team.angular.interactiondesignapi.transfertobjects.land;

import java.util.List;
import java.util.stream.Collectors;

import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.transfertobjects.hightlights.Highlight2HighlightReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.land_info.Land_info2Land_infoReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.unterkunft.Unterkunft2UnterkunftReadListTO;

public class Land2LandReadTO {

	public static LandReadTO apply(Land in) {
		LandReadTO out = new LandReadTO();

		out.setId(in.getId());
		out.setName(in.getName());
		out.setFlughafen(in.getFlughafen());
		out.setUnterkunft_text(in.getUnterkunft_text());
		out.setCorona_infos(in.getCorona_infos());
		out.setKarte_bild(in.getKarte_bild());
		out.setKlima(in.getKlima());
		out.setGesundheit(in.getGesundheit());
		out.setReiseOrdnung(in.getReiseOrdnung());
		out.setHinweise(in.getHinweise());
		out.setMitReiserBerechtigt(in.getMitReiserBerechtigt());
		out.setSonstigeHinweise(in.getSonstigeHinweise());
		//out.setReiseAngebot(ReiseAngebot2ReiseAngebotReadTO.apply(in.getReiseAngebot()));

		if(in.getInfos_Land() != null)
			out.setInfosLands(Land_info2Land_infoReadListTO.apply(in.getInfos_Land()));
		if(in.getHighlights() != null)
			out.setHighlights(Highlight2HighlightReadListTO.apply(in.getHighlights()));
		if(in.getUnterkunft() != null)
			out.setUnterkunft(Unterkunft2UnterkunftReadListTO.apply(in.getUnterkunft()));
		
		return out;
	}

	public static List<LandReadTO> apply(List<Land> lands) {
		return lands.stream().map(u -> apply(u)).collect(Collectors.toList());
	}

}
