package com.team.angular.interactiondesignapi.transfertobjects.land;

import java.util.Date;
import java.util.Set;
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

	private Date startDatum;

	private Date endDatum;

	private String titel;

	private int plaetze;

	private int freiPlaetze;

	private Date anmeldungsFrist;

	private Set<String> leistungen;

	private UUID erwartungenId;
	
	private UUID infos_LandId;
	
	private UUID buchungsklassenId;
}
