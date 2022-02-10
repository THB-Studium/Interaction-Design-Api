package com.team.angular.interactiondesignapi.transfertobjects.reisender;

import com.team.angular.interactiondesignapi.models.Reisender;

import java.util.List;
import java.util.stream.Collectors;

public class ReisenderRead2ReisenderTO {

    public static Reisender apply(ReisenderReadTO in) {
        Reisender out = new Reisender();

        out.setId(in.getId());
        out.setName(in.getName());
        out.setVorname(in.getVorname());
        out.setGeburtsdatum(in.getGeburtsdatum());
        out.setTelefonnummer(in.getTelefonnummer());
        out.setEmail(in.getEmail());
        out.setHochschule(in.getHochschule());
        out.setAdresse(in.getAdresse());
        out.setStudiengang(in.getStudiengang());
        out.setArbeitBei(in.getArbeitBei());
        out.setSchonTeilgenommen(in.isSchonTeilgenommen());

        return out;
    }

    public static List<Reisender> apply(List<ReisenderReadTO> lands) {
        return lands.stream().map(u -> apply(u)).collect(Collectors.toList());
    }

}
