package com.team.angular.interactiondesignapi.transfertobjects.land;

import com.team.angular.interactiondesignapi.models.Land;

import java.util.List;
import java.util.stream.Collectors;

public class Land2LandReadTO {

    public static LandReadTO apply(Land in) {
        LandReadTO out = new LandReadTO();

        out.setId(in.getId());
        out.setName(in.getName());
        out.setFlughafen(in.getFlughafen());
        out.setUnterkunft_text(in.getUnterkunft_text());
        out.setKarte_bild(in.getKarte_bild());

        return out;
    }

    public static List<LandReadTO> apply(List<Land> lands) {
        return lands.stream().map(u -> apply(u)).collect(Collectors.toList());
    }

}
