package com.team.angular.interactiondesignapi.transfertobjects.buchung;

import com.team.angular.interactiondesignapi.models.Buchung;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Buchung2BuchungReadTO {

    public static BuchungReadTO apply(Buchung in) {
        BuchungReadTO out = new BuchungReadTO();

        out.setId(in.getId());
        out.setDatum(in.getDatum());
        out.setMitReiserId(in.getMitReiserId());
        out.setBuchungsklasseId(in.getBuchungsklasseId());
        out.setFlugHafen(in.getFlugHafen());
        out.setHandGepaeck(in.getHandGepaeck());
        out.setKoffer(in.getKoffer());
        out.setZahlungMethod(in.getZahlungMethod());
        out.setReiserId(in.getReiser().getId());
        out.setReiseAngebotId(in.getReiseAngebot().getId());

        return out;
    }

    public static List<BuchungReadTO> apply(List<Buchung> buchungs) {
        return buchungs.stream().map(u -> apply(u)).collect(Collectors.toList());
    }

}
