package com.team.angular.interactiondesignapi.transfertobjects.erwartungen;

import com.team.angular.interactiondesignapi.models.Erwartungen;

public class ErwartungenReadList2Erwartung {
    public static Erwartungen apply(ErwartungenReadListTO in) {
        Erwartungen out = new Erwartungen();

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

}
