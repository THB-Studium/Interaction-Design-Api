package com.team.angular.interactiondesignapi.transfertobjects.erwartungen;

import com.team.angular.interactiondesignapi.models.Erwartungen;

import java.util.List;
import java.util.stream.Collectors;

public class Erwartungen2ErwartungenReadListTO {
    public static ErwartungenReadListTO apply(Erwartungen in) {
        ErwartungenReadListTO out = new ErwartungenReadListTO();

        out.setId(in.getId());
        out.setAbenteuer(in.getAbenteuer());
        out.setEntschleunigung(in.getEntschleunigung());
        out.setKonfort(in.getKonfort());
        out.setNachhaltigkeit(in.getNachhaltigkeit());
        out.setSonne_strand(in.getSonne_strand());
        out.setSicherheit(in.getSicherheit());
        out.setRoad(in.getRoad());

        return out;
    }

    public static List<ErwartungenReadListTO> apply(List<Erwartungen> erwartungenList) {
        return erwartungenList.stream().map(erwartungen -> apply(erwartungen)).collect(Collectors.toList());
    }

}
