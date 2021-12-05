package com.team.angular.interactiondesignapi.transfertobjects.land_info;

import com.team.angular.interactiondesignapi.models.Land_info;

import java.util.List;
import java.util.stream.Collectors;

public class Land_info2Land_infoReadListTO {

    public static Land_infoReadListTO apply(Land_info in) {
        Land_infoReadListTO out = new Land_infoReadListTO();
        out.setId(in.getId());
        out.setTitel(in.getTitel());
        out.setDescription(in.getDescription());
        return out;
    }

    public static List<Land_infoReadListTO> apply(List<Land_info> land_infos) {
        return land_infos.stream().map(land_info -> apply(land_info)).collect(Collectors.toList());
    }
}
