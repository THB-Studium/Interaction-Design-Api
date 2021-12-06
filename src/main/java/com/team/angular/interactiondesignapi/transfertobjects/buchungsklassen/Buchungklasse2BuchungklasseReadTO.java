package com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen;

import com.team.angular.interactiondesignapi.models.Buchungsklassen;

import java.util.List;
import java.util.stream.Collectors;

public class Buchungklasse2BuchungklasseReadTO {

    public static BuchungsklassenReadTO apply(Buchungsklassen in) {
        BuchungsklassenReadTO out = new BuchungsklassenReadTO();

        out.setId(in.getId());
        out.setType(in.getType());
        out.setPreis(in.getPreis());
        out.setReiseAngebot_id(in.getReiseAngebot().getId());

        return out;
    }

    public static List<BuchungsklassenReadTO> apply(List<Buchungsklassen> buchungsklassens) {
        return buchungsklassens.stream().map(buchungsklassen -> apply(buchungsklassen)).collect(Collectors.toList());
    }

}
