package com.team.angular.interactiondesignapi.transfertobjects.reiser;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungReadTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReiserReadTO {

	private UUID id;

	private String name;

	private String vorname;

	private Date geburtsdatum;

	private long telefonnummer;

	private String email;

	private String hochschule;

	private String adresse;

	private String studiengang;

	private String arbeitBei;

	private boolean schonTeilgenommen;

	private Set<BuchungReadTO> buchungen;

}
