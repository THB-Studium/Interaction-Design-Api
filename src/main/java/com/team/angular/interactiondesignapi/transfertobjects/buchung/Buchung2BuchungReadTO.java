package com.team.angular.interactiondesignapi.transfertobjects.buchung;

import com.team.angular.interactiondesignapi.models.Buchung;

import java.util.List;
import java.util.stream.Collectors;

public class Buchung2BuchungReadTO {

    public static BuchungReadTO apply(Buchung in) {
        BuchungReadTO out = new BuchungReadTO();

        out.setId(in.getId());
        out.setBuchungsnummer(in.getBuchungsnummer());
        out.setBuchungDatum(in.getBuchungDatum());
        out.setMitReisenderId(in.getMitReisenderId());
        out.setBuchungsklasseId(in.getBuchungsklasseId());

        out.setAbFlughafenReisender(in.getAbFlughafenReisender());
        out.setAbFlughafenMitReisender(in.getAbFlughafenMitReisender());
        out.setHandGepaeckReisender(in.getHandGepaeckReisender());
        out.setHandGepaeckMitReisender(in.getHandGepaeckMitReisender());
        out.setKofferReisender(in.getKofferReisender());
        out.setKofferMitReisender(in.getKofferMitReisender());

        out.setZahlungMethod(in.getZahlungMethod());
        out.setReisenderId(in.getReisender().getId());
        out.setReiseAngebotId(in.getReiseAngebot().getId());
        out.setStatus(in.getStatus());

        return out;
    }

    public static List<BuchungReadTO> apply(List<Buchung> buchungs) {
        return buchungs.stream().map(u -> apply(u)).collect(Collectors.toList());
    }

}
