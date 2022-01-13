package com.team.angular.interactiondesignapi.transfertobjects.buchung;

import java.util.Date;
import java.util.UUID;

import com.team.angular.interactiondesignapi.models.ZahlungMethod;
import com.team.angular.interactiondesignapi.transfertobjects.reiser.ReiserWriteTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuchungWriteTO {
	
	private UUID id;

	private Date datum;

	private ReiserWriteTO mitReiser;

	private UUID buchungsklasseId;

	private String flugHafen;

	private String handGepaeck;

	private String koffer;
	
	private ZahlungMethod zahlungMethod;
	
	private ReiserWriteTO reiser;
	
	private UUID reiseAngebotId;

}
