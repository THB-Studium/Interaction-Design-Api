package com.team.angular.interactiondesignapi.transfertobjects.reiser;

import java.util.Date;
import java.util.UUID;

public class ReiserReadListTO {

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

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public Date getGeburtsdatum() {
		return geburtsdatum;
	}

	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

	public long getTelefonnummer() {
		return telefonnummer;
	}

	public void setTelefonnummer(long telefonnummer) {
		this.telefonnummer = telefonnummer;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHochschule() {
		return hochschule;
	}

	public void setHochschule(String hochschule) {
		this.hochschule = hochschule;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getStudiengang() {
		return studiengang;
	}

	public void setStudiengang(String studiengang) {
		this.studiengang = studiengang;
	}

	public String getArbeitBei() {
		return arbeitBei;
	}

	public void setArbeitBei(String arbeitBei) {
		this.arbeitBei = arbeitBei;
	}

	public boolean isSchonTeilgenommen() {
		return schonTeilgenommen;
	}

	public void setSchonTeilgenommen(boolean schonTeilgenommen) {
		this.schonTeilgenommen = schonTeilgenommen;
	}

}
