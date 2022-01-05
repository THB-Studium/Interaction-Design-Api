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
	
	private UUID reiseAngebotId;
	
}
