package com.team.angular.interactiondesignapi.transfertobjects.infoLand;

import java.util.List;
import java.util.stream.Collectors;

import com.team.angular.interactiondesignapi.models.Infos_land;

public class InfoLand2InfoLandReadTO {
	
	public static InfoLandReadTO apply(Infos_land in) {
		InfoLandReadTO out = new InfoLandReadTO();

		out.setId(in.getId());
		out.setTitel(in.getTitel());
		out.setBeschreibung(in.getBeschreibung());

		return out;
	}

	public static List<InfoLandReadTO> apply(List<Infos_land> lands) {
		return lands.stream().map(u -> apply(u)).collect(Collectors.toList());
	}

}
