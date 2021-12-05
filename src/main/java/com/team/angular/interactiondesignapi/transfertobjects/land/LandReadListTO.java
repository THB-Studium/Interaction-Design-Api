package com.team.angular.interactiondesignapi.transfertobjects.land;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LandReadListTO {

	private UUID id;

	private String name;

	private List<String> flughafen;

	private String unterkunft_text;

	private String corona_infos;

	private byte[] karte_bild;

	private String klima;

	private String gesundheit;

	private String reiseOrdnung;

	private String hinweise;

	private List<String> mitReiserBerechtigt;

	private String sonstigeHinweise;

}
