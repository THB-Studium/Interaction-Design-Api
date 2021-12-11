package com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot;

import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen.Buchungsklassen2BuchungsklassenReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.erwartungen.Erwartungen2ErwartungenReadListTO;

import java.util.List;
import java.util.stream.Collectors;

public class ReiseAngebot2ReiseAngebotReadTO {
    public static ReiseAngebotReadTO apply(ReiseAngebot in) {
        ReiseAngebotReadTO out = new ReiseAngebotReadTO();
        out.setId(in.getId());
        out.setTitel(in.getTitel());
        out.setStartbild(in.getStartbild());
        out.setStartDatum(in.getStartDatum());
        out.setEndDatum(in.getEndDatum());
        out.setPlaetze(in.getPlaetze());
        out.setFreiPlaetze(in.getFreiPlaetze());
        out.setAnmeldungsFrist(in.getAnmeldungsFrist());
        out.setLeistungen(in.getLeistungen());

        if(in.getBuchungsklassen() != null)
        	out.setBuchungsklassenReadListTO(Buchungsklassen2BuchungsklassenReadListTO.apply(in.getBuchungsklassen()));
        if(in.getErwartungen() != null)
        	out.setErwartungenReadListTO(Erwartungen2ErwartungenReadListTO.apply(in.getErwartungen()));
        if(in.getLand() != null)
        	out.setLand_id(in.getLand().getId());

        return out;
    }

    public static List<ReiseAngebotReadTO> apply(List<ReiseAngebot> reiseAngebots) {
        return reiseAngebots.stream().map(reiseAngebot -> apply(reiseAngebot)).collect(Collectors.toList());
    }
}
