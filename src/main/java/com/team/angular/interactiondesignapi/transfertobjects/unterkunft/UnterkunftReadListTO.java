package com.team.angular.interactiondesignapi.transfertobjects.unterkunft;

import java.util.UUID;

import com.team.angular.interactiondesignapi.transfertobjects.land.LandReadTO;

public class UnterkunftReadListTO {

	private UUID id;

	private String name;

	private String link;

	private String adresse;

	private String beschreibung;

	private LandReadTO land;

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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public LandReadTO getLand() {
		return land;
	}

	public void setLand(LandReadTO land) {
		this.land = land;
	}

}
