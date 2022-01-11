package com.team.angular.interactiondesignapi.transfertobjects.buchung;

import java.util.Date;
import java.util.UUID;

import com.team.angular.interactiondesignapi.models.ZahlungMethod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuchungUpdateTO {
	
	private UUID id;

	private Date datum;

	private UUID mitReiserId;

	private UUID buchungsklasseId;

	private String flugAhfen;

	private String handGepaeck;

	private String koffer;
	
	private ZahlungMethod zahlungMethod;
	
	private UUID reiserId;
	
	private UUID landId;
	
	private UUID reiseAngebotId;

}
