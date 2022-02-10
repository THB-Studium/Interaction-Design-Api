package com.team.angular.interactiondesignapi.transfertobjects.reisender;

import com.team.angular.interactiondesignapi.models.Reisender;

import java.util.List;
import java.util.stream.Collectors;

public class Reisender2ReisenderReadListTO {

    public static ReisenderReadListTO apply(Reisender in) {
        ReisenderReadListTO out = new ReisenderReadListTO();

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

    public static List<ReisenderReadListTO> apply(List<Reisender> lands) {
        return lands.stream().map(u -> apply(u)).collect(Collectors.toList());
    }

}
