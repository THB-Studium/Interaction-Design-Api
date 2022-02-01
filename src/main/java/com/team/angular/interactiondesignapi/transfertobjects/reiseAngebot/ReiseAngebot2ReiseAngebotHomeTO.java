package com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot;

import com.team.angular.interactiondesignapi.models.ReiseAngebot;

import java.util.List;
import java.util.stream.Collectors;

public class ReiseAngebot2ReiseAngebotHomeTO {
    public static ReiseAngebotHomeTO apply(ReiseAngebot in) {
        ReiseAngebotHomeTO out = new ReiseAngebotHomeTO();
        out.setId(in.getId());
        out.setTitel(in.getTitel());
        out.setStartbild(in.getStartbild());
        out.setStartDatum(in.getStartDatum());
        out.setEndDatum(in.getEndDatum());
        if (in.getLand() != null)
            out.setLandName(in.getLand().getName());

        return out;
    }

    public static List<ReiseAngebotHomeTO> apply(List<ReiseAngebot> reiseAngebots) {
        return reiseAngebots.stream().map(reiseAngebot -> apply(reiseAngebot)).collect(Collectors.toList());
    }
}
