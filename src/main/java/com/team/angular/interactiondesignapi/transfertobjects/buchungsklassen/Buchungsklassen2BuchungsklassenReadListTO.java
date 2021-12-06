package com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen;

import com.team.angular.interactiondesignapi.models.Buchungsklassen;

import java.util.List;
import java.util.stream.Collectors;

public class Buchungsklassen2BuchungsklassenReadListTO {
    public static BuchungsklassenReadListTO apply(Buchungsklassen in) {
        BuchungsklassenReadListTO out = new BuchungsklassenReadListTO();
        out.setId(in.getId());
        out.setType(in.getType());
        out.setPreis(in.getPreis());
        return out;
    }

    public static List<BuchungsklassenReadListTO> apply(List<Buchungsklassen> buchungsklassens) {
        return buchungsklassens.stream().map(buchungsklassen -> apply(buchungsklassen)).collect(Collectors.toList());
    }
}
