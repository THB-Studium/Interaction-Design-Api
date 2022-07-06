package com.team.angular.interactiondesignapi.transfertobjects.reisender;

import com.team.angular.interactiondesignapi.models.Reisender;

import java.util.List;
import java.util.stream.Collectors;

public class Reisender2ReisenderReadTO {

    public static ReisenderReadTO apply(Reisender in) {
        ReisenderReadTO out = new ReisenderReadTO();

        out.setId(in.getId());
        out.setName(in.getName());
        out.setVorname(in.getVorname());
        out.setGeburtsdatum(in.getGeburtsdatum());
        out.setTelefonnummer(in.getTelefonnummer());
        out.setEmail(in.getEmail());
        out.setHochschule(in.getHochschule());
        out.setAdresse(in.getAdresse());
        out.setStudiengang(in.getStudiengang());
        out.setStatus(in.getStatus());
        out.setArbeitBei(in.getArbeitBei());
        out.setSchonTeilgenommen(in.isSchonTeilgenommen());
        out.setIdentityCard(in.getIdentity_card());
        return out;
    }

    public static List<ReisenderReadTO> apply(List<Reisender> lands) {
        return lands.stream().map(u -> apply(u)).collect(Collectors.toList());
    }

}
