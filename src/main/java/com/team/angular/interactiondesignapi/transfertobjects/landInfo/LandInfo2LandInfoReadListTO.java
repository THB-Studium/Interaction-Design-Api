package com.team.angular.interactiondesignapi.transfertobjects.landInfo;

import com.team.angular.interactiondesignapi.models.LandInfo;

import java.util.List;
import java.util.stream.Collectors;

public class LandInfo2LandInfoReadListTO {

    public static LandInfoReadListTO apply(LandInfo in) {
        LandInfoReadListTO out = new LandInfoReadListTO();
        out.setId(in.getId());
        out.setTitel(in.getTitel());
        out.setDescription(in.getDescription());
        return out;
    }

    public static List<LandInfoReadListTO> apply(List<LandInfo> landInfos) {
        return landInfos.stream().map(landInfo -> apply(landInfo)).collect(Collectors.toList());
    }
}
