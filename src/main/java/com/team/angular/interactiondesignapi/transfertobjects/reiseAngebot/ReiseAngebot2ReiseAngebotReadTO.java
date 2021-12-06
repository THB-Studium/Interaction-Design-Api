package com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot;

import com.team.angular.interactiondesignapi.models.ReiseAngebot;

import java.util.List;
import java.util.stream.Collectors;

public class ReiseAngebot2ReiseAngebotReadTO {
    public static ReiseAngebotReadListTO apply(ReiseAngebot in) {
        ReiseAngebotReadListTO out = new ReiseAngebotReadListTO();
        out.setId(in.getId());
        out.setTitel(in.getTitel());
        out.setStartbild(in.getStartbild());
        out.setStartDatum(in.getStartDatum());
        out.setEndDatum(in.getEndDatum());
        out.setPlaetze(in.getPlaetze());
        out.setFreiPlaetze(in.getFreiPlaetze());
        out.setAnmeldungsFrist(in.getAnmeldungsFrist());

        return out;
    }

    public static List<ReiseAngebotReadListTO> apply(List<ReiseAngebot> reiseAngebots) {
        return reiseAngebots.stream().map(reiseAngebot -> apply(reiseAngebot)).collect(Collectors.toList());
    }
}
