package com.team.angular.interactiondesignapi.transfertobjects.land;

import java.util.List;
import java.util.stream.Collectors;

import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.transfertobjects.hightlight.Highlight2HighlightReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.landInfo.LandInfo2LandInfoReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.unterkunft.Unterkunft2UnterkunftReadListTO;

public class Land2LandReadTO {

    public static LandReadTO apply(Land in) {
        LandReadTO out = new LandReadTO();

        out.setId(in.getId());
        out.setName(in.getName());
        out.setFlughafen(in.getFlughafen());
        out.setUnterkunft_text(in.getUnterkunft_text());
        out.setKarte_bild(in.getKarte_bild());
        if (in.getLandInfo() != null)
            out.setLandInfo(LandInfo2LandInfoReadListTO.apply(in.getLandInfo()));
        if (in.getHighlights() != null)
            out.setHighlights(Highlight2HighlightReadListTO.apply(in.getHighlights()));
        if (in.getUnterkunft() != null)
            out.setUnterkunft(Unterkunft2UnterkunftReadListTO.apply(in.getUnterkunft()));

        return out;
    }

    public static List<LandReadTO> apply(List<Land> lands) {
        return lands.stream().map(u -> apply(u)).collect(Collectors.toList());
    }

}
