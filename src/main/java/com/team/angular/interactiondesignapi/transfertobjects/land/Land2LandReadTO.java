package com.team.angular.interactiondesignapi.transfertobjects.land;

import java.util.List;
import java.util.stream.Collectors;

import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.transfertobjects.hightlights.Highlight2HighlightReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.landInfo.LandInfo2LandInfoReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot.ReiseAngebot2ReiseAngebotReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.unterkunft.Unterkunft2UnterkunftReadListTO;

public class Land2LandReadTO {

    public static LandReadTO apply(Land in) {
        LandReadTO out = new LandReadTO();

        out.setId(in.getId());
        out.setName(in.getName());
        out.setFlughafen(in.getFlughafen());
        out.setUnterkunft_text(in.getUnterkunft_text());
        out.setCorona_infos(in.getCorona_infos());
        out.setKarte_bild(in.getKarte_bild());
        out.setHinweise(in.getHinweise());
        out.setMitReiserBerechtigt(in.getMitReiserBerechtigt());
        out.setSonstigeHinweise(in.getSonstigeHinweise());
        
        if(in.getReiseAngebot() != null)
        	out.setReiseAngebot(ReiseAngebot2ReiseAngebotReadTO.apply(in.getReiseAngebot()));
        if (in.getLand_info() != null)
            out.setInfosLands(LandInfo2LandInfoReadListTO.apply(in.getLand_info()));
        if (in.getHighlights() != null)
            out.setHighlights(Highlight2HighlightReadTO.apply(in.getHighlights()));
        if (in.getUnterkunft() != null)
            out.setUnterkunft(Unterkunft2UnterkunftReadListTO.apply(in.getUnterkunft()));

        return out;
    }

    public static List<LandReadTO> apply(List<Land> lands) {
        return lands.stream().map(u -> apply(u)).collect(Collectors.toList());
    }

}
