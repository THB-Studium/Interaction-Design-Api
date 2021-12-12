package com.team.angular.interactiondesignapi.transfertobjects.landInfo;

import com.team.angular.interactiondesignapi.models.LandInfo;

import java.util.List;
import java.util.stream.Collectors;

public class LandInfo2LandInfoReadWriteTO {

    public static LandInfoReadWriteTO apply(LandInfo in) {
        LandInfoReadWriteTO out = new LandInfoReadWriteTO();

        out.setId(in.getId());
        out.setTitel(in.getTitel());
        out.setDescription(in.getDescription());
        if (in.getLand() != null)
            out.setLandId(in.getLand().getId());

        return out;
    }

    public static List<LandInfoReadWriteTO> apply(List<LandInfo> landInfos) {
        return landInfos.stream().map(landInfo -> apply(landInfo)).collect(Collectors.toList());
    }
}
