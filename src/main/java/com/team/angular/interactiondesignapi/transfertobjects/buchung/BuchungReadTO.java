package com.team.angular.interactiondesignapi.transfertobjects.buchung;

import java.util.Date;
import java.util.UUID;

import com.team.angular.interactiondesignapi.models.ZahlungMethod;
import com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen.BuchungsklassenReadTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuchungReadTO {

	private UUID id;

	private Date datum;

	private UUID mitReiserId;

	private UUID tarifId;

	private String flugAhfen;

	private String handGepaeck;

	private String koffer;

	private ZahlungMethod zahlungMethod;

	private UUID reiserId;

	private UUID landId;
	
}
