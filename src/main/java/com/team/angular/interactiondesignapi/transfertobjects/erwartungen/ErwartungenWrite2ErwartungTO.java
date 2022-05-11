package com.team.angular.interactiondesignapi.transfertobjects.erwartungen;

import com.team.angular.interactiondesignapi.models.Erwartungen;

public class ErwartungenWrite2ErwartungTO {
    public static Erwartungen apply(ErwartungenReadWriteTO in) {
        Erwartungen out = new Erwartungen();

        out.setId(in.getId());
        out.setAbenteuer(in.getAbenteuer());
        out.setEntschleunigung(in.getEntschleunigung());
        out.setKonfort(in.getKonfort());
        out.setNachhaltigkeit(in.getNachhaltigkeit());
        out.setSonne_strand(in.getSonne_strand());
        out.setSicherheit(in.getSicherheit());
        out.setRoad(in.getRoad());
        /*if (in.getReiseAngebotId() != null) //todo reiseangebot no need
            out.setReiseAngebot(in.getReiseAngebotId());*/

        return out;
    }

}
