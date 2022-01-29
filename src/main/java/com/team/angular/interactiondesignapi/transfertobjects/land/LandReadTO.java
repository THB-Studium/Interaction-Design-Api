package com.team.angular.interactiondesignapi.transfertobjects.land;

import com.team.angular.interactiondesignapi.transfertobjects.hightlight.HighlightReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.landInfo.LandInfoReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.unterkunft.UnterkunftReadListTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LandReadTO {

    private UUID id;

    private String name;

    private String headerFarbe;

    private String bodyFarbe;

    private List<String> flughafen;

    private String unterkunft_text;

    private byte[] karte_bild;

    private List<LandInfoReadListTO> landInfo;

    private List<HighlightReadListTO> highlights;

    private List<UnterkunftReadListTO> unterkunft;
}
