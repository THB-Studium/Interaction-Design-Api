package com.team.angular.interactiondesignapi.transfertobjects.buchung;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.team.angular.interactiondesignapi.models.Buchung;

public class Buchung2BuchungReadListTO {

	public static BuchungReadListTO apply(Buchung in) {
		BuchungReadListTO out = new BuchungReadListTO();

		out.setId(in.getId());
		out.setDatum(in.getDatum());
		out.setMitReiserId(in.getMitReiserId());
		out.setFlugHafen(in.getFlugHafen());
		out.setHandGepaeck(in.getHandGepaeck());
		out.setKoffer(in.getKoffer());
		out.setZahlungMethod(in.getZahlungMethod());
		out.setReiserId(in.getReiser().getId());
		if(in.getReiseAngebot() != null)
			out.setReiseAngebotId(in.getReiseAngebot().getId());

		return out;
	}

	public static Set<BuchungReadListTO> apply(List<Buchung> buchungs) {
		return buchungs.stream().map(u -> apply(u)).collect(Collectors.toSet());
	}

}
