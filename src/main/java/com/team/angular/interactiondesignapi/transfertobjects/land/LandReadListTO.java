package com.team.angular.interactiondesignapi.transfertobjects.land;

import java.util.Date;
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

	private Date startDatum;

	private Date endDatum;

	private String titel;

	private int plaetze;

	private int freiPlaetze;

	private Date anmeldungsFrist;

}
