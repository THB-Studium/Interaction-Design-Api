package com.team.angular.interactiondesignapi.transfertobjects.reiser;

import java.util.List;
import java.util.stream.Collectors;

import com.team.angular.interactiondesignapi.models.Reiser;

public class Reiser2ReiserReadTO {

	public static ReiserReadTO apply(Reiser in) {
		ReiserReadTO out = new ReiserReadTO();

		out.setId(in.getId());
		out.setName(in.getName());
		out.setVorname(in.getVorname());
		out.setGeburtsdatum(in.getGeburtsdatum());
		out.setTelefonnummer(in.getTelefonnummer());
		out.setEmail(in.getEmail());
		out.setHochschule(in.getHochschule());
		out.setAdresse(in.getAdresse());
		out.setStudiengang(in.getStudiengang());
		out.setStatus(in.getStatus());
		out.setArbeitBei(in.getArbeitBei());
		out.setSchonTeilgenommen(in.isSchonTeilgenommen());
		return out;
	}
	public static List<ReiserReadTO> apply(List<Reiser> lands) {
		return lands.stream().map(u -> apply(u)).collect(Collectors.toList());
	}

}
