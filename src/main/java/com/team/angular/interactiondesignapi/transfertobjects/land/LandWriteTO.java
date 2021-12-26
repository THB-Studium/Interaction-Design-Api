package com.team.angular.interactiondesignapi.transfertobjects.land;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LandWriteTO {

	private UUID id;

	private String name;

	private List<String> flughafen;

	private String unterkunft_text;

	private String corona_infos; // TODO remove

	private String klima; // TODO remove

	private String gesundheit; // TODO remove

	private String reiseOrdnung; // TODO remove

	private String hinweise; // TODO remove to reiseAngebot

	private List<String> mitReiserBerechtigt; // in reiseAngebot

	private String sonstigeHinweise; // TODO remove to reiseAngebot
	
	private UUID reiseAngebotId;
	
}
