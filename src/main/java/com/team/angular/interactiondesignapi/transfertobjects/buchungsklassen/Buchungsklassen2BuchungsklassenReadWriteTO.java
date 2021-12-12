package com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen;

import com.team.angular.interactiondesignapi.models.Buchungsklassen;

import java.util.List;
import java.util.stream.Collectors;

public class Buchungsklassen2BuchungsklassenReadWriteTO {

    public static BuchungsklassenReadWriteTO apply(Buchungsklassen in) {
        BuchungsklassenReadWriteTO out = new BuchungsklassenReadWriteTO();

        out.setId(in.getId());
        out.setType(in.getType());
        out.setPreis(in.getPreis());
        if (in.getReiseAngebot() != null)
            out.setReiseAngebotId(in.getReiseAngebot().getId());

        return out;
    }

    public static List<BuchungsklassenReadWriteTO> apply(List<Buchungsklassen> buchungsklassens) {
        return buchungsklassens.stream().map(buchungsklassen -> apply(buchungsklassen)).collect(Collectors.toList());
    }

}
