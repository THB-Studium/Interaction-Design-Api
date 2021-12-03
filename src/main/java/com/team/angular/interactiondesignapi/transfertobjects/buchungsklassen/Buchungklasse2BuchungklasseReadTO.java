package com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen;

import java.util.Set;
import java.util.stream.Collectors;

import com.team.angular.interactiondesignapi.models.Buchungsklassen;
import com.team.angular.interactiondesignapi.transfertobjects.land.Land2LandReadTO;

public class Buchungklasse2BuchungklasseReadTO {

	public static BuchungsklassenReadTO apply(Buchungsklassen in) {
		BuchungsklassenReadTO out = new BuchungsklassenReadTO();

		out.setId(in.getId());
		out.setType(in.getType());
		out.setPreis(in.getPreis());
		out.setLand(Land2LandReadTO.apply(in.getLand()));

		return out;
	}

	public static Set<BuchungsklassenReadTO> apply(Set<Buchungsklassen> buchungs) {
		return buchungs.stream().map(u -> apply(u)).collect(Collectors.toSet());
	}

}
