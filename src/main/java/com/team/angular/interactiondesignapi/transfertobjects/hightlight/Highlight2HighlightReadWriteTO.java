package com.team.angular.interactiondesignapi.transfertobjects.hightlight;

import com.team.angular.interactiondesignapi.models.Highlight;

import java.util.List;
import java.util.stream.Collectors;

public class Highlight2HighlightReadWriteTO {

    public static HighlightReadReadTO apply(Highlight in) {
        HighlightReadReadTO out = new HighlightReadReadTO();
        out.setId(in.getId());
        out.setName(in.getName());
        out.setDescription(in.getDescription());
        out.setBild(in.getBild());
        if (in.getLand() != null)
            out.setLandId(in.getLand().getId());

        return out;
    }

    public static List<HighlightReadReadTO> apply(List<Highlight> highlights) {
        return highlights.stream().map(highlight -> apply(highlight)).collect(Collectors.toList());
    }

}
