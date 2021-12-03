package com.team.angular.interactiondesignapi.transfertobjects.buchung;

import java.util.Set;
import java.util.stream.Collectors;

import com.team.angular.interactiondesignapi.models.Buchung;

public class Buchung2BuchungReadTO {

	public static BuchungReadTO apply(Buchung in) {
		BuchungReadTO out = new BuchungReadTO();

		out.setId(in.getId());
		out.setDatum(in.getDatum());
		out.setMitReiser(in.getMitReiser());
		out.setFlugAhfen(in.getFlugAhfen());
		out.setHandGepaeck(in.getHandGepaeck());
		out.setKoffer(in.getKoffer());
		out.setZahlungMethod(in.getZahlungMethod());
		out.setReiserId(in.getReiser().getId());
		out.setLandId(in.getLand().getId());

		return out;
	}

	public static Set<BuchungReadTO> apply(Set<Buchung> buchungs) {
		return buchungs.stream().map(u -> apply(u)).collect(Collectors.toSet());
	}

}
