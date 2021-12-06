package com.team.angular.interactiondesignapi.transfertobjects.land_info;

import com.team.angular.interactiondesignapi.models.Land_info;

import java.util.List;
import java.util.stream.Collectors;

public class Land_info2Land_infoReadTO {

    public static Land_infoReadTO apply(Land_info in) {
        Land_infoReadTO out = new Land_infoReadTO();

        out.setId(in.getId());
        out.setTitel(in.getTitel());
        out.setDescription(in.getDescription());
        out.setLand_id(in.getLand().getId());

        return out;
    }

    public static List<Land_infoReadTO> apply(List<Land_info> land_infos) {
        return land_infos.stream().map(land_info -> apply(land_info)).collect(Collectors.toList());
    }
}
