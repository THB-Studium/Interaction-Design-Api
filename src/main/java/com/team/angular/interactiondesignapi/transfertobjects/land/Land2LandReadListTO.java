package com.team.angular.interactiondesignapi.transfertobjects.land;

import java.util.List;
import java.util.stream.Collectors;

import com.team.angular.interactiondesignapi.models.Land;

public class Land2LandReadListTO {

	public static LandReadListTO apply(Land in) {
		LandReadListTO out = new LandReadListTO();

		out.setId(in.getId());
		out.setName(in.getName());
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

		return out;
	}

	public static List<LandReadListTO> apply(List<Land> lands) {
		return lands.stream().map(u -> apply(u)).collect(Collectors.toList());
	}

}
