package com.team.angular.interactiondesignapi.transfertobjects.land;

import java.util.List;
import java.util.UUID;

import com.team.angular.interactiondesignapi.transfertobjects.hightlight.HighlightReadWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.landInfo.LandInfoReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot.ReiseAngebotReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.unterkunft.UnterkunftReadListTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LandReadTO {

	private UUID id;

	private String name;

	private List<String> flughafen;

	private String unterkunft_text;

	private byte[] karte_bild;

	private String klima;

	private String gesundheit;

	private String reiseOrdnung;

	private ReiseAngebotReadTO reiseAngebot;

	private List<LandInfoReadListTO> infosLands;

	private List<HighlightReadWriteTO> highlights;

	private List<UnterkunftReadListTO> unterkunft;

}
