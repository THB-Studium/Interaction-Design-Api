package com.team.angular.interactiondesignapi.transfertobjects.unterkunft;

import java.util.List;
import java.util.stream.Collectors;

import com.team.angular.interactiondesignapi.models.Unterkunft;

public class Unterkunft2UnterkunftReadListTO {

	public static UnterkunftReadListTO apply(Unterkunft in) {
		UnterkunftReadListTO out = new UnterkunftReadListTO();

		out.setId(in.getId());
		out.setName(in.getName());
		out.setLink(in.getLink());
		out.setAdresse(in.getAdresse());
		out.setBeschreibung(in.getBeschreibung());

		return out;
	}

	public static List<UnterkunftReadListTO> apply(List<Unterkunft> unterkunfte) {
		return unterkunfte.stream().map(u -> apply(u)).collect(Collectors.toList());
	}

}
