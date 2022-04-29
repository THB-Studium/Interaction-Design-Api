package com.team.angular.interactiondesignapi.transfertobjects.land;

import com.team.angular.interactiondesignapi.transfertobjects.hightlight.HighlightReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.landInfo.LandInfoReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.unterkunft.UnterkunftReadListTO;
import lombok.*;

import javax.persistence.Lob;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LandReadTO {

    private UUID id;

    private String name;

    private String headerFarbe;

    private String bodyFarbe;

    private List<String> flughafen;

    @Lob
    private String unterkunft_text;

    private byte[] karte_bild;

    private List<LandInfoReadListTO> landInfo;

    private List<HighlightReadListTO> highlights;

    private List<UnterkunftReadListTO> unterkunft;
}
