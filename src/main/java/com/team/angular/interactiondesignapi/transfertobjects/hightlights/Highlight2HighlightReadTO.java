package com.team.angular.interactiondesignapi.transfertobjects.hightlights;

import com.team.angular.interactiondesignapi.models.Highlight;

import java.util.List;
import java.util.stream.Collectors;

public class Highlight2HighlightReadTO {

    public static HighlightReadTO apply(Highlight in) {
        HighlightReadTO out = new HighlightReadTO();
        out.setId(in.getId());
        out.setName(in.getName());
        out.setDescription(in.getDescription());
        out.setBild(in.getBild());
        out.setLand_id(in.getLand().getId());

        return out;
    }

    public static List<HighlightReadTO> apply(List<Highlight> highlights) {
        return highlights.stream().map(highlight -> apply(highlight)).collect(Collectors.toList());
    }

}
