package com.team.angular.interactiondesignapi.transfertobjects.reiser;

import com.team.angular.interactiondesignapi.models.Reiser;

import java.util.List;
import java.util.stream.Collectors;

public class ReiserRead2ReiserTO {

    public static Reiser apply(ReiserReadTO in) {
        Reiser out = new Reiser();

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

    public static List<Reiser> apply(List<ReiserReadTO> lands) {
        return lands.stream().map(u -> apply(u)).collect(Collectors.toList());
    }

}
