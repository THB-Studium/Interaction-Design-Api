package com.team.angular.interactiondesignapi.transfertobjects.unterkunft;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnterkunftWriteTO {

	private UUID id;

	private String name;

	private String link;

	private String addresse;

	private String beschreibung;
	
	private List<String> startbild;

	private UUID landId;

}
