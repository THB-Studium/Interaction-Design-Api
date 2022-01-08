package com.team.angular.interactiondesignapi.transfertobjects.land;

import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.transfertobjects.hightlight.Highlight2HighlightReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.landInfo.LandInfo2LandInfoReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.unterkunft.Unterkunft2UnterkunftReadListTO;

import java.util.List;
import java.util.stream.Collectors;

public class Land2LandReadListTO {
    public static LandReadListTO apply(Land in) {
        LandReadListTO out = new LandReadListTO();

        out.setId(in.getId());
        out.setName(in.getName());
        out.setFlughafen(in.getFlughafen());
        out.setUnterkunft_text(in.getUnterkunft_text());

        return out;
    }

    public static List<LandReadListTO> apply(List<Land> lands) {
        return lands.stream().map(u -> apply(u)).collect(Collectors.toList());
    }
}
