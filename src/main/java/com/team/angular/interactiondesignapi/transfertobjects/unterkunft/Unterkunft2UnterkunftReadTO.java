package com.team.angular.interactiondesignapi.transfertobjects.unterkunft;

import java.util.List;
import java.util.stream.Collectors;

import com.team.angular.interactiondesignapi.models.Unterkunft;
import com.team.angular.interactiondesignapi.transfertobjects.land.Land2LandReadTO;

public class Unterkunft2UnterkunftReadTO {

	public static UnterkunftReadTO apply(Unterkunft in) {
		UnterkunftReadTO out = new UnterkunftReadTO();

		out.setId(in.getId());
		out.setName(in.getName());
		out.setLink(in.getLink());
		out.setAdresse(in.getAdresse());
		out.setBeschreibung(in.getBeschreibung());
		out.setLand(Land2LandReadTO.apply(in.getLand()));
		out.setBilder(in.getBilder());

		return out;
	}

	public static List<UnterkunftReadTO> apply(List<Unterkunft> unterkunfte) {
		return unterkunfte.stream().map(u -> apply(u)).collect(Collectors.toList());
	}

}
